package utility;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

/**
 * Class description: A class for receiving a Message object from other 
 * client and send it to propertyChange method of ClientHandler class.
 *@Author Amir Bozorgyamchi (774570)
 *@version September 28, 2019
 */
public class InputListener implements Runnable{
	// Attributes
	public int ID;
	private ObjectInputStream ois;
	private Socket s;
	private List<PropertyChangeListener> observers;
	
	// Constructors
	
	/**
	 * Non-Argument Constructor for InputListener class.
	 */
	/**
	 * Initializes the newly created InputListener
	 * @param id - id for recognizing the sockets.
	 * @param socket - 
	 * @param obervers - list of observers.
	 */
	public InputListener(int id, Socket socket, List<PropertyChangeListener> obervers) {
		this.ID = id;
		this.s = socket;
		this.observers = obervers;
	}
	
	/* this the override method of Runnable interface.
	 * this method will receive the transferable object and
	 * send it to the propertyChange method of ClinetHandler class.
	 */
	@Override
	public void run() {
		try {
			ois = new ObjectInputStream(s.getInputStream());
			while(true) {
				Message message = (Message) ois.readObject();
				for(int i = 0; i < observers.size(); i++) {
					observers.get(i).propertyChange(new PropertyChangeEvent(this, null, null, message));
				}
			}
		} catch (IOException e) {
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
}

