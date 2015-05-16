package impl;

import interfaces.Element;
import interfaces.ITerminator;
import interfaces.IEchequier;

import java.util.ArrayList;
import java.util.List;

import skynet.Global;
import skynet.Terminator;
import enumeration.Couleur;


public class TerminatorImpl extends Terminator {

	@Override
	protected ITerminator make_manage() {
		return new ITerminator() {

			private int x;
			private int y;
			private Couleur couleur;
			private int batterie;
			private List<ArrayList<Element>> liste;
			
			@Override
			public void run() {
				new Parallele(this).start();
			}

			public void execution() {
				do{
				Element boitePlusProche = boitePlusProche(listeBoite(liste));
				if(boitePlusProche != null){
					System.out.println("boitePlusProche = " + boitePlusProche.getX() + " " + boitePlusProche.getY());
					int distance = distance(boitePlusProche);
					System.out.println("distance = " + distance);
					//On considére que le cout de déplacement d'une case est de 5
					//On regarde ici que la batterie est suffisante pour ramener la boite
					if(getBatterie() > 5*distance){
						//tant que on a pas atteint les coordonnées de la boite
						while((boitePlusProche.getX() != getX()) || (boitePlusProche.getY() != getY())){
							Element boitePlusProcheAvantDeplacement = boitePlusProche(listeBoite(liste));
							//On regarde que la boite la plus proche est toujours la même
							if(boitePlusProche.equals(boitePlusProcheAvantDeplacement)){
								deplacement(boitePlusProche, liste);
								System.out.println("batterie = " + getBatterie());
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							//gérer la cas ou la boite n'est plus là
							else{
						
							}
						}
						//On a atteint la boite il faut la ramener au nid correspondant
						Element rameneAuNid = nidCorrespondant(listeNids(liste));
						while((rameneAuNid.getX() != getX()) && (rameneAuNid.getY() != getY())){
								deplacement(rameneAuNid, liste);	
						}
						setBatterie(100);
					}
					else{
						System.out.println("Pas assez de batterie");
					}
				}
				}while(true);
				
				
			}
			
			@Override
			public ITerminator intialisation(Couleur couleur, int x, int y,
					List<ArrayList<Element>> listePion, int batterie) {
				this.couleur = couleur;
				this.x = x;
				this.y = y;
				this.liste = listePion;
				this.batterie = batterie;
				return this;
			}

			@Override
			public ITerminator fabrique(Couleur couleur, int i, int j, List<ArrayList<Element>> liste, int batterie){
				
				Terminator.Component systeme = (new  TerminatorImpl()).newComponent();
				return systeme.manage().intialisation(couleur, i, j, liste, batterie);

			}

			//Retourne la liste des boites
			@Override
			public ArrayList<Element> listeBoite(List<ArrayList<Element>> liste) {
				ArrayList<Element> listeBoite = new ArrayList<Element>();
				for(int i=0;i<liste.size();i++){
					for(int j=0;j<liste.size();j++){
						if(liste.get(i).get(j) != null){
							if(liste.get(i).get(j).isBoite()){
								listeBoite.add(liste.get(i).get(j));
							}
						}
						
					}
				}
				return listeBoite;
			}
			
			//Retourne la liste des nids
			@Override
			public ArrayList<Element> listeNids(List<ArrayList<Element>> liste) {
				ArrayList<Element> listeNids = new ArrayList<Element>();
				for(int i=0;i<liste.size();i++){
					for(int j=0;j<liste.size();j++){
						if(liste.get(i).get(j) != null){
							if(liste.get(i).get(j).isNid()){
								listeNids.add(liste.get(i).get(j));
							}
						}
						
					}
				}
				return listeNids;
			}

			//Retourne la boite la plus proche ayant la même couleur que le robot
			@Override
			public Element boitePlusProche(ArrayList<Element> boite) {
				System.out.println("Taille de la liste de boite : " + boite.size());
				int distance = 100;
				int dist;
				Element plusProche = null;
				for(Element elem : boite){
					if(getCouleur().equals(elem.getCouleur())){
						int x = getX() - elem.getX();
						int y = getY() - elem.getY();
						dist = (Math.abs(x) + Math.abs(y));
						if(dist<distance){
							distance = dist;
							plusProche = elem;
							}
						}
				}
				return plusProche;
			}
			
			//Retoune la distance entre la boite la plus proche et le nid ayant la même couleur
			public Element nidCorrespondant(ArrayList<Element> nids){
				Element nidCorrespondant = null;
				for(Element nid : nids){
					if(nid.getCouleur().equals(getCouleur())){
						nidCorrespondant = nid;	
					}
				}
				return nidCorrespondant;
			}
			
			//Retourne le distance de la boite la plus proche
			@Override
			public int distanceBoitePlusProche(Element boite) {
				int x = getX() - boite.getX();
				int y = getY() - boite.getY();
				int dist = (Math.abs(x) + Math.abs(y));
				return dist;
			}
			
			//Retoune la distance entre la boite la plus proche et le nid ayant la même couleur
			public int distanceBoitePlusProcheToNid(Element boite, Element nid){
				int x = boite.getX() - nid.getX();
				int y = boite.getY() - nid.getY();
				int dist = (Math.abs(x) + Math.abs(y));
				return dist;
			}
			
			//Retourne la distance entre le robot et la boite plus la distance entre la boite et le nids
			public int distance(Element boite){
				int distanceToBoite = distanceBoitePlusProche(boite);
				System.out.println("distanceDeLaBoite = " + distanceToBoite);
				int distanceBoiteToNid = distanceBoitePlusProcheToNid(boite,nidCorrespondant(listeNids(liste)));
				System.out.println("distanceDeLaBoiteAuNid = " + distanceBoiteToNid);
				return distanceToBoite + distanceBoiteToNid;
				
			}
			//Deplace le robot
			public void deplacement(Element boite,List<ArrayList<Element>> liste){
				//boite en bas à droite par rapport au robot
				System.out.println("boite.getX() : " + boite.getX() + " boite.getY() : " + boite.getY()
						+ " getX() : " + getX() + " getY() : " + getY());
				if((boite.getX() > getX()) && (boite.getY() > getY())){
					System.out.println("Bas droite");
					//deplace vers la droite
					if(liste.get(getX() +1).get(getY()) == null){
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()+1).set(getY(),e);
						liste.get(getX()).set(getY(),null); 
						x++;
						batterie = batterie - 5;
					}
					//deplace vers le bas
					else if(liste.get(getX()).get(getY() +1) == null){
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()).set(getY() +1,e);
						liste.get(getX()).set(getY(),null); 
						y++;
						batterie = batterie - 5;
					}
				}
				
				//boite en haut à droite par rapport au robot
				else if((boite.getX() > getX()) && (boite.getY() < getY())){
					System.out.println("haut droite");
					//deplace vers la droite
					if(liste.get(getX() +1).get(getY()) == null){
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()+1).set(getY(),e);
						liste.get(getX()).set(getY(),null); 
						x++;
						batterie = batterie - 5;
					}
					//deplace vers le haut
					else if(liste.get(getX()).get(getY() -1) == null){
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()).set(getY() -1,e);
						liste.get(getX()).set(getY(),null); 
						y--;
						batterie = batterie - 5;
					}	
				}
				
