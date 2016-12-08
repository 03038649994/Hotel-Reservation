import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/** 
 * Start menu GUI to switch between manager
 * and guest interfaces.
 * The very beginning of the program
 */
public class StartMenu extends JFrame
{
	private static final int BUTTON_WIDTH=400;
	private static final int BUTTON_HEIGHT=50;
	private static final Dimension btnDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
	private static final long serialVersionUID = 1L;
	
	/**
	 * creates the GUI start menu interface and initializes all the components.
	 * @param h Hotel object to navigate through the interface
	 */
	public StartMenu(Hotel h)
	{
		System.out.println("in the startMenu");
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

				GuestMenu f = new GuestMenu(StartMenu.this, h);

				f.setLocation(StartMenu.this.getLocation());
				f.setVisible(true);
				StartMenu.this.setVisible(false);
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
						ManagerMenu mm = new ManagerMenu(h, StartMenu.this);
						mm.setLocation(StartMenu.this.getLocation());
						mm.setVisible(true);
						StartMenu.this.setVisible(false);
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