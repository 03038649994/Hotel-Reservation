import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.*;



/**
 * GUI that displays the available rooms
 * from the given dates from ReservationView
 *and allows the rooms to be reserved from the
 *hotel object.
 */

public class DeleteReservationView extends JFrame
{	
	private static final long serialVersionUID = 1L;
	private static final int BUTTON_WIDTH=400;
	private static final int BUTTON_HEIGHT=50;
	private static final Dimension btnDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
	
	private JTextArea taDisplayRooms;
	private JTextField tfRoomNum;
	private JButton btConfrim,btDone;
	private JLabel lblExplain,lblName;
	private JPanel jpView;
	private Hotel h;
	
	public DeleteReservationView(Hotel h, JFrame parent)
	{
		this.h=h;
		
		setSize(500, 500);
		setTitle("Hotel Reservation System");
		setLayout(new BorderLayout());
		
		//---------labels-----------------------
		lblName= new JLabel("DELETE A RESERVATION");
		lblExplain= new JLabel("Enter the room number to delete");
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
								parent.setLocation(DeleteReservationView.this.getLocation());
								parent.setVisible(true);
								DeleteReservationView.this.dispose();
							}
						}
				);
				//-----------------------------
				
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
	public void displayAvailableRooms( int id){			
		Iterator<Reservation> rooms=h.reservationIterator(id);
		
		taDisplayRooms.setText("Rooms you have reserved\n");
		
		while(rooms.hasNext()){
			Reservation r =rooms.next();
				taDisplayRooms.append(r.toString()+"\n");
		}
	}

}