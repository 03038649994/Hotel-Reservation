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
public class MakeReservationView extends JFrame {
	
	private static final int BUTTON_WIDTH=400;
	private static final int BUTTON_HEIGHT=50;
	private static final Dimension btnDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
	private final int econ=80,lux=200;
	
	private GregorianCalendar startCal,endCal;
	private JSlider slideDate;
	private JButton btnMKReservations, btnCancelReservations ;
	private JLabel lblTodaysDate, lblEndDate,lblName,lblPickPrice;
	private JFormattedTextField tfInputDate,tfEndDate;
	private JComboBox<String> CBRoomType;
	private JPanel ReservRoomPanel;
	private int currentYear, currentMonth, currentDay;
	
	public MakeReservationView(Hotel h, JFrame parent,int iD) throws ParseException {
		String currentDate;
		
		setSize(500, 500);
		setTitle("Hotel Reservation System");
		setLayout(new BorderLayout());
		startCal= (GregorianCalendar) Calendar.getInstance();
		
		lblName= new JLabel("Make reservation");
		
		//---------Reservation Interface-------------
		//------labels-----
		lblTodaysDate=new JLabel("Starting date");
		lblTodaysDate.setBounds(10,30,100,50);
		
		lblEndDate= new JLabel("Ending date");
		lblEndDate.setBounds(250,30,100,50);
		
		lblPickPrice= new JLabel("Pick the type of room");
//		lblPickPrice.setBounds(10, 180, 125, 25);
		lblPickPrice.setBounds(10, 180, 200, 25);
		
		//-----buttons-------
		btnMKReservations=new JButton("Make Reservation"); 
		btnMKReservations.setBounds(10,250,150,50);
		btnMKReservations.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String s=(String)CBRoomType.getSelectedItem();
				int roomType;
				if(s.contains("$80")){
					roomType=econ;
				}
				else{
					roomType=lux;
				}
				
				AvailableReservationsView f = new AvailableReservationsView(h, MakeReservationView.this, h.findUserByID(iD),
												startCal,startCal ,roomType, iD);
				
				f.setLocation(MakeReservationView.this.getLocation());
				f.setVisible(true);
				MakeReservationView.this.setVisible(false);
				
			}
		});
	
		
		//-------textField input------
		tfInputDate = new JFormattedTextField(new MaskFormatter("##/##/####"));
//		tfInputDate.setBounds(10,80,75,25);
		tfInputDate.setBounds(10, 80, 100, 25);
		
		if(startCal.get(Calendar.DAY_OF_MONTH)>10)
			currentDate=(startCal.get(Calendar.MONTH)+1)+"/"+startCal.get(Calendar.DAY_OF_MONTH)+""+startCal.get(Calendar.YEAR);
		else
			currentDate=(startCal.get(Calendar.MONTH)+1)+"/"+"0"+startCal.get(Calendar.DAY_OF_MONTH)+""+startCal.get(Calendar.YEAR);
		
		tfInputDate.setText(currentDate);
		tfEndDate=new JFormattedTextField();
//		tfEndDate.setBounds(250,80,75,25);
		tfEndDate.setBounds(250,80,100,25);
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
				
				startCal = new GregorianCalendar(currentYear, currentMonth, currentDay);
				int num= slideDate.getValue();
				endCal=startCal;
				endCal.add(Calendar.DAY_OF_MONTH,num );
				
				int month=endCal.get(Calendar.MONTH)+1;
				String endDate=month+"/"+endCal.get(Calendar.DAY_OF_MONTH)+"/"+endCal.get(Calendar.YEAR);
				tfEndDate.setText(endDate);
		
			}
		});
		//-------comboBox to select room type----
		CBRoomType= new JComboBox<>();
		CBRoomType.addItem("Economic: $80");
		CBRoomType.addItem("Luxurious: $200");
		CBRoomType.setBounds(150, 180, 125, 25);
		
		ReservRoomPanel= new JPanel();
		ReservRoomPanel.setLayout(null);
		ReservRoomPanel.add(lblPickPrice);
		ReservRoomPanel.add(lblTodaysDate);
		ReservRoomPanel.add(lblEndDate);
		ReservRoomPanel.add(tfInputDate);
		ReservRoomPanel.add(tfEndDate);
		ReservRoomPanel.add(slideDate);
		ReservRoomPanel.add(CBRoomType);
		ReservRoomPanel.add(btnMKReservations);
		//-----------Back Button--------------
		JButton back = new JButton("<");
		back.setPreferredSize(btnDim);
		
		back.addActionListener(new
				ActionListener()
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
		add(ReservRoomPanel, BorderLayout.CENTER);
		add(back,BorderLayout.SOUTH);
		
	}
	
	public static void main(String[] args) throws HeadlessException, ParseException {
		 ReservationView the= new ReservationView(new Hotel(), new JFrame(), 5);
		 the.setVisible(true);
	}
}
