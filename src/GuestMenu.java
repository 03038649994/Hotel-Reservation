import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GuestMenu extends JFrame
{
	private static final int BUTTON_WIDTH=400;
	private static final int BUTTON_HEIGHT=50;
	private static final Dimension btnDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
	private static final Dimension textFieldDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT/2);
	private static final long serialVersionUID = 1L;
	private Hotel hotel;

<<<<<<< Updated upstream
	public GuestMenu(JFrame parent, Hotel h)
	{
		hotel = h;
=======
	public GuestMenu(Hotel h,JFrame parent)
	{
		System.out.println("in the GuestMenu");
>>>>>>> Stashed changes
		setSize(500, 500);
		setTitle("Hotel Reservation System");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel guestPanel = new JPanel();
		guestPanel.setLayout(new BoxLayout(guestPanel, BoxLayout.X_AXIS));
		//-----------------------------------------------------------------------
		JPanel existancePanel = new JPanel();
		existancePanel.setLayout(new BoxLayout(existancePanel, BoxLayout.Y_AXIS));
		JLabel exLabel = new JLabel("Existing User");
		JTextField idField = new JTextField("ID");
		idField.setMaximumSize(textFieldDim);
		JButton signInBtn = new JButton("Sign In");

		signInBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean found = false;
				String id = idField.getText();
				Iterator<User> people = hotel.userIterator();

				while (people.hasNext()) {
					User p = people.next();
					if (p.getUserID() == Integer.parseInt(id)) {
						found = true;
						break;
					}
				}
				if (found) {
					ReservationView r;
					try {
						r = new ReservationView(hotel, GuestMenu.this, Integer.parseInt(id));
						r.setLocation(GuestMenu.this.getLocation());
						r.setVisible(true);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(new JFrame("User does not exist"), "User does not exist");
				}
			}
		});

		existancePanel.add(exLabel);
		existancePanel.add(idField);
		existancePanel.add(signInBtn);
		
		guestPanel.add(existancePanel);
		//----------------------------------------------------------------------
		JPanel firstTimePanel = new JPanel();
		firstTimePanel.setLayout(new BoxLayout(firstTimePanel, BoxLayout.Y_AXIS));
		
		JLabel firstTimeLabel = new JLabel("For first time users");
		JTextField nameField = new JTextField("First and last name");
		nameField.setMaximumSize(textFieldDim);
		JTextField id2Field = new JTextField("ID");
		id2Field.setMaximumSize(textFieldDim);
		
		JButton signUpBtn = new JButton("Sign Up");
		
		signUpBtn.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						new SignUpFrame(h); 
					}
				}
		);
		
		firstTimePanel.add(firstTimeLabel);
		firstTimePanel.add(nameField);
		firstTimePanel.add(id2Field);
		firstTimePanel.add(signUpBtn);
		
		guestPanel.add(firstTimePanel);
		//-------------------------------------------------------------------------
		JLabel label = new JLabel("Hotel Manager Menu");
		
		JButton back = new JButton("<");
		back.setPreferredSize(btnDim);
		
		back.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						parent.setLocation(GuestMenu.this.getLocation());
						parent.setVisible(true);
						GuestMenu.this.dispose();
					}
				}
		);
		add(label, BorderLayout.NORTH);
		add(guestPanel, BorderLayout.CENTER);
		add(back, BorderLayout.SOUTH);
	}

}
