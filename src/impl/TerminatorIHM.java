package impl;

import interfaces.ITerminator;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import enumeration.Couleur;

@SuppressWarnings("serial")
public class TerminatorIHM extends JFrame implements Runnable {

	private JLabel id;
	private JLabel couleur;
	private JProgressBar batterie;
	private JPanel boite;
	private BufferedImage image;
	
	private ITerminator myTerminator;

	public TerminatorIHM(ITerminator terminator) {
		super("Terminator " + terminator.getId()); // invoke the JFrame
													// constructor
		this.setVisible(true);
		myTerminator = terminator;
		setSize(150, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container target = this.getContentPane();
		id = new JLabel("Termanitor n°" + terminator.getId());
		id.setAlignmentX(Component.CENTER_ALIGNMENT);
		target.add(id);
		couleur = new JLabel("Je suis " + terminator.getCouleur());
		couleur.setAlignmentX(Component.CENTER_ALIGNMENT);
		target.add(couleur);
		batterie = new JProgressBar(0, terminator.getMaxBatterie());
		batterie.setValue(terminator.getBatterie());
		batterie.setAlignmentX(Component.CENTER_ALIGNMENT);
		target.add(batterie);
		boite = new JPanel();
		boite.setAlignmentX(Component.CENTER_ALIGNMENT);
		boite.setSize(30, 30);
		boite.setVisible(true);
		target.add(boite);
		BoxLayout boxLayout = new BoxLayout(target, BoxLayout.Y_AXIS);
		setLayout(boxLayout);

	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			batterie.setValue(myTerminator.getBatterie());
			Couleur couleurBoite = null;
			synchronized (myTerminator) {
				if(myTerminator.getBoite()!=null)
				couleurBoite = myTerminator.getBoite().getCouleur();
			}
			File file;
			if(couleurBoite!=null) {
				switch (couleurBoite) {
				case ROUGE:
					file = new File("strawberry.png");
					break;
				case BLEU:
					file = new File("grape.png");
					break;
				case VERT:
					file = new File("lemon.png");
					break;
				default:
					// nothing
					return;
				}
				try {
					image = ImageIO.read(file);
					boite.getGraphics().drawImage(image, 0, 0, boite.getWidth(), boite.getHeight(), null);
				} catch (IOException ex) {
				}				
			} else {
				image =null;
				boite.getGraphics().clearRect(0, 0, boite.getWidth(), boite.getHeight());
			}
			
			
			
			
			

		}

	}

}
