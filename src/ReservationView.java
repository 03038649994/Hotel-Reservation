import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
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
public class ReservationView extends JFrame {
	
	private static final int BUTTON_WIDTH=400;
	private static final int BUTTON_HEIGHT=50;
	private static final Dimension btnDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
	
	private User person;
	private GregorianCalendar cal;
	private JSlider slideDate;
	private JButton btnMKReservations, btnCancelReservations ;
	private JLabel lblTodaysDate, lblEndDate,lblName,tfEndDate;
	private JFormattedTextField tfInputDate;
	private JComboBox<String> CBRoomType;
	private JPanel ReservRoomPanel;
	private int currentYear, currentMonth, currentDay;
	
	public ReservationView(Hotel h, JFrame parent,int iD) throws ParseException {
		String currentDate;
		
		setSize(500, 500);
		setTitle("Hotel Reservation System");
		setLayout(new BorderLayout());
		cal= (GregorianCalendar) Calendar.getInstance();
		
		lblName= new JLabel("Make reservation");
		
		person = h.findUserByID(iD);
		
		//---------Reservation Interface-------------
		//------labels-----
		lblTodaysDate=new JLabel("Starting date");
		lblTodaysDate.setBounds(10,30,100,50);
		
		lblEndDate= new JLabel("Ending date");
		lblEndDate.setBounds(250,30,100,50);
		
		//-----buttons-------
		btnMKReservations=new JButton("Make Reservation"); 
		btnMKReservations.setBounds(10,250,150,50);
		btnMKReservations.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		btnCancelReservations=new JButton("Cancel Reservation");
		btnCancelReservations.setBounds(200,250,150,50);
		btnCancelReservations.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//-------textField input------
		tfInputDate = new JFormattedTextField(new MaskFormatter("##/##/####"));
		tfInputDate.setBounds(10,80,75,25);
		
		if(cal.get(Calendar.DAY_OF_MONTH)>10)
			currentDate=(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH)+""+cal.get(Calendar.YEAR);
		else
			currentDate=(cal.get(Calendar.MONTH)+1)+"/"+"0"+cal.get(Calendar.DAY_OF_MONTH)+""+cal.get(Calendar.YEAR);
		
		tfInputDate.setText(currentDate);
		tfEndDate=new JLabel();
		tfEndDate.setBounds(250,80,75,25);
		tfEndDate.setEnabled(false);
		
		//--------slider--------
		slideDate = new JSlider(0, 60,0);
		slideDate.setBounds(10, 130, 300, 50);
		slideDate.setMinorTickSpacing(2);
		slideDate.setMajorTickSpacing(10);
		slideDate.setPaintTicks(true);
		slideDate.setPaintLabels(true);
		slideDate.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) { //
				String startDate= tfInputDate.getText();
				
				String[] date= startDate.split("/");
				currentMonth=Integer.parseInt(date[0])-1;
				currentDay=Integer.parseInt(date[1]);
				currentYear=Integer.parseInt(date[2]);
				
				cal = new GregorianCalendar(currentYear, currentMonth, currentDay);
				int num= slideDate.getValue();
				cal.add(Calendar.DAY_OF_MONTH,num );
				
				int month=cal.get(Calendar.MONTH)+1;
				
				
				tfEndDate.setText(month+"/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.YEAR));
				
			}
		});
		//-------comboBox to select room type----
		CBRoomType= new JComboBox<>();
		CBRoomType.addItem("economic");
		CBRoomType.addItem("luxurious");
		CBRoomType.setBounds(10, 180, 125, 25);
		
		ReservRoomPanel= new JPanel();
		ReservRoomPanel.setLayout(null);
		ReservRoomPanel.add(lblTodaysDate);
		ReservRoomPanel.add(lblEndDate);
		ReservRoomPanel.add(tfInputDate);
		ReservRoomPanel.add(tfEndDate);
		ReservRoomPanel.add(slideDate);
		ReservRoomPanel.add(CBRoomType);
		ReservRoomPanel.add(btnMKReservations);
		ReservRoomPanel.add(btnCancelReservations);
		//-----------Back Button--------------
		JButton back = new JButton("<");
		back.setPreferredSize(btnDim);
		
		back.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						parent.setLocation(ReservationView.this.getLocation());
						parent.setVisible(true);
						ReservationView.this.dispose();
					}
				}
		);
		//-----------------------------
		add(lblName, BorderLayout.NORTH);
		add(ReservRoomPanel, BorderLayout.CENTER);
		add(back,BorderLayout.SOUTH);
		
	}
	
	public static void main(String[] args) throws HeadlessException, ParseException {
		 ReservationView the= new ReservationView(new Hotel(), new JFrame(), 5);
		 the.setVisible(true);
	}
}
