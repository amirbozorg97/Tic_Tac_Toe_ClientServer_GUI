package server;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import utility.Message;

/**
 * Class description: This class will serve as a server for multiple 
 * clients. It will handle each client by creating a new thread. 
 *
 *@Author Amir Bozorgyamchi (774570)
 *@version September 28, 2019
 */
public class Server extends JFrame implements ActionListener{
	// Attributes
	
	private JPanel panel;
	private JTextArea area;
	private JButton disconnect;
	private JButton clear;
	ServerSocket server = null;
	Socket socket = null;
	ArrayList<Socket> list = new ArrayList<>();
	ObjectOutputStream oos1;
	ObjectOutputStream oos2;
	private JScrollPane sp;
	
	// Constructors
	
	/**
	 * Non-Argument Constructor for Server class.
	 * create a Gui for Server class.
	 * create server socket and socket.
	 */
    public Server() {
    	super();
    	setTitle("Tic-Tac-Toe Server");
		setLocation(100, 100);
		setSize(1000,600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		
		JPanel textAreaPanel = new JPanel();
		area = new JTextArea(30,60);
		sp = new JScrollPane(area);
		textAreaPanel.add(sp);
		
		JPanel buttonPanel = new JPanel();
		disconnect = new JButton("Disconnect");
		disconnect.addActionListener(this);
		clear = new JButton("Clear");
		clear.addActionListener(this);
		buttonPanel.add(disconnect);
		buttonPanel.add(clear);
		
		panel.add(textAreaPanel);
		panel.add(buttonPanel);
		
		add(panel);
		setVisible(true);
		
		try
		{
			server = new ServerSocket(4444);
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}

		area.append("Server up and running!!!!!!!!!!\n"+"----------------------------------------------------------------------------"+"\n");
		area.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		
		while(true)
		{
			try
			{
				socket = server.accept();
				list.add(socket);
				if(list.size() == 1) {
					
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd     HH:mm:ss");  
					LocalDateTime now = LocalDateTime.now();  
					area.append("First Client Connected."+"\t"+dtf.format(now)+"\n");
					oos1 = new ObjectOutputStream(list.get(0).getOutputStream());
					Message shapeX = new Message("X",999);
					oos1.writeObject(shapeX);
				}
				
				if(list.size() == 2) {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd     HH:mm:ss");  
					LocalDateTime now = LocalDateTime.now();  
					area.append("Second Client Connected."+"\t"+dtf.format(now)+"\n"+"\n");
					oos2 = new ObjectOutputStream(list.get(1).getOutputStream());
					Message shapeO = new Message("O",888);
					oos2.writeObject(shapeO);
					ClientHandler clientHandler = new ClientHandler(list);
					Thread t1 = new Thread(clientHandler);
					t1.start();
					list.clear();
				}
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
    } 
	
		/*This is the Override method of ActionListener interface.
		 * shows what will happen when the user 
		 * will click on the Disconnect button.
		 *  @param e
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			if(action.equals("Disconnect")) {
				System.exit(0);
			}
			else if(action.equals("Clear")) {
				area.setText(null);
			}
		}
	
	/**
	 * Starts the program.
	 * @param args
	 */
	public static void main(String[] args)
	{
		new Server();
		
	}
}

