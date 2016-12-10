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
import java.util.GregorianCalendar;
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
	private static final long serialVersionUID = 1L;
	private ArrayList<Room> roomsInHotel; //total number of rooms
	private ArrayList<User> usersOfHotel; //users of hotel
	private ArrayList<Reservation> reservationList;

	private Calendar selectedDate; private Calendar selectedDate2;
	private Room selectedRoom;
	private User selectedUser; //the logged in user for Guest View
	private int selectedRoomType; //80 is econ, 200 is lux
	
	private ArrayList<Reservation> mostRecentRes;
	
	private transient ArrayList<ChangeListener> listeners = new ArrayList<ChangeListener>(); //changeListener

	/**
	 * A constructor to construct the hotel for the first time
	 */
	public Hotel()
	{
		roomsInHotel = new ArrayList<Room>();
		usersOfHotel = new ArrayList<User>();
		reservationList = new ArrayList<Reservation>();
		mostRecentRes = new ArrayList<Reservation>();

		for(int i = 0; i < 10; i++){
			roomsInHotel.add(new Room(i, 80));
		}

		for(int i = 10; i < 20; i++){
			roomsInHotel.add(new Room(i, 200));
		}
		selectedDate = new GregorianCalendar(); selectedDate2 = new GregorianCalendar();
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
	 * Gets the currently selected date for the MonthView
	 * @return a calendar representing the selected date
	 */

	/*---------------------------------------------------
	 * 
	 * Getters
	 * 
	 * ---------------------------------------------------
	 */
	/**
	 * Finds the most recently added reservation
	 * @return a reservation
	 */
	public ArrayList<Reservation> getMostRecentReservations(){return mostRecentRes;}
	/**
	 * Finds the cost of the room type sought on the MakeReservation view
	 * @return the cost, 80 = econ, 200 = lux
	 */
	public int getSelectedRoomType(){return selectedRoomType;}
	/**
	 * returns the entire selected date
	 * @return the selected date
	 */
	public Calendar getSelectedDate(){return selectedDate;}
	/**
	 * returns the 2nd selected date for the Guest View
	 * @return the selected date
	 */
	public Calendar getSelectedDate2(){return selectedDate2;}
	/**
	 * Gets the selected day for the month view
	 * @return an integer representing the day of month
	 */
	public int getSelectedDay(){return selectedDate.get(Calendar.DATE);}
	/**
	 * Finds the first day of the month, Sunday = 0
	 * Used in the MonthGrid class
	 * @return an integer representing the day
	 */
	public int getFirstDay()
	{
		GregorianCalendar temp = new GregorianCalendar(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), 0);
		if(temp.get(Calendar.DAY_OF_WEEK) < 7) return temp.get(Calendar.DAY_OF_WEEK);
		return 0;
	}
	/**
	 * Finds the last day of the month (30, 31, 28)
	 * @return the numerical last day of the month
	 */
	public int getLastDay()
	{
		if(selectedDate.get(Calendar.MONTH) < 7 && selectedDate.get(Calendar.MONTH) != 1) return (selectedDate.get(Calendar.MONTH)%2==0) ? 31 : 30;
		else if(selectedDate.get(Calendar.MONTH) >= 7) return (selectedDate.get(Calendar.MONTH)%2==0) ? 30 : 31; //needs: leap years
		return 28;
	}
	/**
	 * Gets the currently selected room for the RoomView
	 * @return the selected room
	 */
	public Room getSelectedRoom(){return selectedRoom;}
	/**
	 * Gets the currently logged in user for the guest view
	 * @return the logged in user
	 */
	public User getSelectedUser(){return selectedUser;}
	/**
	 * Gets an iterator for all the rooms
	 * @return iterator for rooms
	 */
	public Iterator<Room> roomIterator() {return roomsInHotel.iterator();}
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
	public Iterator<User> userIterator() {return usersOfHotel.iterator();}
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
	 * A method to return an iterator for rooms with multiple reservations
	 * @param Room the room with sought reservations
	 * @return an iterator for reservations under same Room
	 */
	public Iterator<Reservation> reservationIterator (Room room) {

		ArrayList<Reservation> reserves = new ArrayList<Reservation>();

		for(Reservation r : reservationList)
		{
			if(r.getRoomNumber() == room.getRoomNumber())
			{
				reserves.add(r);
			}
		}
		return reserves.iterator();
	}
	/**
	 * A method to get an iterator for reservations that contain the date s, any room
	 * @param s the single date to check against
	 * @return an iterator for reservations under the same date
	 */
	public Iterator<Reservation> reservationIterator(Calendar s)
	{
		return reservationIterator(s, (Room) null);
	}
	/**
	 * A method to get an iterator for reservations on date s in room e
	 * @param s the start date to check against
	 * @param e the room to search against
	 * @return an iterator for reservations under the same date
	 */
	public Iterator<Reservation> reservationIterator (Calendar s, Room e)
	{
		ArrayList<Reservation> sameDate = new ArrayList<Reservation>();
		s.set(Calendar.MILLISECOND, 0);

		for(Reservation r : reservationList)
		{
			Calendar start = r.getStartDate();
			Calendar end = r.getEndDate();
			start.set(Calendar.MILLISECOND, 0);
			end.set(Calendar.MILLISECOND, 0);

			if((start.before(s) || start.equals(s)) && (end.after(s) || end.equals(s)))
			{
				if(e == null) sameDate.add(r);
				else if(r.getRoomNumber() == e.getRoomNumber()) sameDate.add(r);
			}
		}
		return sameDate.iterator();
	}
	/**
	 * A method to get an iterator for reservations with dates betwixt s and e
	 * @param s the start date to check against
	 * @param e the end date to check against
	 * @return an iterator for reservations under the same date
	 */
	public Iterator<Reservation> reservationIterator (Calendar start, Calendar end)
	{
		return reservationIterator(start, end, null);
	}
	/**
	 * A method to get an iterator for reservations with dates betwixt s and e
	 * @param s the start date to check against
	 * @param e the end date to check against
	 * @return an iterator for reservations under the same date
	 */
	public Iterator<Reservation> reservationIterator (Calendar start, Calendar end, Room e)
	{
		ArrayList<Reservation> sameDate = new ArrayList<Reservation>();
		start.set(Calendar.MILLISECOND, 0);
		end.set(Calendar.MILLISECOND, 0);

		for(Reservation r : reservationList)
		{
			Calendar rStart = r.getStartDate();
			Calendar rEnd = r.getEndDate();
			rStart.set(Calendar.MILLISECOND, 0);
			rEnd.set(Calendar.MILLISECOND, 0);

			if((start.before(rStart) || start.equals(rStart)) && (end.after(rEnd) || end.equals(rEnd))
					|| (start.before(rEnd) || start.equals(rEnd)) && (end.after(rEnd) || end.equals(rEnd)))
			{
				if(e == null) sameDate.add(r);
				else if(r.getRoomNumber() == e.getRoomNumber()) sameDate.add(r);
			}
		}
		return sameDate.iterator();
	}
	/**
	 * gets available rooms on the date
	 * @param s the date of the sought reservation
	 * @return a list of rooms without reservations between the dates
	 */
	public ArrayList<Room> getAvailableRooms(Calendar s)
	{
		ArrayList<Room> available = new ArrayList<Room>();
		for(Room room : roomsInHotel)
		{
			if(checkAvailable(room, s)) available.add(room);
		}
		return available;
	}
	/**
	 * gets available rooms betwixt the dates
	 * @param s the start date of available rooms
	 * @param e the end date of available rooms
	 * @return a list of rooms without reservations between the dates
	 */
	public ArrayList<Room> getAvailableRooms(Calendar s, Calendar e)
	{
		return getAvailableRooms(s, e, -1);
	}
	/**
	 * gets available rooms betwixt the dates, of the specific room type
	 * @param s the start date of available rooms
	 * @param e the end date of available rooms
	 * @return a list of rooms without reservations between the dates
	 */
	public ArrayList<Room> getAvailableRooms(Calendar s, Calendar e, int roomType)
	{
		ArrayList<Room> available = new ArrayList<Room>();
		for(Room room : roomsInHotel)
		{
			if(checkAvailable(room, s, e))
			{
				if(roomType == -1) available.add(room);
				else if(room.getRoomCost()==roomType) available.add(room);
			}
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
	 * Intended for the MakeReservation view
	 * @param cost the cost that determines the type of room
	 */
	public void setSelectedRoomType(int cost){selectedRoomType = cost; update();}
	/**
	 * called by mutators of this model to inform all views of state changes
	 */
	private void update(){for(ChangeListener c: listeners){c.stateChanged(new ChangeEvent(this));}}
	/**
	 * This scrolls the selected date back by 1 year
	 * PRECONDITION: ??
	 * POSTCONDITION: ??
	 */
	public void yesteryear(){selectedDate.add(Calendar.YEAR, -1); update();}
	/**
	 * This scrolls the selected date back by 1 month
	 * PRE: ??
	 * POST: ??
	 */
	public void yestermonth(){selectedDate.add(Calendar.MONTH, -1); update();}
	/**
	 * This scrolls the selected date forth by 1 year
	 * PRECONDITION: ??
	 * POSTCONDITION: ??
	 */
	public void nextYear(){selectedDate.add(Calendar.YEAR, 1); update();}
	/**
	 * This scrolls the selected date forth by 1 month
	 * PRECONDITION: ??
	 * POSTCONDITION: ??
	 */
	public void nextMonth(){selectedDate.add(Calendar.MONTH, 1); update();}
	/**
	 * a method to set the selected date for the month view
	 * @param day the numerical representation of the day of month
	 * Precondition: ???
	 * Postcondition: ???
	 */
	public void setSelectedDay(int day)
	{
		selectedDate.set(Calendar.DATE, day);
		selectedRoom = null;
		update();
	}
	/**
	 * sets the selected date to a specific date
	 * @param date the date to select
	 */
	public void setSelectedDate(Calendar date)
	{
		selectedDate = date;
		if(selectedDate2.before(selectedDate))
		{
			selectedDate2 = new GregorianCalendar(
					selectedDate.get(Calendar.YEAR),
					selectedDate.get(Calendar.MONTH),
					selectedDate.get(Calendar.DAY_OF_MONTH)
					);
		}
		update();
	}
	/**
	 * sets the selected 2nd date (for the guest view) to a specific date
	 * @param date the date to select
	 */
	public void setSelectedDate2(Calendar date){selectedDate2 = date; update();}
	/**
	 * a method to set the selected room for the room view
	 * @param room the chosen room
	 * Precondition: ???
	 * Postcondition: ???
	 */
	public void setSelectedRoom(Room room){selectedRoom = room; update();}
	/**
	 * a method to set the logged in user for the guest view
	 * @param User the user logging in
	 * Precondition: the user must be in the hotel data structure
	 * Postcondition: ???
	 */
	public void setSelectedUser(User user){selectedUser = user; update();}
	/**
	 * A method to add a room to the list of rooms
	 * @param roomToAdd - the room to add
	 */
	public void addRoom(Room roomToAdd) {roomsInHotel.add(roomToAdd); update();}
	/**
	 * A method to add a new user to the arrayList of users
	 * @param newUser - the new user to add
	 */
	public void addUser(User newUser) {usersOfHotel.add(newUser); update();}
	/**
	 * A method to add a reservation to the reservationList
	 * @param reserve - the reservation to add
	 */
	public void addReservation(Reservation reserve)
	{
		mostRecentRes.add(reserve);
		reservationList.add(reserve); //add to reservationList
		if(reservationList.size() > 1) Collections.sort(reservationList); //sort the reservationList by starting date
		update();
	}
	/**
	 * a method to clear the buffer of recent reservations
	 */
	public void flushRecentRes(){mostRecentRes = new ArrayList<Reservation>();}
	/**
	 * A method to remove a reservation from the arrayList
	 * @param reserve - the reservation to remove
	 */
	public void removeReservation(Reservation reserve) {reservationList.remove(reserve); update();}

	/**
	 * A method to attach a changeListener to the object
	 * @param changeListener - the listener to attach
	 */
	public void attach(ChangeListener changeListener) {listeners.add(changeListener);}

	/**
	 * A method to check if a room is available to Reserve
	 * @param sDate - the date of availability
	 * @return a boolean indicating availability
	 */
	private boolean checkAvailable(Room room, Calendar sDate)
	{
		Iterator<Reservation> iter = reservationIterator(sDate);

		while(iter.hasNext())
		{
			Reservation reservation = iter.next();
			if(reservation.getRoomNumber() == room.getRoomNumber()) return false;
		}
		return true;
	}
	/**
	 * A method to check if a room is available to Reserve for all days between dates
	 * @param sDate - the starting date
	 * @param eDate - the ending date
	 * @return a boolean indicating availability
	 */
	private boolean checkAvailable(Room room, Calendar sDate, Calendar eDate)
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
		listeners = new ArrayList<ChangeListener>();
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
}