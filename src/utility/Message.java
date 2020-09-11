package utility;

import java.io.*;
import java.util.*;

/**
 * @author Amir Bozorgyamchi
 * @version September 28, 2019
 *
 * Class Description: A basic message class that can be transported across
 * the network.
 * 
 */
public class Message implements Serializable
{
	//Constants
	static final long serialVersionUID = 5488945625178844229L;
	
	//Attributes
	private String 			user;
	private String			msg;
	private Date			timeStamp;
	private String           button;
	private int id;
	private int messageId;
	private String shape;
	
	//Constructors
	public Message()
	{
	}
	
	public Message(String d, int id){
		this.button = d;
		this.id = id;
	}
	
	
	/**
	 * Initializes the newly created Message
	 * @param user - the client's user name 
	 * @param msg - the clients message that will send to the other client's chat area.
	 * @param timeStamp - newly updated date
	 * @param p - id to recognize which client is sending message right now.
	 */
	public Message(String user, String msg, Date timeStamp, int p)
	{
		this.user = user;
		this.msg = msg;
		this.timeStamp = timeStamp;
		this.messageId = p;
	}
	
	/**
	 * @param x the x to set
	 */
	public void setShape(String x) {
		this.shape = x;
	}
	
	/**
	 * @return the shape
	 */
	public String getShape() {
		return shape;
	}


	/**
	 * @param q the q to set
	 */
	public void setMessageID(int q) {
		this.messageId = q;
	}
	
	/**
	 * @return the messageId
	 */
	public int getMessageID() {
		return messageId;
	}
	

	/**
	 * @param d the d to set
	 */
	public void setID(int d) {
		this.id = d;
	}
	
	/**
	 * @return the id
	 */
	public int getID() {
		return id;
	}
	

	
	public void setButton(String t) {
		this.button = t;
	}
	
	/**
	 * @return the button
	 */
	public String getButton() {
		return button;
	}
	
	/**
	 * @return the user
	 */
	public String getUser()
	{
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user)
	{
		this.user = user;
	}

	/**
	 * @return the msg
	 */
	public String getMsg()
	{
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	/**
	 * @return the timeStamp
	 */
	public Date getTimeStamp()
	{
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Date timeStamp)
	{
		this.timeStamp = timeStamp;
	}
	
	//Operational Methods
	public String toString()
	{
		return "User Name: "+user+"        "+"Date and Time: "+timeStamp+
				"\nMessage: "+msg;
	}
}

