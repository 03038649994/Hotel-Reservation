/**
 * A class representing the main program to run the project
 * @author Karan Bhargava
 * @version 1.2016.991
 *
 */
public class HotelReservationSystem
{
	public static void main (String[] args)
	{
		//initialize hotel
		Hotel hotel = new Hotel();
		//start the GUI
		CalendarMenu cm = new CalendarMenu(hotel);
		cm.setVisible(true);
		cm.setResizable(false);
	}
}