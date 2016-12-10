import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;
/**
 * Reservation GUI for guest to find a 
 * type of room between the given dates 
 */
public class MakeReservationView extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static final int BUTTON_WIDTH=400;
	private static final int BUTTON_HEIGHT=50;
	private static final Dimension btnDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
	private Hotel h;
	private JFrame parent;
	public JFrame getParental()
	{
		return parent;
	}
	public static Calendar shiftCalendar(Calendar calendar, int dayShift)
	{
		Calendar c = (Calendar) calendar.clone();
		int monthShift = c.get(Calendar.MONTH);
		int yearShift = c.get(Calendar.YEAR);
		System.out.println(monthShift +" "+ yearShift);
		
		while(dayShift > c.getActualMaximum(Calendar.DAY_OF_MONTH)){monthShift++; dayShift -= c.getActualMaximum(Calendar.DAY_OF_MONTH);}
		while(monthShift > c.getActualMaximum(Calendar.MONTH)){yearShift++; monthShift -= c.getActualMaximum(Calendar.MONTH)+1;}
		
		c.set(Calendar.DAY_OF_MONTH, dayShift);
		c.set(Calendar.MONTH, monthShift);
		c.set(Calendar.YEAR, yearShift);
		return c;
	}
	public MakeReservationView(Hotel h, JFrame parent) throws ParseException
	{
		this.parent = parent;
		this.h = h;
		JLabel lblName = new JLabel("Make a Reservation");
		setSize(500, 500);
		setTitle("Hotel Reservation System");
		setLayout(new BorderLayout());
		setResizable(false);

		JPanel reservePanel = new JPanel();
		reservePanel.setLayout(new BoxLayout(reservePanel, BoxLayout.Y_AXIS));
		reservePanel.add(new MakeReservationPanel(h));
		reservePanel.add(new AvailableReservationView(h, this));
		//-----------Back Button--------------
		JButton back = new JButton("<");
		back.setPreferredSize(btnDim);

		back.addActionListener(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						parent.setLocation(MakeReservationView.this.getLocation());
						parent.setVisible(true);
						MakeReservationView.this.dispose();
					}
				}
				);
		//-----------------------------
		add(lblName, BorderLayout.NORTH);
		add(reservePanel, BorderLayout.CENTER);
		add(back,BorderLayout.SOUTH);
	}

	public static class EndDateView extends JFormattedTextField implements ChangeListener
	{
		private static final long serialVersionUID = 1L;
		private Hotel h;
		public EndDateView(Hotel h) throws ParseException
		{
			super(new MaskFormatter("##/##/####"));
			this.h = h;
			h.attach(this);
			setBounds(250,80,100,25);
			setEnabled(false);
			stateChanged(new ChangeEvent(this));
		}
		@Override
		public void stateChanged(ChangeEvent e)
		{
			Calendar endCal = h.getSelectedDate2();
			String currentDate = (endCal.get(Calendar.MONTH)<9? "0" : "") + (endCal.get(Calendar.MONTH)+1) + "/" + (endCal.get(Calendar.DAY_OF_MONTH)>9? "" : "0") + endCal.get(Calendar.DAY_OF_MONTH) + "/" + endCal.get(Calendar.YEAR);
			setText(currentDate);
		}
	}
	public static class MakeReservationPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;
		public MakeReservationPanel(Hotel h) throws ParseException
		{
			setMinimumSize(new Dimension(500, 250));
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			String currentDate;
			//-----------------------------------------RESERVATION CONTROLLER INTERFACE----------------------------------
			//------labels-----
			JLabel lblTodaysDate = new JLabel("Starting date");
			lblTodaysDate.setBounds(10,30,100,50);

			JLabel lblEndDate = new JLabel("Ending date");
			lblEndDate.setBounds(250,30,100,50);

			JLabel lblPickPrice = new JLabel("Pick the type of room");
			//		lblPickPrice.setBounds(10, 180, 125, 25);
			lblPickPrice.setBounds(10, 180, 200, 25);

			//-------comboBox to select room type----
			h.setSelectedRoomType(80);
			JComboBox<String> CBRoomType= new JComboBox<>();
			CBRoomType.addItem("Economic: $80");
			CBRoomType.addItem("Luxurious: $200");
			CBRoomType.setBounds(150, 180, 125, 25);
			CBRoomType.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					if(((String)CBRoomType.getSelectedItem()).equals("Economic: $80")) h.setSelectedRoomType(80);
					else if(((String)CBRoomType.getSelectedItem()).equals("Luxurious: $200")) h.setSelectedRoomType(200);
				}

			});

			//-------textField input------
			JFormattedTextField tfInputDate = new JFormattedTextField(new MaskFormatter("##/##/####"));
			tfInputDate.setBounds(10, 80, 100, 25);

			Calendar startCal = h.getSelectedDate();
			
			if(startCal.get(Calendar.DAY_OF_MONTH)>=10)
				currentDate=(startCal.get(Calendar.MONTH)+1)+"/"+startCal.get(Calendar.DAY_OF_MONTH)+""+startCal.get(Calendar.YEAR);
			else
				currentDate=(startCal.get(Calendar.MONTH)+1)+"/"+"0"+startCal.get(Calendar.DAY_OF_MONTH)+""+startCal.get(Calendar.YEAR);
			tfInputDate.setText(currentDate);
			tfInputDate.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					h.setSelectedDate(
							new GregorianCalendar(
									Integer.parseInt(tfInputDate.getText().substring(6)),
									Integer.parseInt(tfInputDate.getText().substring(0, 2)),
									Integer.parseInt(tfInputDate.getText().substring(3, 5))
									
									)
							);

				}
			});
			EndDateView tfEndDate = new EndDateView(h);
			//--------------Grouped Panels

			//--------slider--------
			JSlider slideDate = new JSlider(0, 60,0);
			slideDate.setBounds(10, 130, 300, 50);
			slideDate.setMinorTickSpacing(2);
			slideDate.setMajorTickSpacing(10);
			slideDate.setPaintTicks(true);
			slideDate.setPaintLabels(true);
			slideDate.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e)
				{
					Calendar endCal = shiftCalendar((Calendar) h.getSelectedDate(), slideDate.getValue() + h.getSelectedDate().get(Calendar.DAY_OF_MONTH));
					h.setSelectedDate2(endCal);
				}
			});
			JPanel inputPanel = new JPanel(); inputPanel.add(lblTodaysDate); inputPanel.add(tfInputDate);
			JPanel outputPanel = new JPanel(); outputPanel.add(lblEndDate); outputPanel.add(tfEndDate);
			JPanel datePanel = new JPanel();  datePanel.add(inputPanel); datePanel.add(outputPanel);
			add(datePanel);
			add(slideDate);
			add(lblPickPrice);
			add(CBRoomType);
		}
	}
	public static class RoomTextArea extends JTextArea implements ChangeListener
	{
		private static final long serialVersionUID = 1L;
		private Hotel h;
		public RoomTextArea(Hotel h)
		{
			this.h = h;
			h.attach(this);
			setBounds(10, 70, 200, 200);
			setEditable(false);
			stateChanged(new ChangeEvent(this));
		}
		@Override
		public void stateChanged(ChangeEvent arg0)
		{
			if(h.getSelectedDate().equals(h.getSelectedDate2()))
			{
				setText("Drag the slider to define\na length of stay.");
			}
			else
			{
				ArrayList<Room> rooms = h.getAvailableRooms(h.getSelectedDate(), h.getSelectedDate2(), h.getSelectedRoomType());
				setText("Available Rooms\n");
				for(Room r: rooms){append(r.getRoomNumber() +", ");}
			}
		}
	}
	public static class AvailableReservationView extends JPanel
	{
		private static final long serialVersionUID = 1L;
		private MakeReservationView holder;
		public AvailableReservationView(Hotel h, MakeReservationView holder)
		{
			this.holder = holder;
			setSize(500, 250);
			setLayout(new BorderLayout());

			//---------labels-----------------------
			JLabel lblName= new JLabel("Available Reservations");
			JLabel lblExplain= new JLabel("Enter the room number to reserve");
			lblExplain.setBounds(230, 30, 200, 50);

			//------Panel to view components------
			JPanel jpView = new JPanel();
			jpView.setLayout(null);

			//--------text field------------------
			JTextField tfRoomNum = new JTextField();
			tfRoomNum.setBounds(230, 70, 60, 25);

			//----------Buttons-------------------]
			JButton btDone = new JButton("Done");
			btDone.setBounds(230, 70, 70, 25);
			btDone.setVisible(false);
			
			JButton btConfirm = new JButton("Confirm");
			btConfirm.setBounds(300, 70, 150, 25);

			JButton btMoreReserv = new JButton("Reserve More Rooms");
			btMoreReserv.setBounds(300, 70, 160, 25);
			btMoreReserv.setVisible(false);

			btConfirm.addActionListener
			(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							if(h.getSelectedDate().equals(h.getSelectedDate2()))
							{
								JOptionPane.showMessageDialog(new JFrame("Works Not"), "Reservations must be at least one night.");
							}
							else
							{
								ArrayList<Room> rooms = h.getAvailableRooms(h.getSelectedDate(), h.getSelectedDate2(), h.getSelectedRoomType());
								boolean available = false;
								for(Room r: rooms)
								{
									if(Integer.parseInt(tfRoomNum.getText()) == r.getRoomNumber()) available = true;
								}
								if(available)
								{
									h.addReservation(new Reservation(h.getSelectedDate(), h.getSelectedDate2(), h.getSelectedUser().getUserID(), h.getRooms().get(Integer.parseInt(tfRoomNum.getText()))));
									tfRoomNum.setVisible(false);
									btConfirm.setVisible(false);
									btMoreReserv.setVisible(true);
									btDone.setVisible(true);
								}
								else JOptionPane.showMessageDialog(new JFrame("Works Not"), "Sorry, this room is not available.");
							}
						}
					});
			btMoreReserv.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					tfRoomNum.setVisible(true);
					tfRoomNum.setText("");
					btConfirm.setVisible(true);
					btMoreReserv.setVisible(false);
					btDone.setVisible(false);
				}
			});

			btDone.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					PrintFrame p = new PrintFrame(h, holder);
					p.setLocation(holder.getLocation());
					p.setVisible(true);
					holder.setVisible(false);
				}
			});
			//-------TextArea to show available rooms-------
			RoomTextArea taDisplayRooms= new RoomTextArea(h);

			jpView.add(btConfirm);
			jpView.add(btDone);
			jpView.add(btMoreReserv);
			jpView.add(tfRoomNum);
			jpView.add(lblExplain);
			jpView.add(taDisplayRooms);

			//-----------------------------
			add(lblName, BorderLayout.NORTH);
			add(jpView, BorderLayout.CENTER);
		}
	}
}
