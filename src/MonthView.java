import java.awt.BorderLayout;
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
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MonthView extends JPanel
{
	private static final long serialVersionUID = 1L;
	public MonthView(Hotel h)
	{
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(new MonthPanel(h));
		add(new MonthInfoPanel(h));
	}

	private static class MonthPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;
		public MonthPanel(Hotel h)
		{
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setMaximumSize(new Dimension(320, 200));

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
		 * --------------------------------------------------
		 */
		public static class ButtonController extends JPanel
		{
			private static final long serialVersionUID = 1L;

			public ButtonController(Hotel h)
			{
				setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

				JButton yesteryear = new JButton("<<");
				yesteryear.addActionListener(new
						ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0){h.yesteryear();}
				}
						);

				JButton yestermonth = new JButton("<");
				yestermonth.addActionListener(new
						ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {h.yestermonth();}
				}
						);

				JButton nextmonth = new JButton(">");
				nextmonth.addActionListener(new
						ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {h.nextMonth();}
				}
						);
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
		public static class MonthLabel extends JLabel implements ChangeListener
		{
			private static final long serialVersionUID = 1L;
			enum MONTHS
			{
				January, February, March, April, May, June, July, August, September, October, November, December;
			}
			private Hotel h;
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

			enum DAYS
			{
				Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
			}
			/*
			 * iterate through model, add boxShape class
			 * these may start at different days of week
			 * also, some day of the month is selected
			 */
			public MonthGrid(Hotel h)
			{
				setLayout(new GridLayout(0, 7, 0, 0));
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

			public class SelectableViewController extends JComponent implements ChangeListener
			{
				private static final long serialVersionUID = 1L;
				private Hotel hotel;
				private DayBlock selectable;
				private int day;
				public SelectableViewController(Hotel h, int day, DayBlock s)
				{
					this.hotel = h;
					this.selectable = s;
					h.attach(this);
					setDay(day);
				}

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

			public static interface Drawable
			{
				public void draw(Graphics2D g2);
			}
			public static abstract class Selectable implements Drawable
			{
				private boolean select;
				public boolean getSelect(){return select;}
				public void select(boolean truth){select = truth;}
			}
			public static class DayBlock extends Selectable implements Serializable
			{
				private static final long serialVersionUID = 1L;
				private String symb;
				public DayBlock(boolean select)
				{
					select(select);
				}
				public DayBlock(String symb, boolean select)
				{
					this.symb = symb;
					select(select);
				}
				public void setSymb(String s){symb = s;}
				@Override
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
	private static class MonthInfoPanel extends JPanel implements ChangeListener
	{
		private Hotel h;
		private JPanel availableRooms;
		private JPanel reservedRooms;
		public MonthInfoPanel(Hotel h)
		{
			this.h = h;
			h.attach(this);
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setMaximumSize(new Dimension(320, 200));
			availableRooms = new JPanel();
			availableRooms.setLayout(new GridLayout(0, 4));

			reservedRooms = new JPanel();
			reservedRooms.setLayout(new BoxLayout(reservedRooms, BoxLayout.Y_AXIS));

			add(new JLabel("Room Information"));
			add(new JLabel("Available Rooms:"));
			add(availableRooms);
			add(new JLabel("Reserved Rooms:"));
			add(reservedRooms);
			stateChanged(new ChangeEvent(this));
		}

		@Override
		public void stateChanged(ChangeEvent arg0)
		{
			availableRooms.removeAll();
			ArrayList<Room> rooms = h.getAvailableRooms(h.getSelectedDate());
			for(Room room : rooms)
			{
				availableRooms.add(new JLabel(room.getRoomNumber()+""));
			}
			reservedRooms.removeAll();
			Iterator<Reservation> r = h.reservationIterator(h.getSelectedDate());
			while(r.hasNext())
			{
				Reservation res = r.next();
				reservedRooms.add(new JLabel("Room " + res.getRoomNumber()+": "+h.findUserByID(res.getID()).getUserName()));
			}
			repaint();
		}
	}
}
