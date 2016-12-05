/**
 * A class representing the cancellation frame
 * @author Karan Bhargava
 * @version 1.2016.991
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;

public class CancelReservationFrame {

	private User user;
	private Hotel hotel;

	private final JFrame frame = new JFrame();
	private final JPanel northPanel = new JPanel();
	private final JPanel centerPanel = new JPanel();
	private final JPanel southPanel = new JPanel();
	private final JScrollPane scrollPane;
	private final JButton cancelButton = new JButton("Cancel Reservation");
	private final JList<Reservation> jlist = new JList<Reservation>();
	private DefaultListModel<Reservation> model = new DefaultListModel<Reservation>();

	CancelReservationFrame(Hotel h, User u) {
		user = u;
		hotel = h;

		frame.setTitle("Hotel Reservation System");
		frame.setSize(400,400);

		northPanel.setLayout(new BorderLayout());
		JLabel label = new JLabel("Please select a reservaiton to cancel! \n");
		northPanel.add(label, BorderLayout.NORTH);
		Border lineBorder1 = BorderFactory.createLineBorder(Color.GRAY);
		northPanel.setBorder(lineBorder1);

		setView();
		scrollPane = new JScrollPane(jlist);
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(scrollPane, BorderLayout.CENTER);

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				cancelReservation();
			}
		});

		southPanel.add(cancelButton, BorderLayout.SOUTH);
		Border lineBorder2 = BorderFactory.createLineBorder(Color.GRAY);
		southPanel.setBorder(lineBorder2);

		frame.add(northPanel, BorderLayout.NORTH);
		frame.add(centerPanel, BorderLayout.CENTER);

		frame.add(southPanel, BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		frame.setVisible(true);

	}

	private void cancelReservation() {
		Reservation r = jlist.getSelectedValue();
		if (r != null) {
			int index = jlist.getSelectedIndex();

			model.remove(index);
			hotel.removeReservation(r);
		}
	}

	private void setView() {
		Iterator<Reservation> iter = hotel.reservationIterator(user.getUserID());
		while(iter.hasNext()) {
			model.addElement(iter.next());
		}

		jlist.setModel(model);
	}

}
