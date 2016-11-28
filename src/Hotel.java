import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
	private ArrayList<User> usersOfHotel; //users of hotel
	private ArrayList<Reservation> reservationList;

	private transient ArrayList<ChangeListener> listener = new ArrayList<ChangeListener>(); //changeListener

	/**
	 * A constructor to construct the hotel for the first time
	 */
	public Hotel()
	{

		roomsInHotel = new ArrayList<Room>();
		usersOfHotel = new ArrayList<User>();
		reservationList = new ArrayList<Reservation>();

		for(int i = 0; i < 10; i++){
			roomsInHotel.add(new Room(i, 80)); // new Reservations() ));
		}

		for(int i = 10; i < 20; i++){
			roomsInHotel.add(new Room(i, 200)); //new Reservations() ));
		}
	}
	/** TODO: implement serialization
	 * A method to save the entire hotel object to an .ser file.
	 */
	public void save() {
		try {
			//This will need to save more.
			File fileToSave = new File("allReservations.txt");

			if (!fileToSave.exists()) {
				fileToSave.createNewFile();
			}

			FileWriter writer = new FileWriter(fileToSave.getAbsoluteFile());

			BufferedWriter buffer = new BufferedWriter(writer);
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			for (Reservation r: reservationList)
			{
				buffer.write(dateFormat.format(r.getStartDate()) + ";");
				buffer.write(dateFormat.format(r.getEndDate()) + ";");
				buffer.write(r.getID() + ";");
				buffer.write(r.getRoomNumber());
				buffer.newLine();

			}
			buffer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * A method to get an iterator for the rooms in the hotel
	 * @return an iterator for the rooms
	 */
	public Iterator<Room> roomIterator() {
		return roomsInHotel.iterator();
	}
	/*---------------------------------------------------
	 * 
	 * Getters
	 * 
	 * ---------------------------------------------------
	 */
	/**
	 * A method to get the rooms 
	 * @return roomsInHotel - the arrayList of rooms
	 */
	public ArrayList<Room> getRooms() {
		return roomsInHotel;
	}
	/**
	 * A method to get an iterator for the users
	 * @return an iterator for the users arrayList
	 */
	public Iterator<User> userIterator() {
		return usersOfHotel.iterator();
	}
	/**
	 * A method to get an iterator for the reservations
	 * @return an iterator for reservations
	 */
	public Iterator<Reservation> reservationIterator() {
		return reservationList.iterator();
	}
	/**
	 * A method to return an iterator for users with multiple reservations
	 * @param iDCheck - the ID to check by
	 * @return an iterator for reservations under same ID
	 */
	public Iterator<Reservation> reservationIterator (int iDCheck) {

		ArrayList<Reservation> reservesUnderSameID = new ArrayList<Reservation>();

		for(Reservation r : reservationList)
		{
			if(r.getID() == iDCheck)
			{
				reservesUnderSameID.add(r);
			}
		}

		return reservesUnderSameID.iterator();
	}
	/**
	 * A method to get an iterator for reservations with dates betwixt s and e
	 * @param s the start date to check against
	 * @param d the end date to check against
	 * @return an iterator for reservations under the same date
	 */
	public Iterator<Reservation> reservationIterator (Date s, Date e) {
		ArrayList<Reservation> sameDate = new ArrayList<Reservation>();

		for(Reservation r : reservationList)
		{
			if(s.before(r.getStartDate()) && e.after(r.getStartDate())) sameDate.add(r);
			else if(s.before(r.getEndDate()) && e.after(r.getEndDate()))sameDate.add(r);
		}
		return sameDate.iterator();
	}
	/**
	 * gets available rooms between dates
	 * @param s the start date of the sought reservation
	 * @param e the end date of the sought reservation
	 * @return a list of rooms without reservations between the dates
	 */
	public ArrayList<Room> getAvailableRooms(Date s, Date e) {
		ArrayList<Room> available = new ArrayList<Room>();
		for(Room room : roomsInHotel)
		{
			if(checkAvailable(room, s, e)) available.add(room);
		}
		return available;
	}
	
	/*---------------------------------------------------
	 * 
	 * Setters
	 * 
	 * ---------------------------------------------------
	 */

	/**
	 * A method to add a room to the list of rooms
	 * @param roomToAdd - the room to add
	 */
	public void addRoom(Room roomToAdd) {
		roomsInHotel.add(roomToAdd);
	}
	/**
	 * A method to add a new user to the arrayList of users
	 * @param newUser - the new user to add
	 */
	public void addUser(User newUser) {
		usersOfHotel.add(newUser);
	}
	/**
	 * A method to add a reservation to the reservationList
	 * @param reserve - the reservation to add
	 */
	public void addReservation(Reservation reserve) {
		reservationList.add(reserve); //add to reservationList

		//sort the reservationList by starting date
		if(reservationList.size() > 1) Collections.sort(reservationList);
		ChangeEvent e = new ChangeEvent(this);
		for(ChangeListener c : listener)
		{
			c.stateChanged(e);
		}
	}

	/**
	 * A method to remove a reservation from the arrayList
	 * @param reserve - the reservation to remove
	 */
	public void removeReservation(Reservation reserve) {
		reservationList.remove(reserve);
	}

	/**
	 * A method to attach a changeListener to the object
	 * @param changeListener - the listener to attach
	 */
	public void attach(ChangeListener changeListener) {
		listener.add(changeListener);
	}

	/**
	 * A method to check if a room is available to Reserve
	 * @param sDate - the starting date
	 * @param eDate - the ending date
	 * @param roomType - the type of room to change
	 */
	private boolean checkAvailable(Room room, Date sDate, Date eDate)
	{
		Iterator<Reservation> iter = reservationIterator(sDate, eDate);

		while(iter.hasNext())
		{
			Reservation reservation = iter.next();
			if(reservation.getRoomNumber() == room.getRoomNumber()) return false;
		}
		return true;
	}

	//writeObject
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