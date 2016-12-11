import java.util.Iterator;

/**
 * A class representing a more comprehensive
 * receipt as request (following the strategy pattern)
 */
public class ComprehensiveReceiptFormat implements ReceiptFormatter {

	//instance variables of the receipt
	private Hotel hotel;
	private int total;

	/**
	 * A constructor for the receipt to initialize the 
	 * instance variables
	 * @param h - the hotel we're working with
	 */
	public ComprehensiveReceiptFormat(Hotel h)
	{
		hotel = h;
		total = 0;
	}

	/**
	 * A method to get receipt of a user's booking
	 * in a comprehensive, predefined format
	 * @return receipt - the receipt to return
	 */
	public String format()
	{
		String receipt = hotel.getSelectedUser().toString() + "\n";
		Iterator<Reservation> iter2 = hotel.reservationIterator(hotel.getSelectedUser().getUserID());
		while(iter2.hasNext())
		{
			Reservation temp = iter2.next();
			Room r = hotel.getRooms().get(temp.getRoomNumber());
			receipt += temp.toString() + "\n";

			total += r.getRoomCost() * (temp.getEndDate().getTimeInMillis() - temp.getStartDate().getTimeInMillis()) / (10000 * 60 * 60 * 24);
		}
		return receipt += "The total amount due is: " + total;
	}

}
