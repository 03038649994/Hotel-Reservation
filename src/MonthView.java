import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A class representing the month view of the reservations inside the manager
 *@author Matthew Binning
 *@version 11.5818.221
 *
 */
public class MonthView extends JPanel
{
	//instance variables of the monthView
	private static final long serialVersionUID = 1L;

	/**
	 * A constructor to construct the monthView panel
	 * @param h - the hotel we're working with
	 */
	public MonthView(Hotel h)
	{
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setMaximumSize(new Dimension(500, 250));
		add(new MonthPanel(h));
		add(new MonthInfoPanel(h));
	}

	/**
	 * A class representing the GUI for the month view
	 *@author Matthew Binning
	 *@version 11.5818.221
	 *
	 */
	private static class MonthPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		/**
		 * A constructor to construct the JPanel for the monthView
		 * @param h - the hotel we're working with
		 */
		public MonthPanel(Hotel h)
		{
			setMaximumSize(new Dimension(220, 250));
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			ButtonController buttonController = new ButtonController(h);
			add(buttonController);

			MonthLabel monthLabel = new MonthLabel(h);
			add(monthLabel);
			h.attach(monthLabel);

			MonthGrid monthGrid = new MonthGrid(h);
			add(monthGrid);
		}

		/*
		 * --------------------------------------------------
		 * Nested for the sake of brevity of files
		 * Class representing the buttons on the month view panel
		 * @author Matthew Binning
		 * @version 11.5818.221
		 * --------------------------------------------------
		 */
		public static class ButtonController extends JPanel
		{
			private static final long serialVersionUID = 1L;

			/**
			 * A constructor to construct the buttons of the month view
			 * @param h - the hotel we're working with
			 */
			public ButtonController(Hotel h)
			{
				setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

				JButton yesteryear = new JButton("<<");
				yesteryear.addActionListener(new
						ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0){h.yesteryear();}
				});

