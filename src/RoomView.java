import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RoomView extends JPanel
{
	private static final long serialVersionUID = 1L;
	public RoomView(Hotel h)
	{
		setMaximumSize(new Dimension(500, 200));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(new RoomPanel(h));
		add(new RoomInfoPanel(h));
	}
	private static class RoomPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		public RoomPanel(Hotel h)
		{
			setMaximumSize(new Dimension(225, 200));
			setLayout(new GridLayout(0,3,0,0));

			List<Room> rooms = h.getRooms();
			for(Room room : rooms)
			{
				JButton roomBtn = new JButton("Room "+room.getRoomNumber());
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
	private static class RoomInfoPanel extends JPanel implements ChangeListener
	{
		private static final long serialVersionUID = 1L;
		private JTextArea roomInfoText;
		private JScrollBar roomScrollBar;
		private Hotel h;
		public RoomInfoPanel(Hotel h)
		{
			this.h = h;
			h.attach(this);
			setMaximumSize(new Dimension(300, 200));
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
				roomInfoText.setText("Click a room for details.\n");
				
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
					roomInfoText.setText("Room is available");
				}
				while(r.hasNext())
				{
					Reservation res = r.next();
					roomInfoText.append("Tennant name: "+h.findUserByID(res.getID()).getUserName() +"\n");
					roomInfoText.append("Tennant ID: "+res.getID() +"\n");

					Calendar start = res.getStartDate(); Calendar end = res.getEndDate();
					int day1 = start.get(Calendar.DATE); int day2 = end.get(Calendar.DATE);
					int month1 = start.get(Calendar.MONTH)+1; int month2 = end.get(Calendar.MONTH)+1;
					int year1 = start.get(Calendar.YEAR); int year2 = end.get(Calendar.YEAR);
					int duration = end.get(Calendar.DAY_OF_YEAR)-start.get(Calendar.DAY_OF_YEAR);
					roomInfoText.append("Start date: "+month1+"/"+day1+"/"+year1+"\n");
					roomInfoText.append("End date: "+month2+"/"+day2+"/"+year2+"\n");
					roomInfoText.append("Duration of stay: "+duration+" "+((duration>1)? "nights" : "night")+"\n");
					roomInfoText.append("Transaction ID: "+res.getTransactionID() +"\n");
				}
			}
		}
	}
}
