import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;

/**
 * A class representing a room for the hotel management system
 * @author Karan Bhargava
 * @version 1.2016.991
 *
 */
public class Room implements Serializable
{
	private static final long serialVersionUID = 1L;
	//instance variables for a room
	private int cost;
	private int roomNumber;
	private Hotel reservationHotel;

	/**
	 * A constructor to construct a room
	 * @param number - the room number
	 * @param roomCost - the cost of the room
	 */
	public Room(int number, int roomCost) {
		roomNumber = number;
		cost = roomCost;
	}

	/**
	 * A method to get the room number
	 * @return roomNumber - the room number
	 */
	public int getRoomNumber() {
		return roomNumber;
	}
	
	/**
	 * A method to get the room cost
	 * @return cost - the room cost
	 */
	public int getRoomCost() {
		return cost;
	}
	
	/**
	 * A method to get the room number and price in a String format
	 * @return a string with the room number and it's price
	 */
	public String toString() {
		return "Room # " + getRoomNumber() + "Price: " + getRoomCost();
	}
	
	/**
	 * A method to check what kind of room this is
	 * Either a luxurious or economic
	 * @param whatType - an int representing the cost of the room
	 * @return a boolean true or false depending on the type of room
	 */
	public boolean whatType(int whatType) {
		return cost == whatType;
	}
	
	/**
	 * A method to check if a room is available
	 * @param startingDate - the starting date to check
	 * @param endingDate - the ending date to check
	 * @return true if room is available; false otherwise
	 */
	public boolean isAvailable(Calendar startingDate, Calendar endingDate) {
		
		Iterator<Reservation> iter = reservationHotel.reservationIterator();
		while (iter.hasNext()) {
			Reservation temp = iter.next();
			Calendar startDate = temp.getStartDate();
			Calendar endDate = temp.getEndDate();
			
			if( ((startDate.after(startingDate.getTime()) || startDate.equals(startingDate.getTime())) && 
					(startDate.before(endingDate.getTime())) || startDate.equals(endingDate.getTime())) || 
					((endDate.after(startingDate.getTime()) || endDate.equals(startingDate.getTime())) && 
					(endDate.before(endingDate.getTime()) || endDate.equals(endingDate.getTime())))) {
				
				return false;
			}
		}
		return true;
	}
	
	/**
	 * A method to get the reservations from the hotel
	 * @return an iterator that loops over the reservations
	 */
	public Iterator<Reservation> getReservations() {
		return reservationHotel.reservationIterator();
	}
	
	/**
	 * An overloaded method to get reservations from hotel
	 * by userID
	 * @param iD - the ID to check by
	 * @return an iterator that loops over the reservations
	 */
	public Iterator<Reservation> getReservationByUser(int iD) {
		return reservationHotel.reservationIterator(iD);
	}
	
}