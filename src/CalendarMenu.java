import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class CalendarMenu
{
	public static void main(String[] args)
	{
		final int BUTTON_WIDTH=400;
		final int BUTTON_HEIGHT=50;
		Dimension btnDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);

		JFrame frame= new JFrame();
		frame.setSize(500, 500);

		JButton guest= new JButton("Guest");
		guest.setPreferredSize(btnDim);

		guest.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//GuestLogin f = new GuestLogin();
			}
		});

		JButton manager= new JButton("Manager");
		manager.setPreferredSize(btnDim);

		JLabel label= new JLabel("Guest Or Manager?");
		
		JPanel btnPanel = new JPanel();
		btnPanel.add(guest, JPanel.TOP_ALIGNMENT);
		btnPanel.add(manager, JPanel.BOTTOM_ALIGNMENT);
		
		frame.setLayout(new BorderLayout());
		frame.add(label, BorderLayout.NORTH);
		frame.add(btnPanel, BorderLayout.CENTER);

		frame.setTitle("Hotel Reservation System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.pack();
		frame.setVisible(true);
	}

}
