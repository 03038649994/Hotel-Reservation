import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * A class representing the print frame
 * @author Karan Bhargava
 * @version 1.2016.991
 */
public class PrintFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	/**
	 * A constructor to construct the printFrame for the receipts 
	 * @param h - the hotel we're working with
	 * @param parent - the parent of the current frame
	 */
	PrintFrame(Hotel h, MakeReservationView parent)
	{
		setTitle("Hotel Reservation Receipt Print");
		setSize(600,600);
		setResizable(false);

		JPanel northPanel = new JPanel();
		JLabel label = new JLabel("Account information print");
		northPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		label.setFont(new Font("Sans-Serif", Font.PLAIN, 24));
		northPanel.add(label);

		JPanel centerPanel = new JPanel();
		JRadioButton simpleButton = new JRadioButton("Print Simple Receipt");
		JRadioButton comprehensiveButton = new JRadioButton("Print Comprehensive Receipt");

		ButtonGroup group = new ButtonGroup();
		group.add(simpleButton);
		group.add(comprehensiveButton);
		centerPanel.add(simpleButton);
		centerPanel.add(comprehensiveButton);

		Border lineBorder1 = BorderFactory.createLineBorder(Color.GRAY);
		centerPanel.setBorder(lineBorder1);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(centerPanel, BorderLayout.NORTH);
		final JTextArea textArea = new JTextArea();
		bottomPanel.add(textArea, BorderLayout.CENTER);

		JButton closeBtn = new JButton("Close");
		bottomPanel.add(closeBtn, BorderLayout.SOUTH);
		closeBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				parent.getParental().setLocation(PrintFrame.this.getLocation());
				parent.getParental().setVisible(true);
				parent.dispose();
				PrintFrame.this.dispose();
			}
		});

		simpleButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				ReceiptFormatter r = new SimpleReceiptFormat(h);
				textArea.setText(r.format());
			}
		});

		comprehensiveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				ReceiptFormatter r = new ComprehensiveReceiptFormat(h);
				textArea.setText(r.format());
			}
		});
		
		add(northPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}