				JButton yestermonth = new JButton("<");
				yestermonth.addActionListener(new
						ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {h.yestermonth();}
				});

				JButton nextmonth = new JButton(">");
				nextmonth.addActionListener(new
						ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {h.nextMonth();}
				});
				JButton nextyear = new JButton(">>");
				nextyear.addActionListener(new
						ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e){h.nextYear();
					}
				});

				add(yesteryear);
				add(yestermonth);
				add(nextmonth);
				add(nextyear);
			}
		}
		/**
		 * A class representing the labels for the month view
		 *@author Matthew Binning
		 *@version 11.5818.221
		 *
		 */
		public static class MonthLabel extends JLabel implements ChangeListener
		{
			private static final long serialVersionUID = 1L;
			/**
			 * An enum representing all the months of the year
			 *@author Matthew Binning
			 *@version 11.5818.221
			 */
			enum MONTHS
			{
				January, February, March, April, May, June, July, August, September, October, November, December;
			}
			private Hotel h;

			/**
			 * A constructor to construct the required labels for the month view
			 * @param h - the hotel we're working with
			 */
			public MonthLabel(Hotel h)
			{
				this.h = h;
				setText(h.getSelectedDate().get(Calendar.DATE) + " " + MONTHS.values()[(h.getSelectedDate().get(Calendar.MONTH))] + " " + h.getSelectedDate().get(Calendar.YEAR));
			}
			@Override
			public void stateChanged(ChangeEvent e)
			{
				setText(h.getSelectedDate().get(Calendar.DATE) + " " + MONTHS.values()[(h.getSelectedDate().get(Calendar.MONTH))] + " " + h.getSelectedDate().get(Calendar.YEAR));
				repaint();
			}

		}

		/**
		 * A class representing the grid the buttons are housed in for the month view
		 *@author Matthew Binning
		 *@version 11.5818.221
		 *
		 */
		public static class MonthGrid extends JPanel implements ChangeListener
		{
			private static final long serialVersionUID = 1L;
			Hotel h;
			/*
			 * These three fields are used to track updates to the starting day of the month
			 */
			private SelectableViewController[] SVCs;
			private int firstDay;
			private int lastDay;

			/**
			 * An enum representing the days of the week
			 *@author Matthew Binning
			 *@version 11.5818.221
			 */
			enum DAYS
			{
				Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
			}
			/**
			 * iterate through model, add boxShape class
			 * these may start at different days of week
			 * also, some day of the month is selected
			 * 
			 * A constructor to construct the month grid with the appropriate days
			 */
			public MonthGrid(Hotel h)
			{
				setLayout(new GridLayout(0, 7, 0, 0));
				setMinimumSize(new Dimension(215, 200));
				this.h = h;
				h.attach(this);

				SVCs = new SelectableViewController[42];
				DAYS[] days = DAYS.values();

				firstDay = h.getFirstDay();
				lastDay = h.getLastDay();

				for(int i = 0; i < 7; i++)
				{
					add(new SelectableViewController(h, -1, new DayBlock(days[i].toString().substring(0, 1), false)));
				}
				for(int i = 0; i < firstDay; i++)
				{
					SVCs[i] = new SelectableViewController(h, 0, new DayBlock(false));
				}
				for(int i = firstDay; i < firstDay+lastDay; i++)
				{
					SVCs[i] = new SelectableViewController(h, i+1-firstDay, new DayBlock(i+1-firstDay == h.getSelectedDay()));
				}
				for(int i = firstDay+lastDay; i < SVCs.length; i++)
				{
					SVCs[i] = new SelectableViewController(h, 0, new DayBlock(false));
				}
				for(SelectableViewController svc : SVCs){add(svc);}
			}

			@Override
			public void stateChanged(ChangeEvent e)
			{
				if(firstDay != h.getFirstDay() || lastDay != h.getLastDay())
				{
					firstDay = h.getFirstDay(); lastDay = h.getLastDay();

					for(int i = 0; i < firstDay; i++)
					{
						SVCs[i].setDay(0);
					}
					for(int i = firstDay; i < firstDay+lastDay; i++)
					{
						SVCs[i].setDay(i+1-firstDay);
					}
					for(int i = firstDay+lastDay; i < SVCs.length; i++)
					{
						SVCs[i].setDay(0);
					}
				}
			}

			/**
			 * A class representing the selectable buttons to make them clickable
			 *@author Matthew Binning
			 *@version 11.5818.221
			 *
			 */
			public class SelectableViewController extends JComponent implements ChangeListener
			{
				private static final long serialVersionUID = 1L;
				private Hotel hotel;
				private DayBlock selectable;
				private int day;

				/**
				 * A constructor to construct the clickable buttons
				 * @param h - the hotel we're working with
				 * @param day - the current day
				 * @param s - the current day block (button)
				 */
				public SelectableViewController(Hotel h, int day, DayBlock s)
				{
					this.hotel = h;
					this.selectable = s;
					h.attach(this);
					setDay(day);
				}

				/**
				 * A method to set the current day
				 * @param day - the day to set
				 */
				public void setDay(int day)
				{
					this.day = day;
					if(this.getMouseListeners().length > 0)
					{
						this.removeMouseListener(getMouseListeners()[0]);
					}
					this.addMouseListener(
							new MouseAdapter() //this is constructed and used statically, regardless if day is updated
							{
								public void mousePressed(MouseEvent mEvent)
								{
									if(day > 0 && day != hotel.getSelectedDay()) hotel.setSelectedDay(day);
								}
							});
					if(day == 0) selectable.setSymb("");
					else if(day > 0)selectable.setSymb(day+"");
				}
				/**
				 * A method to paint the viewController
				 * @param g - the graphics 
				 */
				public void paintComponent(Graphics g)
				{
					Graphics2D g2 = (Graphics2D) g;
					selectable.draw(g2);
				}
				@Override
				public void stateChanged(ChangeEvent e)
				{
					selectable.select(day == hotel.getSelectedDay());
					repaint();
				}
			}
			/**
			 * --------------------------------------------------
			 * NESTED INTERFACE, ABSTRACT CLASS, AND CLASS
			 * --------------------------------------------------
			 */

			/**
			 * An interface representing drawable GUI
			 *@author Matthew Binning
			 *@version 11.5818.221
			 *
			 */
			public static interface Drawable
			{
				/**
				 * A method to draw the GUI
				 * @param g2 - the graphics we're working with
				 */
				public void draw(Graphics2D g2);
			}

			/**
			 * An abstract class allowing the buttons to be selected
			 *@author Matthew Binning
			 *@version 11.5818.221
			 *
			 */
			public static abstract class Selectable implements Drawable
			{
				private boolean select;
				/**
				 * A method to get if the button is selected
				 * @return select - the boolean if button is selected
				 */
				public boolean getSelect(){return select;}

				/**
				 * A method to select the button
				 * @param truth - the boolean of the button
				 */
				public void select(boolean truth){select = truth;}
			}

			/**
			 * A class representing a day in the calendar
			 *@author Matthew Binning
			 *@version 11.5818.221
			 *
			 */
			public static class DayBlock extends Selectable implements Serializable
			{
				private static final long serialVersionUID = 1L;
				private String symb;

				/**
				 * A constructor to construct the day buttons
				 * @param select - the boolean of the button
				 */
				public DayBlock(boolean select)
				{
					select(select);
				}

				/**
				 * An overloaded constructor of the dayBlock to add a string alongside the boolean
				 * @param symb - the string to add
				 * @param select - the boolean of the button
				 */
				public DayBlock(String symb, boolean select)
				{
					this.symb = symb;
					select(select);
				}
				/**
				 * A method to set the string of the button
				 * @param s - the string to set
				 */
				public void setSymb(String s){symb = s;}
				@Override
				/**
				 * A method to draw the buttons
				 * @param g2 - the graphics we're working with
				 */
				public void draw(Graphics2D g2)
				{
					g2.setColor(Color.WHITE);
					Rectangle2D box = new Rectangle2D.Float(0, 0, 29.0f, 21.0f);
					g2.fill(box);
					g2.setColor(Color.BLACK);
					if(getSelect()){g2.draw(box);}
					g2.drawString(symb, 10, 20);
				}
			}
		}
	}

	/**
	 * A class representing all the information inside the monthPanel
	 *@author Matthew Binning
	 *@version 11.5818.221
	 *
	 */
	private static class MonthInfoPanel extends JPanel implements ChangeListener
	{
		private static final long serialVersionUID = 1L;
		private Hotel h;
		private JTextArea roomInfo;
		private JScrollPane monthViewBar;
		
		/**
		 * A constructor to construct the info panel of the month
		 * @param h - the hotel we're working with
		 */
		public MonthInfoPanel(Hotel h)
		{
			this.h = h;
			h.attach(this);
			setMaximumSize(new Dimension(300, 250));
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			roomInfo = new JTextArea(20, 17);
			roomInfo.setMinimumSize(new Dimension(285, 125));
			monthViewBar = new JScrollPane(roomInfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			add(monthViewBar);
			//add(roomInfo);
			stateChanged(new ChangeEvent(this));
		}

		@Override
		public void stateChanged(ChangeEvent arg0)
		{
			roomInfo.setText("Available Rooms:\n");
			ArrayList<Room> rooms = h.getAvailableRooms(h.getSelectedDate());
			for(Room room : rooms)
			{
				if(room.getRoomNumber() != 19) {
					roomInfo.append(room.getRoomNumber()+", ");
				} else {
					roomInfo.append(room.getRoomNumber() + "");
				}
			}
			roomInfo.append("\nReserved Rooms:\n");
			Iterator<Reservation> r = h.reservationIterator(h.getSelectedDate());
			while(r.hasNext())
			{
				Reservation res = r.next();
				roomInfo.append("Room " + res.getRoomNumber()+": "+h.findUserByID(res.getID()).getUserName() +"\n");
			}
			repaint();
		}
	}
}
