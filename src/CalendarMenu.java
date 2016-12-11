import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


/**
 * A class representing the starting menu of the system
 * @author Matthew Binning
 * @version 0.000.0001
 */
public class CalendarMenu extends JFrame
{
	//instance variables of the starting menu
	private static final int BUTTON_WIDTH=400;
	private static final int BUTTON_HEIGHT=50;
	private static final Dimension btnDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
	private static final long serialVersionUID = 1L;

	/**
	 * A constructor to initialize the start menu
	 * @param h - the hotel we're working with
	 */
	public CalendarMenu(Hotel h)
	{
		setSize(600, 600);
		setTitle("Hotel Reservation System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		JPanel northPanel = new JPanel();
		JLabel label = new JLabel("Welcome to our hotel!\n Please choose an option");
		northPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		label.setFont(new Font("Sans-Serif", Font.PLAIN, 24));
		northPanel.add(label);
		
		//buttons of the system
		JButton guest= new JButton("Guest");
		guest.setPreferredSize(btnDim);

		guest.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GuestMenu f = new GuestMenu(h,CalendarMenu.this);
				f.setLocation(CalendarMenu.this.getLocation());
				f.setVisible(true);
				CalendarMenu.this.setVisible(false);
			}
		});

		JButton manager= new JButton("Manager");
		manager.setPreferredSize(btnDim);
		
		manager.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						ManagerMenu mm = new ManagerMenu(h, CalendarMenu.this);
						mm.setLocation(CalendarMenu.this.getLocation());
						mm.setVisible(true);
						CalendarMenu.this.setVisible(false);
					}
				}
		);
		
		JPanel btnPanel = new JPanel();
		btnPanel.add(guest, JPanel.TOP_ALIGNMENT);
		btnPanel.add(manager, JPanel.BOTTOM_ALIGNMENT);
		
		setLayout(new BorderLayout());
		add(label, BorderLayout.NORTH);
		add(btnPanel, BorderLayout.CENTER);
	}

}