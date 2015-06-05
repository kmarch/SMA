package impl;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TerminatorIHM extends JFrame {

	private JPanel panel;

	public TerminatorIHM(int id) {
		super("Terminator "+id); // invoke the JFrame constructor
		setSize(150, 100);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new FlowLayout()); // set the layout manager
	}

}
