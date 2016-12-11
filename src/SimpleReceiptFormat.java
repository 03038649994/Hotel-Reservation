/**
 * A simple receipt format following the strategy pattern
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
			totalOfTrans += room.getRoomCost() * (reservation.getEndDate().getTimeInMillis() - reservation.getStartDate().getTimeInMillis()) / (10000 * 60 * 60 * 24);
		}
		return receipt += "Total amount due is: " + totalOfTrans;
	}
}