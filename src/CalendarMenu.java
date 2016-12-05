import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class CalendarMenu extends JFrame
{
	private static final int BUTTON_WIDTH=400;
	private static final int BUTTON_HEIGHT=50;
	private static final Dimension btnDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
	private static final long serialVersionUID = 1L;

	public CalendarMenu(Hotel h)
	{
		setSize(500, 500);
		setTitle("Hotel Reservation System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton guest= new JButton("Guest");
		guest.setPreferredSize(btnDim);

		guest.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GuestMenu f = new GuestMenu(CalendarMenu.this, h);
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

		JLabel label= new JLabel("Welcome");
		
		JPanel btnPanel = new JPanel();
		btnPanel.add(guest, JPanel.TOP_ALIGNMENT);
		btnPanel.add(manager, JPanel.BOTTOM_ALIGNMENT);
		
		setLayout(new BorderLayout());
		add(label, BorderLayout.NORTH);
		add(btnPanel, BorderLayout.CENTER);
	}

}