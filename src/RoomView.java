import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A class representing the room view for the manager
 *@author Matthew Binning
 *@version 11.5818.221
 *
 */
public class RoomView extends JPanel
{
	private static final long serialVersionUID = 1L;
	/**
	 * A constructor to construct the room view for the manager
	 * @param h - the hotel we're working with
	 */
	public RoomView(Hotel h)
	{
		setMaximumSize(new Dimension(500, 200));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(new RoomPanel(h));
		add(new RoomInfoPanel(h));
	}

	/**
	 * A nested class representing the information panel for the rooms
	 *@author Matthew Binning
	 *@version 11.5818.221
	 *
	 */
	private static class RoomPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		/**
		 * A constructor to construct the information panel
		 * @param h - the hotel we're working with
		 */
		public RoomPanel(Hotel h)
		{
			setMaximumSize(new Dimension(215, 200));
			setLayout(new GridLayout(0,3,0,0));

			List<Room> rooms = h.getRooms();
			for(Room room : rooms)
			{
				JButton roomBtn = new JButton("R"+room.getRoomNumber());
				roomBtn.addActionListener(new
						ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						h.setSelectedRoom(room);
					}

				});
				add(roomBtn);
			}
		}
	}

	/**
	 * A class populating all the information inside the room information panel
	 *@author Matthew Binning
	 *@version 11.5818.221
	 *
	 */
	private static class RoomInfoPanel extends JPanel implements ChangeListener
	{
		private static final long serialVersionUID = 1L;
		private JTextArea roomInfoText;
		private Hotel h;
		
		/**
		 * A constructor to initialize and populate the information panel
		 * @param h - the hotel we're working with
		 */
		public RoomInfoPanel(Hotel h)
		{
			this.h = h;
			h.attach(this);
			setMaximumSize(new Dimension(285, 200));
			roomInfoText = new JTextArea(100, 20);
			roomInfoText.setLineWrap(true);
			roomInfoText.setEnabled(false);
			stateChanged(new ChangeEvent(this));
			add(roomInfoText);
		}

		@Override
		public void stateChanged(ChangeEvent arg0)
		{
			if(h.getSelectedRoom()==null)
			{
				roomInfoText.setText("Click a room button for details.\n");
			}
			else
			{
				Iterator<Reservation> r = h.reservationIterator(h.getSelectedDate(), h.getSelectedRoom());
				if(r.hasNext())
				{
					roomInfoText.setText("Room " + h.getSelectedRoom().getRoomNumber() + " Details (Confidential)\n");
				}
				else
				{
					if(h.getSelectedRoom().whatType(80)) roomInfoText.setText("This economic room is available.");
					if(h.getSelectedRoom().whatType(200)) roomInfoText.setText("This luxury room is available.");
				}
				while(r.hasNext())
				{
					if(h.getSelectedRoom().whatType(80)) roomInfoText.append("\nRoom type: Economic");
					else if (h.getSelectedRoom().whatType(200)) roomInfoText.append("\nRoom type: Luxury");
					Reservation res = r.next();
					roomInfoText.append("\nTennant name: "+h.findUserByID(res.getID()).getUserName() +"\n");
					roomInfoText.append("Tennant ID: "+res.getID() +"\n");

					Calendar start = res.getStartDate(); Calendar end = res.getEndDate();
					int day1 = start.get(Calendar.DATE); int day2 = end.get(Calendar.DATE);
					int month1 = start.get(Calendar.MONTH)+1; int month2 = end.get(Calendar.MONTH)+1;
					int year1 = start.get(Calendar.YEAR); int year2 = end.get(Calendar.YEAR);
					long startTime = res.getStartDate().getTimeInMillis();
					long endTime = res.getEndDate().getTimeInMillis();
					int duration = (int) TimeUnit.MILLISECONDS.toDays(Math.abs(endTime - startTime));
					roomInfoText.append("Start date: "+month1+"/"+day1+"/"+year1+"\n");
					roomInfoText.append("End date: "+month2+"/"+day2+"/"+year2+"\n");
					roomInfoText.append("Duration of stay: "+duration+" "+((duration>1)? "nights" : "night")+"\n");
				}
			}
		}
	}
}
