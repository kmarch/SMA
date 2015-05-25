package impl;

import interfaces.Element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import enumeration.Couleur;

public class ElementIHM extends JPanel {

	private static final long serialVersionUID = -3665765221048444671L;

	private BufferedImage image;
	
    public ElementIHM()
    {
        super();
    }

    public void configure(Element element, int size)
    {
      setSize(size,size);
      if (element.isBoite()) {
    	  selectImageBoite(element.getCouleur());
      }else if (element.isNid()) {
          selectBackground(element.getCouleur());
          try {
			image = ImageIO.read(new File("cart.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
      } else { //is robot/terminator
          selectImageTerminator(element.getCouleur());
      }
      
      setVisible(true);
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
         } catch (IOException ex) {}
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
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null); // see javadoc for more info on the parameters            
    }
    
	public void configureEmpty(int size) {
    	setSize(size,size);
        setVisible(true);
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
