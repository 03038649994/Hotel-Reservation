import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ManagerView extends JFrame
{
	private static final int BUTTON_WIDTH=400;
	private static final int BUTTON_HEIGHT=50;
	private static final Dimension btnDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
	private static final long serialVersionUID = 1L;
	
	public ManagerView(Hotel h, JFrame parent)
	{
		setSize(500, 500);
		setTitle("Hotel Reservation System");
		setLayout(new BorderLayout());
		
		JButton back = new JButton("<");
		back.setPreferredSize(btnDim);
		
		back.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						parent.setLocation(ManagerView.this.getLocation());
						parent.setVisible(true);
						ManagerView.this.dispose();
					}
				}
		);
		JLabel label = new JLabel("Manager View Menu");
		
		JPanel managerPanel = new JPanel();
		managerPanel.setLayout(new BoxLayout(managerPanel, BoxLayout.Y_AXIS));
		
		MonthView monthView = new MonthView(h);
		
		JPanel roomView = new JPanel();
		roomView.setLayout(new BoxLayout(roomView, BoxLayout.X_AXIS));
		
		managerPanel.add(monthView);
		managerPanel.add(roomView);
		
		add(label, BorderLayout.NORTH);
		add(managerPanel, BorderLayout.CENTER);
		add(back, BorderLayout.SOUTH);
	}
}