				//boite en bas à gauche par rapport au robot
				else if((boite.getX() < getX()) && (boite.getY() > getY())){
					System.out.println("Bas gauche");
					//deplace vers la gauche
					if(liste.get(getX() -1).get(getY()) == null){
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()-1).set(getY(),e);
						liste.get(getX()).set(getY(),null); 
						x--;
						batterie = batterie - 5;
					}
					//deplace vers le bas
					else if(liste.get(getX()).get(getY() +1) == null){
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()).set(getY() +1,e);
						liste.get(getX()).set(getY(),null); 
						y++;
						batterie = batterie - 5;
					}
				}
				
				//boite en haut à gauche par rapport au robot
				else if((boite.getX() < getX()) && (boite.getY() < getY())){
					System.out.println("Haut gauche");
					//deplace vers la gauche
					if(liste.get(getX() -1).get(getY()) == null){
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()-1).set(getY(),e);
						liste.get(getX()).set(getY(),null); 
						x--;
						batterie = batterie - 5;
					}
					//deplace vers le haut
					else if(liste.get(getX()).get(getY() -1) == null){
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()).set(getY() -1,e);
						liste.get(getX()).set(getY(),null); 
						y--;
						batterie = batterie - 5;
					}
				}
				//boite en dessous
				else if((boite.getX() == getX()) && (boite.getY() > getY())){
					System.out.println("dessous");
					if(liste.get(getX()).get(getY() +1) == null){
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()).set(getY() +1,e);
						liste.get(getX()).set(getY(),null); 
						y++;
						batterie = batterie - 5;
					}
				}
				
				//boite en dessus
				else if((boite.getX() == getX()) && (boite.getY() < getY())){
					System.out.println("dessus");
					if(liste.get(getX()).get(getY() -1) == null){
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()).set(getY() -1,e);
						liste.get(getX()).set(getY(),null); 
						y--;
						batterie = batterie - 5;
					}
				}
				
				//boite à gauche
				else if((boite.getX() < getX()) && (boite.getY() == getY())){
					//deplace vers la gauche
					System.out.println("gauche");
					if(liste.get(getX() -1).get(getY()) == null){
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()-1).set(getY(),e);
						liste.get(getX()).set(getY(),null);
						x--;
						batterie = batterie - 5;
					}
				}
				
				//boite à droite
				else if((boite.getX() > getX()) && (boite.getY() == getY())){
					//deplace vers la droite
					System.out.println("droite");
					if(liste.get(getX() +1).get(getY()) == null){
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()+1).set(getY(),e);
						liste.get(getX()).set(getY(),null); 
						x++;
						batterie = batterie - 5;
					}
				}
			}

			@Override
			public int getX() {
				return this.x;
			}

			@Override
			public int getY() {
				return this.y;
			}
			
			@Override
			public boolean isBoite() {
				return false;
			}
			

			@Override
			public boolean isNid() {
				return false;
			}

			@Override
			public Couleur getCouleur() {
				return couleur;
			}

			@Override
			public int getBatterie() {
				return batterie;
			}

			@Override
			public void setBatterie(int batterie) {
				this.batterie = batterie;
				
			}
		};
	}

	


}
