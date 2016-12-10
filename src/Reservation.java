import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A class representing an arrayList form for a reservation
 * @author Karan Bhargava
 * @version 1.2016.991
 */
public class Reservation implements Serializable, Comparable<Reservation>
{
	private static final long serialVersionUID = 1L;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
	private Calendar startDate;
	private Calendar endDate;
	private int userID;
	private Room room;

	/**
	 * A constructor to construct a reservation
	 * @param transID - the transaction ID
	 * @param sDate - the start date
	 * @param eDate - the end date
	 * @param iD - the user's ID
	 * @param userRoom - the Room we're working with
	 */
	public Reservation(Calendar sDate, Calendar eDate, int iD, Room userRoom)
	{
		startDate = sDate;
		endDate = eDate;
		userID = iD;
		room = userRoom;
	}

	/**
	 * An overloaded constructor to reset the reservation
	 * @param sDate - the start date
	 * @param eDate - the end date
	 * @param iD - the ID of the user
	 */
	public Reservation(Calendar sDate, Calendar eDate, int iD)
	{
		startDate = sDate;
		endDate = eDate;
		userID = iD;
	}

	/**
	 * A getter method to get the user's ID
	 * @return userID - the user's ID
	 */
	public int getID() {
		return userID;
	}

	/**
	 * A getter method to get the room number
	 * @return the room number
	 */
	public int getRoomNumber() {
		return room.getRoomNumber();
	}

	/**
	 * A getter method to get the starting date of the reservation
	 * @return startDate - the starting date
	 */
	public Calendar getStartDate() {
		return startDate;
	}

	/**
	 * A getter method to get the ending date of the reservation
	 * @return endDate - the ending date
	 */
	public Calendar getEndDate() {
		return endDate;
	}

	/**
	 * A getter method to get the room's cost
	 * @return the room cost
	 */
	public int getPrice() {
		return room.getRoomCost();
	}

	/**
	 * A method to get the contents of the reservation as a String
	 * @return a string with the reservation data
	 */
	public String toString() {
		return "Room #" + getRoomNumber() + " check in: " + dateFormat.format(startDate) + " check out: " + dateFormat.format(endDate);
	}

	@Override
	public int compareTo(Reservation other)
	{
		if(getStartDate().after(other.getStartDate())) {
			return  -1;
		}
		else {
			return 1;
		}
	}
}