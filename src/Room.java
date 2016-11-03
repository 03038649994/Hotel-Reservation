import java.io.Serializable;
import java.util.Calendar;

/**
 * A class representing a room for the hotel management system
 * @author Karan Bhargava
 * @version 1.2016.991
 *
 */
public class Room implements Serializable {

	//instance variables for a room
	private int cost;
	private int roomNumber;
	//private Reservations

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
	
	public boolean isAvailable(Calendar startingDate, Calendar endingDate) {
		
		//Iterator to loop over reservations
		
		return true;
		
	}
	
	//METHODS FOR getReservation, addReservation, cancelReservation
	
}