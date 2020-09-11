package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import utility.InputListener;
import utility.Message;

/**
 * Class description: A basic class with a GUI interface to
 * control client side of application.
 * will give a chance to clients to play a simple tic-tac-toe
 * game and chat during the game.
 *@Author Amir Bozorgyamchi (774570)
 *@version September 28, 2019
 */
public class ClientGui extends JFrame implements PropertyChangeListener
{
	// Attributes
	private JPanel panel;
	private JButton connect;
	private JButton disconnect;
	//private JButton reset;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	private JButton button6;
	private JButton button7;
	private JButton button8;
	private JButton button9;
	private Border border;
	private JLabel ipAddressLabel;
	private JTextField ipAddressTextfield;
	private JLabel shapeLabel;
	private JTextField shapeText;
	private JLabel userNameLabel;
	private JTextField userNameTextField;
	private JTextField message;
	private JTextArea chatArea;
	private JLabel messageLabel;
	private JButton send;
	private JButton clear;
	private Socket socket = null;
	private ObjectOutputStream oos = null;
	private InputListener input;
	private ArrayList<PropertyChangeListener> observers = new ArrayList<>();
	private Message messageConnect;
	
	// Constructors
	/**
	 * non-argument Constructor for ClientGui
	 */
	public ClientGui() {
		super();
		setTitle("Tic-Tac-Toe");
		setLocation(100, 100);
		setSize(1000,600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(5, 5));
		buildPanel();
		add(panel);
		setVisible(true);
		observers.add(this);
	}
	
	/**
	 * divide the main panel to three sections(north, center, south).
	 * create three other panels and add them to the main panel's sections.
	 */
	private void buildPanel() {
		panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		border = BorderFactory.createRaisedBevelBorder();
		panel.add(createNorthPanel(), BorderLayout.NORTH);
		panel.add(createCenterPanel(), BorderLayout.CENTER);
		panel.add(createSouthPanel(), BorderLayout.SOUTH);
	}
	
