import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ManagerMenu extends JFrame
{
	private static final int BUTTON_WIDTH=400;
	private Hotel hotel;
	private static final int BUTTON_HEIGHT=50;
	private static final Dimension btnDim = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
	private static final long serialVersionUID = 1L;

	public ManagerMenu(Hotel h, JFrame parent)
	{
		hotel = h;
		setSize(500, 500);
		setTitle("Hotel Reservation System");

		JButton loadBtn = new JButton("Load Existing Reservation");
		loadBtn.setPreferredSize(btnDim);

		loadBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChoose = new JFileChooser();
				fileChoose.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChoose.showOpenDialog(ManagerMenu.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChoose.getSelectedFile();
					ObjectInputStream in = null;

					try	{
						in = new ObjectInputStream(new FileInputStream(selectedFile));
						hotel = (Hotel) in.readObject();
						in.close();
					} catch (IOException exception)
					{
						JOptionPane.showMessageDialog(null, exception + "io");
					}
					catch (ClassNotFoundException exception)
					{
						JOptionPane.showMessageDialog(null, exception + "class");
					}
				}
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
				ManagerView m = new ManagerView(hotel, ManagerMenu.this);
				m.setLocation(ManagerMenu.this.getLocation());
				m.setVisible(true);
				ManagerMenu.this.setVisible(false);
			}
		}
				);

		JButton saveBtn = new JButton("Save Reservations and Quit");
		saveBtn.setPreferredSize(btnDim);

		saveBtn.addActionListener(new
				ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try {
					File file = new File("./Reservation.txt");
					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
					out.writeObject(hotel);
					out.close();
					System.exit(0);

				} catch(IOException exception) {
					JOptionPane.showMessageDialog(null, exception);
				}
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

		setLayout(new BorderLayout());
		add(label, BorderLayout.NORTH);
		add(btnPanel, BorderLayout.CENTER);
		add(back, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}