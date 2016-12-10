import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



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

	private JTextField tfRoomNum;
	private JButton btConfrim;
	private JLabel lblExplain;
	private Hotel h;

	public DeleteReservationView(Hotel h, JFrame parent)
	{
		this.h=h;

		setSize(500, 500);
		setTitle("Hotel Reservation System");
		setLayout(new BorderLayout());

		//---------labels-----------------------
		JLabel lblName = new JLabel("DELETE A RESERVATION");
		lblExplain = new JLabel("Enter the index number to delete");
		lblExplain.setBounds(230, 30, 200, 50);

		//------Panel to view components------
		JPanel jpView = new JPanel();
		jpView.setLayout(null);

		//--------text field------------------
		tfRoomNum = new JTextField();
		tfRoomNum.setBounds(230, 70, 50, 25);

		//----------Buttons-------------------

		btConfrim = new JButton("Cancel Reservation");
		btConfrim.setBounds(230, 100, 200, 25);
		btConfrim.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(tfRoomNum.equals(""))
				{
					JOptionPane.showMessageDialog(new JFrame("Works Not"), "Please enter a room number to delete.");
				}
				else
				{
					Iterator<Reservation> reservations = h.reservationIterator(h.getSelectedUser().getUserID());
					Reservation r = reservations.next();
					int counter = 1;
					while(counter < Integer.parseInt(tfRoomNum.getText())){counter++; r = reservations.next();}
					h.removeReservation(r);
				}
			}
		});
		//-------TextArea to show available rooms-------
		RoomTextArea displayRooms = new RoomTextArea(h);
		//displayRooms.setBounds(10, 50, 200, 250);
		displayRooms.setEditable(false);
		JScrollPane taDisplayRooms = new JScrollPane(displayRooms);
		taDisplayRooms.setBounds(10, 50, 200, 250);
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
			Iterator<Reservation> reservations = h.reservationIterator(h.getSelectedUser().getUserID());
			setText("Your reservations:\n");
			int counter = 0;
			while(reservations.hasNext())
			{
				counter++;
				Reservation r = reservations.next();
				append(counter + ". " + r.toString()+"\n");
			}
		}
	}
}