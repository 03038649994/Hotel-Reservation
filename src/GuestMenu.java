import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * A class representing the guest's functions
 * @author Karan Bhargava & Matthew Binning
 * @version 1.2016.991
 *
 */
public class GuestMenu extends JFrame
{
	//instance variables for the frame
	private static final int BUTTON_WIDTH=400;
	private static final int BUTTON_HEIGHT=50;
	private static final String DEFAULT_NAME="Ex. Joe Schmoe";
	private static final Dimension btnDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
	private static final Dimension textFieldDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT/2);
	private static final long serialVersionUID = 1L;

	/**
	 * A constructor to initialize the frame components
	 * @param h - the hotel we're working with
	 * @param parent - the GuestMenu's parent to go back to the previous frame
	 */
	public GuestMenu(Hotel h,JFrame parent)
	{
		setSize(600, 600);
		setTitle("Hotel Reservation System");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		JPanel guestPanel = new JPanel();
		guestPanel.setLayout(new BoxLayout(guestPanel, BoxLayout.Y_AXIS));
		//-----------------------------------------------------------------------
		JPanel existancePanel = new JPanel();
		existancePanel.setLayout(new BoxLayout(existancePanel, BoxLayout.Y_AXIS));
		JLabel exLabel = new JLabel("For existing users");

		JLabel idLbl = new JLabel("ID ");
		JTextField idField = new JTextField("0000"); idField.setMaximumSize(textFieldDim);
		JPanel idPanel = new JPanel(); idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.X_AXIS)); idPanel.add(idLbl); idPanel.add(idField);

		JButton signInBtn = new JButton("Sign In");
		signInBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				boolean found = false;
				String id = idField.getText();
				Iterator<User> people = h.userIterator();

				while (people.hasNext())
				{
					User p = people.next();
					if(p.getUserID() == Integer.parseInt(id)){found = true; break;}
				}
				if(found)
				{
					h.setSelectedUser(h.findUserByID(Integer.parseInt(id)));
					idField.setText("0000");
					ReservationView r = new ReservationView(h, GuestMenu.this);
					r.setLocation(GuestMenu.this.getLocation());
					r.setVisible(true);
					GuestMenu.this.setVisible(false);
				}
				else
				{
					JOptionPane.showMessageDialog(new JFrame("User does not exist"), "User does not exist");
				}
			}
		});
		idPanel.add(signInBtn);

		existancePanel.add(exLabel);
		existancePanel.add(idPanel);

		guestPanel.add(existancePanel);
		//----------------------------------------------------------------------
		JPanel firstTimePanel = new JPanel();
		firstTimePanel.setLayout(new BoxLayout(firstTimePanel, BoxLayout.Y_AXIS));

		JLabel firstTimeLabel = new JLabel("For first time users");

		JLabel nameLbl = new JLabel("Name: ");
		JTextField nameField = new JTextField(DEFAULT_NAME); nameField.setMinimumSize(textFieldDim);
		JLabel id2Lbl = new JLabel("ID");
		JTextField id2Field = new JTextField("0000"); id2Field.setMaximumSize(textFieldDim);
		JPanel id2Panel = new JPanel(); id2Panel.add(nameLbl); id2Panel.add(nameField); id2Panel.add(id2Lbl); id2Panel.add(id2Field);

		JButton signUpBtn = new JButton("Sign Up");
		signUpBtn.addActionListener(new
				ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(nameField.getText().equals(DEFAULT_NAME))
				{
					JOptionPane.showMessageDialog(new JFrame("Enter a name!"), "Enter a unique name!");
				}
				else
				{
					int testID = Integer.parseInt(id2Field.getText());
					boolean uniqueID = true;
					Iterator<User> users = h.userIterator();
					while(users.hasNext() && uniqueID)
					{
						User user = users.next();
						uniqueID = !(testID == user.getUserID());
					}
					if(uniqueID)
					{
						h.addUser(new User(Integer.parseInt(id2Field.getText()), nameField.getText()));
						nameField.setText(DEFAULT_NAME); id2Field.setText("0000");
						JOptionPane.showMessageDialog(new JFrame("Works"), "Account creation successful!");
					}
					else
					{
						JOptionPane.showMessageDialog(new JFrame("Works Not"), "Sorry, this ID is taken.");
					}
				}
			}
		}
				);
		id2Panel.add(signUpBtn);
		firstTimePanel.add(firstTimeLabel);
		firstTimePanel.add(id2Panel);

		guestPanel.add(firstTimePanel);
		//-------------------------------------------------------------------------
		JPanel northPanel = new JPanel();
		JLabel label = new JLabel("Thank you for choosing us for your stay. :)");
		northPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		label.setFont(new Font("Sans-Serif", Font.PLAIN, 24));
		northPanel.add(label);

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
