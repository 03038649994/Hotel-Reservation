import java.util.Calendar;
import java.util.GregorianCalendar;

public class HotelReservationSystem
{
	public static void main (String[] args)
	{
		Hotel hotel = new Hotel();
		hotel.addUser(new User(0, "Bob", "Jones"));
		hotel.addUser(new User(1, "Mary", "Todd"));
		hotel.addUser(new User(2, "Samurai", "Jack"));
		hotel.addUser(new User(3, "Wu", "Zetian"));
		
		Calendar day1 = new GregorianCalendar();
		Calendar day2 = new GregorianCalendar(); day2.add(Calendar.DATE, 1);
		Calendar day3 = new GregorianCalendar(); day3.add(Calendar.DATE, 4);
		
		hotel.addReservation(new Reservation(0, day1, day2, 0, hotel.getRooms().get(0)));
		hotel.addReservation(new Reservation(0, day1, day2, 1, hotel.getRooms().get(2)));
		hotel.addReservation(new Reservation(0, day1, day3, 2, hotel.getRooms().get(3)));
		hotel.addReservation(new Reservation(0, day1, day2, 3, hotel.getRooms().get(4)));
		
		CalendarMenu cm = new CalendarMenu(hotel);
		cm.setVisible(true);
	}
}