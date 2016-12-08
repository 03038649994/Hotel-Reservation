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
	
	
	private JButton btnMKReservations, btnCancelReservations ;
	private JLabel lblName; 
	
	public ReservationView(Hotel h, JFrame parent, int id) throws ParseException {
		setSize(500, 500);
		setTitle("Hotel Reservation System");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		lblName= new JLabel("Guest Menu");
		
		 btnMKReservations= new JButton("Make A Reservation");
		 btnMKReservations.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				MakeReservationView r;
				try {
					r = new MakeReservationView(h, ReservationView.this, id);
					r.setLocation(ReservationView.this.getLocation());
					r.setVisible(true);
					ReservationView.this.setVisible(false);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		 
		 
		 btnCancelReservations= new JButton("Cancel Reservation");
		 btnCancelReservations.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		 
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
		add(btnMKReservations, BorderLayout.WEST);
		add(btnCancelReservations, BorderLayout.EAST);
		add(back,BorderLayout.SOUTH);
		
	}
	
	public static void main(String[] args) throws HeadlessException, ParseException {
		 ReservationView the= new ReservationView(new Hotel(), new JFrame(),0);
		 the.setVisible(true);
	}
}
