
public class HotelReservationSystem
{
	public static void main (String[] args)
	{
		Hotel hotel = new Hotel();
		hotel.addUser(new User(0, "Bob", "Jones"));
		
		CalendarMenu cm = new CalendarMenu();
		cm.setVisible(true);
	}
}