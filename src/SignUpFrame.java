import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * A class representing the sign up frame for
 * the reservation system
 * @author Karan Bhargava
 * @version 1.2016.991
 *
 */
public class SignUpFrame {

	private Hotel h;

	SignUpFrame(Hotel hotel) {
		h = hotel;

		final JFrame frame = new JFrame();
		frame.setTitle("Hotel Sign Up");
		frame.setSize(600,200);

		JPanel northPanel = new JPanel();
		JLabel label = new JLabel("Hotel Account Sign Up");
		northPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		label.setFont(new Font("Serif", Font.PLAIN, 24));
		northPanel.add(label);

		JPanel centerPanel = new JPanel();
		JPanel centerPanel1 = new JPanel();
		JPanel centerPanel2 = new JPanel();
		JPanel centerPanel3 = new JPanel();
		JLabel idLabel = new JLabel("User ID");
		JLabel pinLabel = new JLabel("Password");
		JLabel nameLabel = new JLabel("Name");
		final JTextField userIDField = new JTextField("1234");
		final JPasswordField pinField = new JPasswordField("1234");
		final JTextField nameField = new JTextField("1234");

		centerPanel1.add(idLabel);
		centerPanel1.add(userIDField);

		centerPanel2.add(pinLabel);
		centerPanel2.add(pinField);

		centerPanel3.add(nameLabel);
		centerPanel3.add(nameField);

		centerPanel.add(centerPanel1);
		centerPanel.add(centerPanel2);
		centerPanel.add(centerPanel3);

		JPanel bottomPanel = new JPanel();
		JButton submitButton = new JButton("Submit");

		submitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int id = Integer.parseInt(userIDField.getText());
				String pin = new String(pinField.getPassword());
				String name = nameField.getText();

				User guest = new User(id, name, pin);
				hotel.addUser(guest);
				frame.dispose();

			}
		});

		bottomPanel.add(submitButton);

		frame.add(northPanel, BorderLayout.NORTH);
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);
	}

}