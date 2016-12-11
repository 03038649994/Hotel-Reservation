import java.util.concurrent.TimeUnit;

/**
 * A simple receipt format following the strategy pattern
 * @author Matthew Binning (originally Karan Bhargava)
 * @version 1.2016.991
 *
 */
public class SimpleReceiptFormat implements ReceiptFormatter
{	
	private Hotel hotel;
	private int totalOfTrans;
	
	/**
	 * A constructor to initialize the simple receipt instance variables
	 * @param h - the hotel we're working with
	 * @param g - the guest we're working with
	 * @param iD - the transactionID 
	 */
	public SimpleReceiptFormat(Hotel h)
	{
		hotel = h;
		totalOfTrans = 0;
	}

	/**
	 * A method to get receipt of a user's booking
	 * in a simple, predefined format
	 * @return receipt - the receipt to return
	 */
	public String format()
	{
		String receipt = hotel.getSelectedUser().toString() + "\n";
		for(Reservation reservation : hotel.getMostRecentReservations())
		{
			Room room = hotel.getRooms().get(reservation.getRoomNumber());
			receipt += ("Room #: " + room.getRoomNumber() + '\n');
			long startTime = reservation.getStartDate().getTimeInMillis();
			long endTime = reservation.getEndDate().getTimeInMillis();
			totalOfTrans += room.getRoomCost() * (TimeUnit.MILLISECONDS.toDays(Math.abs(endTime - startTime)));
		}
		return receipt += "Total amount due is: " + totalOfTrans;
	}
}