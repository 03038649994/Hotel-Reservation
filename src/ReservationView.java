import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.*;


/**
 * Reservation GUI for guest to find a 
 * type of room between the given dates 
 */
public class ReservationView extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static final int BUTTON_WIDTH=400;
	private static final int BUTTON_HEIGHT=50;
	private static final Dimension btnDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
	
	private JLabel lblName; 

	public ReservationView(Hotel h, JFrame parent)
	{
		setSize(500, 500);
		setTitle("Hotel Reservation System");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		JPanel resViewPanel = new JPanel();
		resViewPanel.setLayout(new BoxLayout(resViewPanel, BoxLayout.Y_AXIS));


		lblName= new JLabel("Guest Reservation Menu");

		JButton btnMake = new JButton("Make A Reservation");
		btnMake.setMinimumSize(btnDim);
		btnMake.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				MakeReservationView r;
				try
				{
					r = new MakeReservationView(h, ReservationView.this);
					r.setLocation(ReservationView.this.getLocation());
					r.setVisible(true);
					ReservationView.this.setVisible(false);
				}
				catch (ParseException e1) {e1.printStackTrace();}
			}
		});


		JButton btnCancel = new JButton("Cancel Reservation");
		btnCancel.setMinimumSize(btnDim);
		btnCancel.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				DeleteReservationView r = new DeleteReservationView(h, ReservationView.this);
				r.setLocation(ReservationView.this.getLocation());
				r.setVisible(true);
				ReservationView.this.setVisible(false);
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
		resViewPanel.add(btnMake); resViewPanel.add(btnCancel);
		add(lblName, BorderLayout.NORTH);
		add(resViewPanel, BorderLayout.CENTER);
		add(back,BorderLayout.SOUTH);

	}
}
