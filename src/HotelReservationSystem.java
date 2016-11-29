import java.util.Calendar;
import java.util.GregorianCalendar;

public class HotelReservationSystem
{
	public static void main (String[] args)
	{
		Hotel hotel = new Hotel();
		hotel.addUser(new User(0, "Bob", "Jones"));
		Calendar day2 = new GregorianCalendar();
		day2.add(Calendar.DATE, 1);
		
		hotel.addReservation(new Reservation(0, new GregorianCalendar(), day2, 0, hotel.getRooms().get(0)));
		CalendarMenu cm = new CalendarMenu(hotel);
		cm.setVisible(true);
	}
}