package server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import utility.InputListener;
import utility.Message;

/**
 * Class description: A class for controlling multiple clients and their connections.
 * 
 *@Author Amir Bozorgyamchi (774570)
 *@version September 28, 2019
 */
public class ClientHandler implements Runnable, PropertyChangeListener {
	//Attributes
	private ArrayList<Socket> list;
	private Socket s1;
	private Socket s2;
	private ObjectOutputStream oos1;
	private ObjectOutputStream oos2;
	private InputListener input1;
	private InputListener input2;
	private List<PropertyChangeListener> observers = new ArrayList<>();
	
	
	/**
	 * Initializes the newly created ClientHandler
	 * @param arrayList - an array of sockets.
	 */
	public ClientHandler(ArrayList<Socket> arrayList) {
		this.list = arrayList;
		this.s1 = arrayList.get(0);
		this.s2 = arrayList.get(1);
	}
	
	/* this is the override method of Runnable interface.
	 * when an instance of ClientHandler class has been created in
	 * Server class, this run method will run automatically.
	 */
	public void run() {
		try {
			observers.add(this);
			
			input1 = new InputListener(1,s1, observers);
			Thread t1 = new Thread(input1);
			t1.start();
			
			input2 = new InputListener(2,s2, observers);
			Thread t2 = new Thread(input2);
			t2.start();
			
			oos1 = new ObjectOutputStream(s1.getOutputStream());
			oos2 = new ObjectOutputStream(s2.getOutputStream());
			
			while(s1.isConnected() && s2.isConnected());
		}
		catch (IOException e)
		{
			//e.printStackTrace();
			System.out.println("client disconnected!!!");
		}
	}
	
	
	/*this is the override method of PropertyChangeListener interface.
	 * this will run when it has been called from InputListner class's 
	 * run method.
	 */
	@Override
	public synchronized void propertyChange(PropertyChangeEvent evt) {
		Message message = (Message)evt.getNewValue();
		int id = ((InputListener)evt.getSource()).ID;
		
		if(id == 1) 
		{
			try {
				oos2.writeObject(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (id == 2)
		{
			try {
				oos1.writeObject(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}



