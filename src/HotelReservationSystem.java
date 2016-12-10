import java.util.Calendar;
import java.util.GregorianCalendar;

public class HotelReservationSystem
{
	public static void main (String[] args)
	{
		Hotel hotel = new Hotel();
		hotel.addUser(new User(0001, "Bob"));
		hotel.addUser(new User(0002, "Mary"));
		hotel.addUser(new User(0003, "Samurai"));
		hotel.addUser(new User(0004, "Wu"));
		
		Calendar day1 = new GregorianCalendar();
		Calendar day2 = new GregorianCalendar(); day2.add(Calendar.DATE, 1);
		Calendar day3 = new GregorianCalendar(); day3.add(Calendar.DATE, 4);
		
		hotel.addReservation(new Reservation(0, day1, day2, 0001, hotel.getRooms().get(0)));
		hotel.addReservation(new Reservation(0, day1, day2, 0002, hotel.getRooms().get(2)));
		hotel.addReservation(new Reservation(0, day1, day3, 0003, hotel.getRooms().get(3)));
		hotel.addReservation(new Reservation(0, day1, day2, 0004, hotel.getRooms().get(4)));
		
		CalendarMenu cm = new CalendarMenu(hotel);
		cm.setVisible(true);
	}
}