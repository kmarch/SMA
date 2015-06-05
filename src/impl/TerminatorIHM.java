package impl;

import interfaces.ITerminator;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class TerminatorIHM extends JFrame implements Runnable {

	private JLabel id;
	private JLabel couleur;
	private JProgressBar batterie;
	
	private ITerminator myTerminator;

	public TerminatorIHM(ITerminator terminator) {
		super("Terminator "+terminator.getId()); // invoke the JFrame constructor
		this.setVisible(true);
		myTerminator = terminator;
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container target = this.getContentPane();
		id = new JLabel("Termanitor n°"+terminator.getId());
		id.setAlignmentX( Component.CENTER_ALIGNMENT );
		target.add(id);
		couleur = new JLabel("Je suis "+terminator.getCouleur());
		couleur.setAlignmentX( Component.CENTER_ALIGNMENT );
		target.add(couleur);
		batterie = new JProgressBar(0, 2000);
		batterie.setValue(terminator.getBatterie());
		batterie.setAlignmentX( Component.CENTER_ALIGNMENT );
		target.add(batterie);
		BoxLayout boxLayout = new BoxLayout(target,BoxLayout.Y_AXIS);
		setLayout(boxLayout);
		
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			batterie.setValue(myTerminator.getBatterie());
		}
		
	}

}
