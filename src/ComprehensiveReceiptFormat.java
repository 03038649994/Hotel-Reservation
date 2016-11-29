import java.util.Iterator;

/**
 * A class representing a more comprehensive
 * receipt as request (following the strategy pattern)
 * @author Karan Bhargava
 * @version 1.2016.991
 */
public class ComprehensiveReceiptFormat implements ReceiptFormatter {

	//instance variables of the receipt
	private Hotel hotel;
	private User guest;
	private int total;

	/**
	 * A constructor for the receipt to initialize the 
	 * instance variables
	 * @param h - the hotel we're working with
	 * @param g - the guest we're working with
	 */
	public ComprehensiveReceiptFormat(Hotel h, User g) {
		hotel = h;
		guest = g;
		total = 0;
	}

	/**
	 * A method to get receipt of a user's booking
	 * in a comprehensive, predefined format
	 * @return receipt - the receipt to return
	 */
	public String format() {
		String receipt = guest.toString() + "\n";
		Iterator<Room> iter = hotel.roomIterator();
		while(iter.hasNext()) {
			Room r = iter.next();
			Iterator<Reservation> iter2 = r.getReservationByUser(guest.getUserID());
			
			while(iter2.hasNext()) {
				Reservation temp = iter2.next();
				receipt += temp.toString() + "\n";
				
				total += r.getRoomCost() * (temp.getEndDate().getTimeInMillis() - temp.getStartDate().getTimeInMillis()) / (1000 * 60 * 60 * 24);
			}
		}
		return receipt += "The total amount due is: " + total;
	}

}