	/**
	 * create north panel's components.
	 * @return northPanel
	 */
	private JPanel createNorthPanel() {
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10,10));
		
		JPanel ip = new JPanel();
		ip.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
		ipAddressLabel = new JLabel("Server's IP Address: ");
		ipAddressLabel.setForeground(Color.BLUE);
		ipAddressLabel.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		ipAddressLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ipAddressTextfield = new JTextField(20);
		ipAddressTextfield.setHorizontalAlignment(SwingConstants.LEFT);
		userNameLabel  = new JLabel("User Name: ");
		userNameLabel.setForeground(Color.BLUE);
		userNameLabel.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		userNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		userNameTextField = new JTextField(20);
		userNameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		shapeLabel = new JLabel("Your Shape: ");
		shapeLabel.setForeground(Color.BLUE);
		shapeLabel.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		shapeText = new JTextField(3);
		
		ip.add(ipAddressLabel);
		ip.add(ipAddressTextfield);
		ip.add(userNameLabel);
		ip.add(userNameTextField);
		ip.add(shapeLabel);
		ip.add(shapeText);
		
		northPanel.add(ip);
		
		return northPanel;
	}
	
	
	/**divide center panel to two sections(left, right).
	 * @return center panel.
	 */
	private JPanel createCenterPanel() {
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new GridLayout(1, 2));
		panelCenter.add(this.createCenterLeft());
		panelCenter.add(this.createCenterRight());
		return panelCenter;
	}
	
	
	/**create left side of center panel's components and divide the 
	 * left section to two sections(gameTitle, gameBoard).
	 * @return left section of center panel.
	 */
	private JPanel createCenterLeft() {
		JPanel centerLeft = new JPanel();
		centerLeft.setLayout(new BorderLayout());
		centerLeft.setBorder(border);
	
		JPanel game = createGameBoard();
		JPanel gameTitle = createGameTitle();
		
		centerLeft.add(gameTitle, BorderLayout.NORTH);
		centerLeft.add(game, BorderLayout.CENTER);
		
		return centerLeft;
	}
	
	
	/**create gameTitle section of center left.
	 * @return game title
	 */
	private JPanel createGameTitle() {
		JPanel title = new JPanel();
		title.setLayout(new FlowLayout(FlowLayout.CENTER, 10,10));
		title.setBorder(border);
		title.setSize(200,300);
		JLabel label = new JLabel("Game Board");
		label.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		title.add(label);
		
		return title;
	}
	
	
	/**create gameBoard section of center left.
	 * @return gameBoard
	 */
	private JPanel createGameBoard() {
		JPanel gameBoard = new JPanel();
		gameBoard.setLayout(new GridLayout(3,3));
		button1 = new JButton();
		button2 = new JButton();
		button3 = new JButton();
		button4 = new JButton();
		button5 = new JButton();
		button6 = new JButton();
		button7 = new JButton();
		button8 = new JButton();
		button9 = new JButton();
		button1.addActionListener(new Button1());
		button2.addActionListener(new Button2());
		button3.addActionListener(new Button3());
		button4.addActionListener(new Button4());
		button5.addActionListener(new Button5());
		button6.addActionListener(new Button6());
		button7.addActionListener(new Button7());
		button8.addActionListener(new Button8());
		button9.addActionListener(new Button9());
		gameBoard.add(button1);
		gameBoard.add(button2);
		gameBoard.add(button3);
		gameBoard.add(button4);
		gameBoard.add(button5);
		gameBoard.add(button6);
		gameBoard.add(button7);
		gameBoard.add(button8);
		gameBoard.add(button9);
		
		return gameBoard;
	}
	
	
	/**
	 * Class description: A basic class which controls the
	 *button1's actions.
	 */
	public class Button1 implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
				button1.setText(shapeText.getText()); 
				button1.setFont(new Font("Arial", Font.PLAIN, 80));
				Message m = new Message(button1.getText(),1);
				try {
					oos.writeObject(m);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				disable();
				whoWin();
				noWin();
		}
	}
	
	/**
	 * Class description: A basic class which controls the
	 *button2's actions.
	 */
	public class Button2 implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			button2.setText(shapeText.getText());
			button2.setFont(new Font("Arial", Font.PLAIN, 80));
			Message m = new Message(button2.getText(),2);
			try {
				oos.writeObject(m);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			disable();
			whoWin();
			noWin();
		}
	}
	
	/**
	 * Class description: A basic class which controls the
	 *button3's actions.
	 */
	public class Button3 implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			button3.setText(shapeText.getText());
			button3.setFont(new Font("Arial", Font.PLAIN, 80));
			Message m = new Message(button3.getText(),3);
			try {
				oos.writeObject(m);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			disable();
			whoWin();
			noWin();
		}
	}
	
	/**
	 * Class description: A basic class which controls the
	 *button4's actions.
	 */
	public class Button4 implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			button4.setText(shapeText.getText());
			button4.setFont(new Font("Arial", Font.PLAIN, 80));
			Message m = new Message(button4.getText(),4);
			try {
				oos.writeObject(m);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			disable();
			whoWin();
			noWin();
		}
	}
	
	/**
	 * Class description: A basic class which controls the
	 *button5's actions.
	 */
	public class Button5 implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			button5.setText(shapeText.getText());
			button5.setFont(new Font("Arial", Font.PLAIN, 80));
			Message m = new Message(button5.getText(),5);
			try {
				oos.writeObject(m);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			disable();
			whoWin();
			noWin();
		}
	}
	
	/**
	 * Class description: A basic class which controls the
	 *button6's actions.
	 */
	public class Button6 implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			button6.setText(shapeText.getText());
			button6.setFont(new Font("Arial", Font.PLAIN, 80));
			Message m = new Message(button6.getText(),6);
			try {
				oos.writeObject(m);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			disable();
			whoWin();
			noWin();
		}
	}
	
	/**
	 * Class description: A basic class which controls the
	 *button7's actions.
	 */
	public class Button7 implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			button7.setText(shapeText.getText());
			button7.setFont(new Font("Arial", Font.PLAIN, 80));
			Message m = new Message(button7.getText(),7);
			try {
				oos.writeObject(m);
			} catch (IOException e1) {
				e1.printStackTrace();
			}	
			disable();
			whoWin();
			noWin();
		}
	}
	
	/**
	 * Class description: A basic class which controls the
	 *button8's actions.
	 */
	public class Button8 implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			button8.setText(shapeText.getText());
			button8.setFont(new Font("Arial", Font.PLAIN, 80));
			Message m = new Message(button8.getText(),8);
			try {
				oos.writeObject(m);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			disable();
			whoWin();
			noWin();
		}
	}
	
	/**
	 * Class description: A basic class which controls the
	 *button9's actions.
	 */
	public class Button9 implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			button9.setText(shapeText.getText());
			button9.setFont(new Font("Arial", Font.PLAIN, 80));
			Message m = new Message(button9.getText(),9);
			try {
				oos.writeObject(m);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			disable();
			whoWin();
			noWin();
		}
	}
	
	
	/* 
	 * will disable all the buttons.
	 */
	public void disable() {
		button1.setEnabled(false);
		button2.setEnabled(false);
		button3.setEnabled(false);
		button4.setEnabled(false);
		button5.setEnabled(false);
		button6.setEnabled(false);
		button7.setEnabled(false);
		button8.setEnabled(false);
		button9.setEnabled(false);
	}
	
	
	/** create center right section's components.
	 * divide it to two sections(chatRoom, chatTitle).
	 * @return center right
	 */
	private JPanel createCenterRight() {
		JPanel centerRight = new JPanel();
		centerRight.setLayout(new BorderLayout());
		centerRight.setBorder(border);
		
		JPanel chat = createChatRoom();
		JPanel chatTitle = createChatTitle();
		
		centerRight.add(chatTitle, BorderLayout.NORTH);
		centerRight.add(chat, BorderLayout.CENTER);
		
		return centerRight;
	}	
	
	
	/** create chatTitle section of center right.
	 * @return chatTitle
	 */
	private JPanel createChatTitle() {
		JPanel chatTitle = new JPanel();
		chatTitle.setBorder(border);
		chatTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 10,10));
		chatTitle.setSize(200,300);
		
		JLabel label = new JLabel("Chat Room");
		label.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		
		chatTitle.add(label);
		
		return chatTitle;
	}
	
	
	/**create chatRoom section of center right.
	 * @return chatRoom
	 */
	private JPanel createChatRoom() {
		JPanel chatRoom = new JPanel();
		chatRoom.setLayout(new GridLayout(2,1,20,10));

		JPanel messageAreaPanel = new JPanel();
		messageAreaPanel.setLayout(new FlowLayout());
		chatArea = new JTextArea(50,45);
		JScrollPane scrol = new JScrollPane(chatArea);
		messageAreaPanel.add(scrol);
	
		JPanel messageArea = new JPanel();
		messageArea.setLayout(new FlowLayout());
		JPanel label =  new JPanel();
		label.setLayout(new GridLayout(1,1));
		messageLabel = new JLabel("Send Your Message to Your Opponent: ");
		message = new JTextField(20);
		label.add(messageLabel);
		label.add(message);
		
		JPanel button = new JPanel();
		button.setLayout(new GridLayout(1,1));
		send = new JButton("Send");
		clear = new JButton("Clear");
		clear.addActionListener(new ClearButton());
		send.addActionListener(new SendButton());
		button.add(send);
		button.add(clear);
		
		messageArea.add(label);
		messageArea.add(button);
		
		chatRoom.add(messageAreaPanel);
		chatRoom.add(messageArea);
		return chatRoom;
	}
	
	
	/**
	 * Class description: A class to control Clear button's section.
	 *will remove all the texts from chat area.
	 */
	public class ClearButton implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			chatArea.setText(null);
		}
	}
	
	
	/* 
	 *this is the override method of PropertyChangeListener interface.
	 *this method will call from the InputListner's run method.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Message message = (Message) evt.getNewValue();

		if(message.getID() == 1000) {
			JOptionPane.showMessageDialog(null, message.getButton());
		}
		
		//send each button's text(X or O) to the other client.
		if(message.getID() == 1) {
			button1.setText(message.getButton());
			button1.setFont(new Font("Arial", Font.PLAIN, 80));
			enable();
		} else if(message.getID() == 2) {
			button2.setText(message.getButton());
			button2.setFont(new Font("Arial", Font.PLAIN, 80));
			enable();
		} else if(message.getID() == 3) {
			button3.setText(message.getButton());
			button3.setFont(new Font("Arial", Font.PLAIN, 80));
			enable();
		} else if(message.getID() == 4) {
			button4.setText(message.getButton());
			button4.setFont(new Font("Arial", Font.PLAIN, 80));
			enable();
		} else if(message.getID() == 5) {
			button5.setText(message.getButton());
			button5.setFont(new Font("Arial", Font.PLAIN, 80));
			enable();
		} else if(message.getID() == 6) {
			button6.setText(message.getButton());
			button6.setFont(new Font("Arial", Font.PLAIN, 80));
			enable();
		} else if(message.getID() == 7) {
			button7.setText(message.getButton());
			button7.setFont(new Font("Arial", Font.PLAIN, 80));
			enable();
		} else if(message.getID() == 8) {
			button8.setText(message.getButton());
			button8.setFont(new Font("Arial", Font.PLAIN, 80));
			enable();
		} else if(message.getID() == 9) {
			button9.setText(message.getButton());
			button9.setFont(new Font("Arial", Font.PLAIN, 80));
			enable();
		}
		
		//call whoWin method that checks which client won the game. 
		whoWin();
		
		//call noWin method that check if any of the players won the game.
		noWin();
		
		//send each client's message to other client and display it on the chat area.
		if(message.getMessageID() == 99) {
			chatArea.append(message.getUser() + ": " + message.getMsg() + "\n");
		}
		
		if(message.getMessageID() == 91) {
			JOptionPane.showMessageDialog(null, message.getUser()+ message.getMsg());
		}
		
	}
	
	public void noWin() {
		if(!button1.getText().equals("") && !button2.getText().equals("") && !button3.getText().equals("") && !button4.getText().equals("")
				&& !button5.getText().equals("") && !button6.getText().equals("") && !button7.getText().equals("") && !button8.getText().equals("")
				&& !button9.getText().equals("")) 
		{
			String s = JOptionPane.showInputDialog("No Player Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
			}
		}
	}
	
	/* 
	 * set all button's text to empty strings and
	 * enable all buttons.
	 */
	public void enable() {
		if(button1.getText().equals("")) {
			button1.setEnabled(true);
		}
		if(button2.getText().equals("")) {
			button2.setEnabled(true);
		}
		if(button3.getText().equals("")) {
			button3.setEnabled(true);
		}
		if(button4.getText().equals("")) {
			button4.setEnabled(true);
		}
		if(button5.getText().equals("")) {
			button5.setEnabled(true);
		}
		if(button6.getText().equals("")) {
			button6.setEnabled(true);
		}
		if(button7.getText().equals("")) {
			button7.setEnabled(true);
		}
		if(button8.getText().equals("")) {
			button8.setEnabled(true);
		}
		if(button9.getText().equals("")) {
			button9.setEnabled(true);
		}
	}
	
	
	/**
	 * this method checks who won the game.
	 * and will allow the clients to choose quit or continue.
	 */
	public void whoWin() {
		if(button1.getText().equals("X") && button2.getText().equals("X") && button3.getText().equals("X")) {
			button1.setBackground(Color.red);
			button2.setBackground(Color.red);
			button3.setBackground(Color.red);
			String s = JOptionPane.showInputDialog("Player X Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
		else if(button1.getText().equals("X") && button4.getText().equals("X") && button7.getText().equals("X")) {
			button1.setBackground(Color.red);
			button4.setBackground(Color.red);
			button7.setBackground(Color.red);
			String s = JOptionPane.showInputDialog("Player X Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
		else if(button1.getText().equals("X") && button5.getText().equals("X") && button9.getText().equals("X")) {
			button1.setBackground(Color.red);
			button5.setBackground(Color.red);
			button9.setBackground(Color.red);
			String s = JOptionPane.showInputDialog("Player X Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
		else if(button2.getText().equals("X") && button5.getText().equals("X") && button8.getText().equals("X")) {
			button2.setBackground(Color.red);
			button5.setBackground(Color.red);
			button8.setBackground(Color.red);
			String s = JOptionPane.showInputDialog("Player X Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
		else if(button3.getText().equals("X") && button6.getText().equals("X") && button9.getText().equals("X")) {
			button3.setBackground(Color.red);
			button6.setBackground(Color.red);
			button9.setBackground(Color.red);
			String s = JOptionPane.showInputDialog("Player X Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
		else if(button4.getText().equals("X") && button5.getText().equals("X") && button6.getText().equals("X")) {
			button4.setBackground(Color.red);
			button5.setBackground(Color.red);
			button6.setBackground(Color.red);
			String s = JOptionPane.showInputDialog("Player X Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
		else if(button7.getText().equals("X") && button8.getText().equals("X") && button9.getText().equals("X")) {
			button7.setBackground(Color.red);
			button8.setBackground(Color.red);
			button9.setBackground(Color.red);
			String s = JOptionPane.showInputDialog("Player X Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
		else if(button3.getText().equals("X") && button5.getText().equals("X") && button7.getText().equals("X")) {
			button3.setBackground(Color.red);
			button5.setBackground(Color.red);
			button7.setBackground(Color.red);
			String s = JOptionPane.showInputDialog("Player X Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
		else if(button3.getText().equals("X") && button5.getText().equals("X") && button7.getText().equals("X")) {
			button3.setBackground(Color.red);
			button5.setBackground(Color.red);
			button7.setBackground(Color.red);
			String s = JOptionPane.showInputDialog("Player X Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
		else if(button1.getText().equals("O") && button2.getText().equals("O") && button3.getText().equals("O")) {
			button1.setBackground(Color.yellow);
			button2.setBackground(Color.yellow);
			button3.setBackground(Color.yellow);
			String s = JOptionPane.showInputDialog("Player O Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
		else if(button1.getText().equals("O") && button4.getText().equals("O") && button7.getText().equals("O")) {
			button1.setBackground(Color.yellow);
			button4.setBackground(Color.yellow);
			button7.setBackground(Color.yellow);
			String s = JOptionPane.showInputDialog("Player O Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
		else if(button1.getText().equals("O") && button5.getText().equals("O") && button9.getText().equals("O")) {
			button1.setBackground(Color.yellow);
			button5.setBackground(Color.yellow);
			button9.setBackground(Color.yellow);
			String s = JOptionPane.showInputDialog("Player O Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
		else if(button2.getText().equals("O") && button5.getText().equals("O") && button8.getText().equals("O")) {
			button2.setBackground(Color.yellow);
			button5.setBackground(Color.yellow);
			button8.setBackground(Color.yellow);
			String s = JOptionPane.showInputDialog("Player O Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);	
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
		else if(button3.getText().equals("O") && button6.getText().equals("O") && button9.getText().equals("O")) {
			button3.setBackground(Color.yellow);
			button6.setBackground(Color.yellow);
			button9.setBackground(Color.yellow);
			String s = JOptionPane.showInputDialog("Player O Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
		else if(button4.getText().equals("O") && button5.getText().equals("O") && button6.getText().equals("O")) {
			button4.setBackground(Color.yellow);
			button5.setBackground(Color.yellow);
			button6.setBackground(Color.yellow);
			String s = JOptionPane.showInputDialog("Player O Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
		else if(button7.getText().equals("O") && button8.getText().equals("O") && button9.getText().equals("O")) {
			button7.setBackground(Color.yellow);
			button8.setBackground(Color.yellow);
			button9.setBackground(Color.yellow);
			String s = JOptionPane.showInputDialog("Player O Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);	
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
		else if(button3.getText().equals("O") && button5.getText().equals("O") && button7.getText().equals("O")) {
			button3.setBackground(Color.yellow);
			button5.setBackground(Color.yellow);
			button7.setBackground(Color.yellow);
			String s = JOptionPane.showInputDialog("Player O Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
		else if(button3.getText().equals("O") && button5.getText().equals("O") && button7.getText().equals("O")) {
			button3.setBackground(Color.yellow);
			button5.setBackground(Color.yellow);
			button7.setBackground(Color.yellow);
			String s = JOptionPane.showInputDialog("Player O Won!!\n\nType Quit or Continue?\nQuit: Will Exit The Game.\nContinue: Will Continue To Play With Same Player Or Another Player.\n\n");
			if(s.equalsIgnoreCase("quit")) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
			else if (s.equalsIgnoreCase("continue")) {
				reset();
				restColor();
			}
		}
	}
	
	
	/**
	 * reset all the background color of the buttons.
	 */
	public void restColor() {
		button1.setBackground(null);
		button2.setBackground(null);
		button3.setBackground(null);
		button4.setBackground(null);
		button5.setBackground(null);
		button6.setBackground(null);
		button7.setBackground(null);
		button8.setBackground(null);
		button9.setBackground(null);
	}
	
	
	/**
	 * enable all the buttons and set all button's text to empty string.
	 */
	public void reset() {
		button1.setEnabled(true);
		button2.setEnabled(true);
		button3.setEnabled(true);
		button4.setEnabled(true);
		button5.setEnabled(true);
		button6.setEnabled(true);
		button7.setEnabled(true);
		button8.setEnabled(true);
		button9.setEnabled(true);
		button1.setText("");
		button2.setText("");
		button3.setText("");
		button4.setText("");
		button5.setText("");
		button6.setText("");
		button7.setText("");
		button8.setText("");
		button9.setText("");
	}
	
	
	/**
	 * Class description: A class to control Send button's action.
	 * send client's message to other client's chat area.
	 */
	public class SendButton implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = userNameTextField.getText();
			String m = message.getText();
			chatArea.append(username+ ":" + " ");
			chatArea.append(m);
			chatArea.append("\n");
			message.setText(null);
			Message msg = new Message(username,m,new Date(),99);
			try {
				oos.writeObject(msg);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	/** create south panel section's components.
	 * @return south panel
	 */
	private JPanel createSouthPanel() {
		JPanel panelSouth = new JPanel();
		panelSouth.setLayout(new BorderLayout());
		connect = new JButton("Connect");
		disconnect = new JButton("Disconnect");
		connect.addActionListener(new Connect());
		disconnect.addActionListener(new Disconnect());
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(connect);
		panel.add(disconnect);
		
//		reset = new JButton("Reset");
//		reset.addActionListener(new Reset ());
//		JPanel panel2 = new JPanel();
//		panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
//		panel2.add(reset);
		
		panelSouth.add(panel, BorderLayout.EAST);
		//panelSouth.add(panel2, BorderLayout.WEST);
		
		return panelSouth;
	}
	
	
	/**
	 * Class description: A basic class to control Connect button's actions.
	 *create a communication between client and server.
	 *start connection....
	 */
	public class Connect implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == connect) {
				try {
					String ip = ipAddressTextfield.getText();
					socket = new Socket(ip,4444);
					oos = new ObjectOutputStream(socket.getOutputStream());
					Message m = new Message("You Have Matched To Your Opponent.\nYout Can Start Game!",1000);
					oos.writeObject(m);
					
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					messageConnect = (Message)ois.readObject();
					
					if(messageConnect.getID() == 999) {
						shapeText.setText(messageConnect.getButton());
						JOptionPane.showMessageDialog(null,"You Are The First Player.\nYou Will Paly With Shape "+messageConnect.getButton()+"\n"+"Wait For Your Opponent!!");
					}
					else if(messageConnect.getID() == 888) {
						shapeText.setText(messageConnect.getButton());
						JOptionPane.showMessageDialog(null,"You Are The Second Player.\nYou Will Paly With Shape "+messageConnect.getButton());
					}
					
					input = new InputListener(1,socket, observers);
					Thread t = new Thread(input);
					t.start();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * Class description: A class to control Disconnect button's actions.
	 * it will disconnect client form sever and exit the program.
	 */
	public class Disconnect implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == disconnect) {
				try {
					String m = "Your Opponent Left The Game.\nConnect Again To Find Another Opponent.";
					String username = "Admin: ";
					Message msg = new Message(username, m, new Date(),91);
					oos.writeObject(msg);
					oos.close();
					socket.close();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "You have been disconnected!!");
				}
			}
		}
	}
	
	
	/**
	 * Class description: A class to control Rest button's actions.
	 *set all button's text to empty strings and enable all buttons.
	 */
//	public class Reset implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			button1.setText("");
//			button2.setText("");
//			button3.setText("");
//			button4.setText("");
//			button5.setText("");
//			button6.setText("");
//			button7.setText("");
//			button8.setText("");
//			button9.setText("");
//			button1.setEnabled(true);
//			button2.setEnabled(true);
//			button3.setEnabled(true);
//			button4.setEnabled(true);
//			button5.setEnabled(true);
//			button6.setEnabled(true);
//			button7.setEnabled(true);
//			button8.setEnabled(true);
//			button9.setEnabled(true);
//		}
//	}
	
	
	/**start program......
	 * @param args
	 */
	public static void main(String[] args)
	{
		new ClientGui();
	}
}
