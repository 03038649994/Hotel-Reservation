import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * GUI that displays the available rooms
 * from the given dates from ReservationView
 *and allows the rooms to be reserved from the
 *hotel object.
 */

public class AvailableReservationsView extends JFrame implements ChangeListener{
	
	private static final int BUTTON_WIDTH=400;
	private static final int BUTTON_HEIGHT=50;
	private static final Dimension btnDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
	
	private ArrayList<Reservation> holdReservations;
	private JTextArea taDisplayRooms;
	private JTextField tfRoomNum;
	private JButton btConfrim,btMoreReserv,btDone;
	private JLabel lblExplain,lblName;
	private JPanel jpView;
	private Hotel h;
	
	public AvailableReservationsView(Hotel h, JFrame parent, User person, Calendar startCal,Calendar endCal, int roomType,int iD) {
		this.h=h;
		holdReservations= new ArrayList<>();
		setSize(500, 500);
		setTitle("Hotel Reservation System");
		setLayout(new BorderLayout());
		
		//---------labels-----------------------
		lblName= new JLabel("MAKE A RESERVATION");
		lblExplain= new JLabel("Enter the room number to reserve");
		lblExplain.setBounds(230, 30, 200, 50);
		
		//------Panel to view components------
		jpView=new JPanel();
		jpView.setLayout(null);
		
		//--------text field------------------
		tfRoomNum= new JTextField();
		tfRoomNum.setBounds(230, 70, 50, 25);
			
		//----------Buttons-------------------
		
		btConfrim= new JButton("Confirm");
		btConfrim.setBounds(230, 100, 100, 25);
		btConfrim.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Room room= new Room(Integer.parseInt(tfRoomNum.getText()), roomType);
				
				Reservation r= new Reservation(0,startCal, endCal, iD,room);
				holdReservations.add(r);
			}
		});
		
		btMoreReserv= new JButton("Reserve more rooms");
		btMoreReserv.setBounds(230, 280, 200, 25);
		btMoreReserv.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		btDone= new JButton("Done");
		btDone.setBounds(230, 250, 100, 25);
		btDone.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});		
		//-------TextArea to show available rooms-------
		taDisplayRooms= new JTextArea();
		taDisplayRooms.setBounds(10, 50, 200, 250);
		taDisplayRooms.setEditable(false);
		
		jpView.add(btDone);
		jpView.add(btMoreReserv);
		jpView.add(btConfrim);
		jpView.add(tfRoomNum);
		jpView.add(lblExplain);
		jpView.add(taDisplayRooms);
		
		//-----------Back Button--------------
				JButton back = new JButton("<");
				back.setPreferredSize(btnDim);
				
				back.addActionListener(new
						ActionListener()
						{
							@Override
							public void actionPerformed(ActionEvent arg0)
							{
								parent.setLocation(AvailableReservationsView.this.getLocation());
								parent.setVisible(true);
								AvailableReservationsView.this.dispose();
							}
						}
				);
				//-----------------------------
				
				displayAvailableRooms(startCal,endCal ,roomType);
				add(lblName, BorderLayout.NORTH);
				add(back, BorderLayout.SOUTH);
				add(jpView, BorderLayout.CENTER);
	}
	
	
	/**
	 * displays the available rooms on 
	 * the text Area
	 * @param cal
	 * @param roomType
	 * @param date
	 */
	public void displayAvailableRooms(Calendar start,Calendar end,int roomType ){
		
		ArrayList<Room> rooms=h.getAvailableRooms(start);
		taDisplayRooms.setText("Available Rooms\n");
		
		for(Room r: rooms){
			if(r.whatType(roomType))
				taDisplayRooms.append(r.getRoomNumber() +"\n");
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}

}
