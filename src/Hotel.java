import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A class representing a hotel for the hotel management system
 * @author Karan Bhargava
 * @version 1.2016.991
 *
 */
public class Hotel implements Serializable {

	//instance variables for a Hotel

	private ArrayList<Room> roomsInHotel; //total number of rooms
	private ArrayList<Room> numOfRoomsInHotel; //available rooms
	private ArrayList<User> usersOfHotel; //users of hotel
	private transient ArrayList<ChangeListener> listener = new ArrayList<ChangeListener>(); //changeListener

	/**
	 * A constructor to construct the hotel for the first time
	 */
	public Hotel() {

		roomsInHotel = new ArrayList<Room>();
		usersOfHotel = new ArrayList<User>();

		for(int i = 0; i < 10; i++){
			roomsInHotel.add(new Room(i, 80)); // new Reservations() ));
		}

		for(int i = 10; i < 20; i++){
			roomsInHotel.add(new Room(i, 200)); //new Reservations() ));
		}
	}

	/**
	 * A method to get an iterator for the rooms in the hotel
	 * @return an iterator for the rooms
	 */
	public Iterator<Room> roomIterator() {
		return roomsInHotel.iterator();
	}

	/**
	 * A method to add a room to the list of rooms
	 * @param roomToAdd - the room to add
	 */
	public void addRoom(Room roomToAdd) {
		roomsInHotel.add(roomToAdd);
	}

	/**
	 * A method to get the rooms 
	 * @return roomsInHotel - the arrayList of rooms
	 */
	public ArrayList<Room> getRooms() {
		return roomsInHotel;
	}
	
	/**
	 * A method to attach a changeListener to the object
	 * @param changeListener - the listener to attach
	 */
	public void attach(ChangeListener changeListener) {
		listener.add(changeListener);
	}

	/**
	 * A method to change the number of available rooms 
	 * @param sDate - the starting date
	 * @param eDate - the ending date
	 * @param roomType - the type of room to change
	 */
	public void changeAvailableRooms(Calendar sDate, Calendar eDate, int roomType)
	{
		Iterator<Room> iter = roomIterator();
		numOfRoomsInHotel = new ArrayList<Room>();
		while(iter.hasNext())
		{
			Room room = iter.next();
			if(room.whatType(roomType) && room.isAvailable(sDate, eDate)) {
				numOfRoomsInHotel.add(room);
			}
		}
		ChangeEvent e = new ChangeEvent(this);
		for(ChangeListener c : listener)
		{
			c.stateChanged(e);
		}
	}
	
	/**
	 * A method to read the object through the serializable arrayList
	 * @param in - the inputStream to read the object from
	 * @throws ClassNotFoundException - if class is not found
	 * @throws IOException - the object reading fails
	 */
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		in.defaultReadObject();
		listener = new ArrayList<ChangeListener>();
	}

	/**
	 * A method to get an iterator for the users
	 * @return an iterator for the users arrayList
	 */
	public Iterator<User> userIterator() {
		return usersOfHotel.iterator();
	}
	
	/**
	 * A method to add a new user to the arrayList of users
	 * @param newUser - the new user to add
	 */
	public void addUser(User newUser) {
		usersOfHotel.add(newUser);
	}
	
	/**
	 * A method to find the user by their ID
	 * @param iD - the iD to check by
	 * @return checkUser - if the user is found; null otherwise
	 */
	public User findUserByID(int iD) {
		Iterator<User> iter = userIterator();
		
		while (iter.hasNext()) {
			User checkUser = iter.next();
			if(checkUser.getUserID() == iD) {
				return checkUser;
			}
		}
		
		return null;
	}
	
	/**
	 * A method to authenticate a user with their ID and Password
	 * @param iD - the user's ID
	 * @param pass - the user's password
	 * @return true if user is found; false otherwise
	 */
	public boolean authenticate(int iD, String pass) {
		User checkUser = findUserByID(iD);
		if (checkUser != null) {
			return checkUser.getUserPassword().equals(pass);
		} 
		return false;
	}

	//Methods for reservations (getReservations, cancelReservations, 
}
