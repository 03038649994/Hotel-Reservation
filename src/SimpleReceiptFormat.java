import java.util.Iterator;

/**
 * A simple receipt format following the strategy pattern
 * @author Karan Bhargava
 * @version 1.2016.991
 *
 */
public class SimpleReceiptFormat implements ReceiptFormatter {
	
	private Hotel hotel;
	private User guest;
	private int transID;
	private int totalOfTrans;
	
	
	/**
	 * A constructor to initialize the simple receipt instance variables
	 * @param h - the hotel we're working with
	 * @param g - the guest we're working with
	 * @param iD - the transactionID 
	 */
	public SimpleReceiptFormat(Hotel h, User g, int iD) {
		hotel = h;
		guest = g;
		transID = iD;
		totalOfTrans = 0;
	}
	
	/**
	 * A method to get receipt of a user's booking
	 * in a simple, predefined format
	 * @return receipt - the receipt to return
	 */
	public String format() {
		String receipt = guest.toString() + "\n";
		
		Iterator<Room> iter = hotel.roomIterator();
		
		while (iter.hasNext()) {
			Room r = iter.next();
			Iterator<Reservation> iter2 = r.getReservationByUser(guest.getUserID());
			
			while (iter2.hasNext()) {
				Reservation temp = iter2.next();
				if(temp.getTransactionID() == transID) {
					 receipt+=("Room #" + r.getRoomNumber() + '\n');
					 totalOfTrans += r.getRoomCost() * (temp.getEndDate().getTime() - temp.getStartDate().getTime()) / (1000 * 60 * 60 * 24);
				}
			}
		}		
		return receipt += "Total amount due is: " + totalOfTrans;
	}
}