import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ManagerMenu extends JFrame
{
	private static final int BUTTON_WIDTH=400;
	private static final int BUTTON_HEIGHT=50;
	private static final Dimension btnDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
	private static final long serialVersionUID = 1L;

	public ManagerMenu(Hotel h, JFrame parent)
	{
		setSize(500, 500);
		setTitle("Hotel Reservation System");
		
		JButton loadBtn = new JButton("Load Existing Reservation");
		loadBtn.setPreferredSize(btnDim);

		loadBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//GuestLogin f = new GuestLogin();
			}
		});

		JButton viewBtn = new JButton("View Information");
		viewBtn.setPreferredSize(btnDim);
		
		viewBtn.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						ManagerView m = new ManagerView(h, ManagerMenu.this);
						m.setLocation(ManagerMenu.this.getLocation());
						m.setVisible(true);
						ManagerMenu.this.setVisible(false);
					}
				}
		);
		
		JButton saveBtn = new JButton("Save Reservations");
		saveBtn.setPreferredSize(btnDim);
		
		saveBtn.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						
					}
				}
		);
		
		JButton quitBtn = new JButton("Quit");
		quitBtn.setPreferredSize(btnDim);
		
		quitBtn.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						
					}
				}
		);
		
		JButton back = new JButton("<");
		back.setPreferredSize(btnDim);
		
		back.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						parent.setLocation(ManagerMenu.this.getLocation());
						parent.setVisible(true);
						ManagerMenu.this.dispose();
					}
				}
		);
		JLabel label= new JLabel("Hotel Manager Menu");
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
		btnPanel.add(loadBtn);
		btnPanel.add(viewBtn);
		btnPanel.add(saveBtn);
		btnPanel.add(quitBtn);
		
		setLayout(new BorderLayout());
		add(label, BorderLayout.NORTH);
		add(btnPanel, BorderLayout.CENTER);
		add(back, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}