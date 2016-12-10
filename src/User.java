import java.io.Serializable;

/**
 * A class representing a user for the hotel reservation system
 * @author Karan Bhargava
 * @version 1.2016.991
 *
 */
public class User implements Serializable
{	
	private static final long serialVersionUID = 1L;
	private int userID;
	private String userName;

	/**
	 * A constructor to construct the users for the hotel
	 * @param id - the userID of the user
	 * @param name - the name of the passenger
	 * @param password - the password for the user
	 */
	public User(int id, String name) {userID = id; userName = name;}

	/**
	 * A getter method to get the userID of the passenger
	 * @return userID - the userID of the passenger
	 */
	public int getUserID() {return userID;}

	/**
	 * A getter method to get the userName of the passenger
	 * @return userName - the userName of the passenger
	 */
	public String getUserName() {return userName;}

	/**
	 * A method to get the user information as a String
	 * @return A string with userID and name
	 */
	public String toString() {return "ID: " + userID + " Name: " + userName;}

}