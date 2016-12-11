import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A class representing all the manager GUI and functions
 *@author Matthew Binning
 *@version 11.5818.221
 *	
 */
public class ManagerMenu extends JFrame
{
	
	//instance variables of the manager View
	private static final int BUTTON_WIDTH=400;
	private Hotel hotel;
	private static final int BUTTON_HEIGHT=50;
	private static final Dimension btnDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
	private static final long serialVersionUID = 1L;

	/**
	 * A constructor to construct the manager's panel
	 * @param h - the hotel we're working with
	 * @param parent - the managerView's parent frame
	 */
	public ManagerMenu(Hotel h, JFrame parent)
	{
		hotel = h;
		setSize(600, 600);
		setTitle("Hotel Reservation System");
		setResizable(false);

		JButton loadBtn = new JButton("Load Existing Reservation");
		loadBtn.setPreferredSize(btnDim);

		
		//Deserialize the file
		loadBtn.addActionListener(new ActionListener()
		{
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String fileName = "hotel.ser";
				File fileIn = new File(fileName);

				if(fileIn.exists()) {
					try {
						FileInputStream fileInput = new FileInputStream(fileIn);
						ObjectInputStream objIn = new ObjectInputStream(fileInput);

						hotel = (Hotel) objIn.readObject();
						objIn.close();
						fileInput.close();						

					} catch (IOException i ){
						i.printStackTrace();
					} catch (ClassNotFoundException c){
						c.printStackTrace();
					}
				}

				//Confirmation that the file for de-serialized
				JDialog conflict = new JDialog();
				conflict.setModalityType(ModalityType.APPLICATION_MODAL);
				conflict.setResizable(false);
				conflict.setLayout(new GridLayout(2, 0));
				conflict.add(new JLabel("Deserialized the file!"));
				JButton doneButton = new JButton("Yay :)");
				doneButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						conflict.dispose();
					}
				});
				conflict.add(doneButton);
				conflict.pack();
				conflict.setVisible(true);;
			}
		});

		//RoomView and monthView
		JButton viewBtn = new JButton("View Information");
		viewBtn.setPreferredSize(btnDim);

		viewBtn.addActionListener(new
				ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ManagerView m = new ManagerView(hotel, ManagerMenu.this);
				m.setLocation(ManagerMenu.this.getLocation());
				m.setVisible(true);
				ManagerMenu.this.setVisible(false);
			}
		});
		
		//Serialize the file
		JButton saveBtn = new JButton("Save Reservations and Quit");
		saveBtn.setPreferredSize(btnDim);

		saveBtn.addActionListener(new
				ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(h.isEmpty()) {
					System.exit(0);
				} 

				String fileName = "hotel.ser";
				File fileIn = new File(fileName);
				try {
					if(!fileIn.exists()) {
						FileOutputStream fileOut = new FileOutputStream(fileIn);
						ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
						objOut.writeObject(hotel);
						objOut.close();
						fileOut.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				System.exit(0);
			}
		});
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

		setLayout(new BorderLayout());
		add(label, BorderLayout.NORTH);
		add(btnPanel, BorderLayout.CENTER);
		add(back, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}