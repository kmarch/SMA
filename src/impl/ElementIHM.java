package impl;

import interfaces.Element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import enumeration.Couleur;

public class ElementIHM extends JPanel {

	private static final long serialVersionUID = -3665765221048444671L;
	
	private BufferedImage image;
	private Couleur couleurActuelle;
	private boolean estTerminator;
	private boolean estNid;
	private boolean estBoite;

	public ElementIHM() {
		super();
		setVisible(true);
	}

	public boolean aChange(Element element) {
		boolean change = false;
		change = change || (element == null && couleurActuelle != null);
		if (element != null) {
			change = change || (element.getCouleur() != this.couleurActuelle);
			change = change || (element.isBoite() != this.estBoite);
			change = change || (element.isNid() != this.estNid);
			change = change
					|| ((!element.isBoite() && !element.isNid()) != this.estTerminator);
		}
		return change;
	}

	public void configure(Element element, int size) {
		this.couleurActuelle = element.getCouleur();
		setSize(size, size);
		if (element.isBoite()) {
			selectImageBoite(element.getCouleur());
			estNid = false;
			estBoite = true;
			estTerminator = false;
		} else if (element.isNid()) {
			estNid = true;
			estBoite = false;
			estTerminator = false;
			selectBackground(element.getCouleur());
			try {
				image = ImageIO.read(new File("cart.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { // is robot/terminator
			estNid = false;
			estBoite = false;
			estTerminator = true;
			selectImageTerminator(element.getCouleur());
		}
	}

	private void selectImageBoite(Couleur couleur) {
		File file;
		switch (couleur) {
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
			return;
		}
		try {
			image = ImageIO.read(file);
		} catch (IOException ex) {
		}
	}

	private void selectImageTerminator(Couleur couleur) {
		File file;
		switch (couleur) {
		case ROUGE:
			file = new File("WALL-E.png");
			break;
		case BLEU:
			file = new File("R2-D2.png");
			break;
		case VERT:
			file = new File("ANDROID.png");
			break;
		default:
			return;
		}
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);	
		
		if(image!= null) {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		}
		
		//super.paintComponent(g);		
		//if (image != null) {
			//
		//}
	}

	public void configureEmpty(int size) {
		estNid = false;
		estBoite = false;
		estTerminator = false;
		couleurActuelle = null;
		setSize(size, size);
		image = null;
		revalidate();
		repaint();
	}

	private void selectBackground(Couleur couleur) {
		switch (couleur) {
		case ROUGE:
			setBackground(Color.RED);
			break;
		case BLEU:
			setBackground(Color.BLUE);
			break;
		case VERT:
			setBackground(Color.GREEN);
			break;
		default:
			setBackground(Color.LIGHT_GRAY);
			break;
		}
	}

}
