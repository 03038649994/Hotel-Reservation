import java.io.*;
import java.text.*;
import java.util.*;

/**
 * A class representing the reservations (with all methods)
 * @author Karan Bhargava
 * @version 1.2016.991
 */
public class Reservations implements Serializable {

	private ArrayList<Reservation> reservationList;

	/**
	 * A constructor to initialize the reservationList arrayList
	 */
	public Reservations() {
		reservationList = new ArrayList<Reservation>();
	}

	/**
	 * A method to add a reservation to the reservationList
	 * @param reserve - the reservation to add
	 */
	public void add(Reservation reserve) {
		reservationList.add(reserve); //add to reservationList

		//sort the reservationList by starting date
		if(reservationList.size() > 1){
			Collections.sort(reservationList, new Comparator<Reservation>(){
				public int compare(Reservation reserve1, Reservation reserve2)
				{
					if(reserve1.getStartDate().after(reserve2.getStartDate())) {
						return  -1;
					}
					else {
						return 1;
					}
				}});
		}
	}

	/**
	 * A method to remove a reservation from the arrayList
	 * @param reserve - the reservation to remove
	 */
	public void cancel(Reservation reserve) {
		reservationList.remove(reserve);
	}

	/**
	 * A method to get an iterator for the reservations
	 * @return an iterator for reservations
	 */
	public Iterator<Reservation> getReservationIterator() {
		return reservationList.iterator();
	}

	/**
	 * A method to return an iterator for users with multiple reservations
	 * @param iDCheck - the ID to check by
	 * @return an iterator for reservations under same ID
	 */
	public Iterator<Reservation> getReserveByUser (int iDCheck) {

		ArrayList<Reservation> reservesUnderSameID = new ArrayList<Reservation>();

		for(Reservation r : reservationList)
		{
			if(r.getID() == iDCheck)
			{
				reservesUnderSameID.add(r);
			}
		}

		return reservesUnderSameID.iterator();
	}

	/**
	 * A method to get an iterator for reservations with the same date
	 * @param d - the date to check
	 * @return an iterator for reservations under the same date
	 */
	public Iterator<Reservation> getReservationByDate (Date d) {
		ArrayList<Reservation> reservesUnderSameDate = new ArrayList<Reservation>();

		for(Reservation r : reservationList)
		{
			if(d.before(r.getStartDate()) || d.after(r.getendDate())) {
				continue;
			}
			reservesUnderSameDate.add(r);
		}
		return reservesUnderSameDate.iterator();
	}

	/**
	 * A method to try and save all the reservations in a text file
	 */
	public void save() {
		try {
			File fileToSave = new File("allReservations.txt");

			if (!fileToSave.exists()) {
				fileToSave.createNewFile();
			}

			FileWriter writer = new FileWriter(fileToSave.getAbsoluteFile());
			
			BufferedWriter buffer = new BufferedWriter(writer);
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			for (Reservation r: reservationList)
			{
				buffer.write(dateFormat.format(r.getStartDate()) + ";");
				buffer.write(dateFormat.format(r.getendDate()) + ";");
				buffer.write(r.getID() + ";");
				buffer.write(r.getRoomNumber());
				buffer.newLine();

			}
			buffer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

