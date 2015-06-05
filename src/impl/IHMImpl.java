package impl;

import interfaces.Element;
import interfaces.IIHM;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import enumeration.Couleur;
import skynet.IHM;

public class IHMImpl extends IHM {

	@Override
	protected IIHM make_affichage() {
		return new IIHM() {

			public static final int JPANEL_SIZE = 15;
			private JFrame frame;
			private int nbRafraichissement;
			
			@Override
			public void run() {
				new ParalleleIHM(this).start();
			}

			private void affichage() {
				
				List<ArrayList<Element>> l = requires().map().getMap();
				
				if (l == null) {
					return;
				}
				int size = l.size();

		        Container container = frame.getContentPane();
		        ElementIHM temp = null;
		        
		        for ( int i = 0; i < size; i++ )
		        {
		        	for ( int j = 0; j < size; j++ ) {
		        		temp = (ElementIHM) container.getComponent(i*size+j);
		        		Element element = l.get(i).get(j);
		        		if(temp.aChange(element)) {
			        		if(element==null) {
			        			temp.configureEmpty(size); 
			        		} else {
			        			temp.configure(element, size);
			        		}
			        		temp.revalidate();
			        		temp.repaint();
		        		}
		        	}		            
		        }
				nbRafraichissement++;
			}
			
			private void initialize() {
				List<ArrayList<Element>> l;
				
				while ((l=requires().map().getMap()) == null) {
					
				}
				int size = l.size();
		        frame.setSize( size*JPANEL_SIZE, size*JPANEL_SIZE);
		        frame.setResizable( false );
		        frame.setLocationRelativeTo( null );
		        frame.setLayout( new GridLayout(size,size) );
		        
		        Container container = frame.getContentPane();
		        container.removeAll();
		        ElementIHM temp = null;

		        for ( int i = 0; i < size; i++ )
		        {
		        	for ( int j = 0; j < size; j++ ) {
		        		temp = new ElementIHM();
		        		Element element = l.get(i).get(j);
		        		if(element==null) temp.configureEmpty(size); 
		        		else temp.configure(element, size);
			            container.add(temp);
		        	}		            
		        }
		        
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
		        frame.setVisible( true );
			}

			@Override
			public void execution() {
				frame = new JFrame("SMA - Terminator Team");
				initialize();
				do {
					affichage();
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				} while (true);
			}
		};
	}

}
