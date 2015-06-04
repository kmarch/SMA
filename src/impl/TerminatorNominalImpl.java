package impl;

import interfaces.Element;
import interfaces.ILogger;
import interfaces.ITerminator;
import interfaces.IEchequier;

import java.util.ArrayList;
import java.util.List;

import skynet.Global;
import skynet.Terminator;
import enumeration.Couleur;


public class TerminatorNominalImpl extends Terminator {

	@Override
	protected ITerminator make_manage() {
		return new ITerminator() {

			private int num;
			private int x;
			private int y;
			private Couleur couleur;
			private int batterie;
			private List<ArrayList<Element>> liste;
			private ILogger logger;
			
			@Override
			public void run() {
				new Parallele(this).start();
			}

			public void execution() {
				int etape = 1;
				do{
				Element boitePlusProche = boitePlusProche(listeBoite(liste));
				if(boitePlusProche != null){
					System.out.println("boitePlusProche = " + boitePlusProche.getX() + " " + boitePlusProche.getY());
					int distance = distance(boitePlusProche);
					System.out.println("distance = " + distance);
					//On consid�re que le cout de d�placement d'une case est de 5
					//On regarde ici que la batterie est suffisante pour ramener la boite
					if(getBatterie() > 5*distance){
						etape = 2;
						//tant que on a pas atteint les coordonn�es de la boite
						while((boitePlusProche.getX() != getX()) || (boitePlusProche.getY() != getY()) && etape == 2){
							//On regarde que la boite est toujours la
							if(liste.get(boitePlusProche.getX()).get(boitePlusProche.getY()) != null){
								deplacement(boitePlusProche);
								System.out.println("batterie = " + getBatterie());
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							//g�rer la cas ou la boite n'est plus l�
							else{
								etape = 1;
							}
							
						}
						logger.logPriseBoite(num, boitePlusProche);
						//On a atteint la boite il faut la ramener au nid correspondant
						Element nid = nidCorrespondant(boitePlusProche,listeNids());
						while(!aCoteNid(nid) && etape == 2){
								deplacement(nid);	
								System.out.println("batterie = " + getBatterie());
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}
						if(aCoteNid(nid)){
						logger.logPoseBoite(num, boitePlusProche, nid);
						setBatterie(2000);
						}
					}
					else{
						System.out.println("Pas assez de batterie");
						liste.get(getX()).set(getY(),null); 
						etape = 0;
					}
				}
				}while(etape != 0);
				
				
			}
			
			@Override
			public ITerminator intialisation(ILogger logger, int num, Couleur couleur, int x, int y,
					List<ArrayList<Element>> listePion, int batterie) {
				this.num = num;
				this.couleur = couleur;
				this.x = x;
				this.y = y;
				this.liste = listePion;
				this.batterie = batterie;
				this.logger = logger;
				return this;
			}

			@Override
			public ITerminator fabrique(ILogger logger, int num, Couleur couleur, int i, int j, List<ArrayList<Element>> liste, int batterie){
				Terminator.Component systeme = (new  TerminatorNominalImpl()).newComponent();
				return systeme.manage().intialisation(logger, num, couleur, i, j, liste, batterie);

			}

			//Retourne la liste des boites
			public ArrayList<Element> listeBoite(List<ArrayList<Element>> liste) {
				ArrayList<Element> listeBoite = new ArrayList<Element>();
				for(int i=0;i<liste.size();i++){
					for(int j=0;j<liste.size();j++){
						synchronized (liste) {
							if((liste.get(i).get(j) != null) && (liste.get(i).get(j).isBoite())){
								listeBoite.add(liste.get(i).get(j));
							}
						}			
					}
				}
				return listeBoite;
			}
			
			//Retourne la liste des nids
			public ArrayList<Element> listeNids() {
				ArrayList<Element> listeNids = new ArrayList<Element>();
				for(int i=0;i<liste.size();i++){
					for(int j=0;j<liste.size();j++){
						if((liste.get(i).get(j) != null) && (liste.get(i).get(j).isNid())){
							listeNids.add(liste.get(i).get(j));
						}
						
					}
				}
				return listeNids;
			}

			//Retourne la boite la plus proche ayant la m�me couleur que le robot
			public Element boitePlusProche(ArrayList<Element> boite) {
				int distance = 10000000;
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
			
			//Retoune la distance entre la boite la plus proche et le nid ayant la m�me couleur
			public Element nidCorrespondant(Element boite, ArrayList<Element> nids){
				Element nidCorrespondant = null;
				for(Element nid : nids){
					if(nid.getCouleur().equals(boite.getCouleur())){
						nidCorrespondant = nid;	
					}
				}
				return nidCorrespondant;
			}
			
			//Retourne le distance de la boite la plus proche
			public int distanceBoitePlusProche(Element boite) {
				int x = getX() - boite.getX();
				int y = getY() - boite.getY();
				int dist = (Math.abs(x) + Math.abs(y));
				return dist;
			}
			
			//Retoune la distance entre la boite la plus proche et le nid ayant la m�me couleur
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
				int distanceBoiteToNid = distanceBoitePlusProcheToNid(boite,nidCorrespondant(boite,listeNids()));
				System.out.println("distanceDeLaBoiteAuNid = " + distanceBoiteToNid);
				return distanceToBoite + distanceBoiteToNid;
				
			}
			//Deplace le robot
			public void deplacement(Element boite){
				//boite en bas � droite par rapport au robot
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
				
				//boite en haut � droite par rapport au robot
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
				
				//boite en bas � gauche par rapport au robot
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
				
				//boite en haut � gauche par rapport au robot
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
				synchronized (liste) {
				//boite en dessous
					 if((boite.getX() == getX()) && (boite.getY() > getY())){
						System.out.println("dessous");
						if(liste.get(getX()).get(getY() +1) == null || boite.getY() == y+1){
							Element e = liste.get(getX()).get(getY());
							liste.get(getX()).set(getY() +1,e);
							liste.get(getX()).set(getY(),null); 
							y++;
							batterie = batterie - 5;
						}
					 }

							
							
					//boite en dessus
					 if((boite.getX() == getX()) && (boite.getY() < getY())){
						System.out.println("dessus");
						if(liste.get(getX()).get(getY() -1) == null || boite.getY() == y-1){
							Element f = liste.get(getX()).get(getY());
							liste.get(getX()).set(getY() -1,f);
							liste.get(getX()).set(getY(),null); 
							y--;
							batterie = batterie - 5;
						}
					}	
				//boite � gauche
					if((boite.getX() < getX()) && (boite.getY() == getY())){
						//deplace vers la gauche
						System.out.println("gauche");
						if(liste.get(getX() -1).get(getY()) == null || boite.getX() == x-1){
							Element g = liste.get(getX()).get(getY());
							liste.get(getX()-1).set(getY(),g);
							liste.get(getX()).set(getY(),null);
							x--;
							batterie = batterie - 5;
						}
					}
				
				//boite � droite
				if((boite.getX() > getX()) && (boite.getY() == getY())){
					//deplace vers la droite
					System.out.println("droite");
					if(liste.get(getX() +1).get(getY()) == null || boite.getX() == x+1){
						Element h = liste.get(getX()).get(getY());
						liste.get(getX()+1).set(getY(),h);
						liste.get(getX()).set(getY(),null); 
						x++;
						batterie = batterie - 5;
					}
				}
			}
				logger.logDeplacement(num, x, y);
				System.out.println("boite.getX() : " + boite.getX() + " boite.getY() : " + boite.getY()
						+ " getX() : " + getX() + " getY() : " + getY());
			}
			
			//Permet de savoir si on est � cot� du nid 
			public boolean aCoteNid(Element nid){
				//nid en dessous
				if((nid.getX() == getX()) && (nid.getY() == y+1)){
					return true;
					}
				
				//nid en dessus
				else if((nid.getX() == getX()) && (nid.getY() == y-1)){
					return true;
					}
				
				//boite � gauche
				else if((nid.getX() == x-1) && (nid.getY() == getY())){
					//deplace vers la gauche
					return true;
					}
				
				//boite � droite
				else if((nid.getX() == x+1) && (nid.getY() == getY())){
					//deplace vers la droite
					return true;
				}
				
				else {
					return false;
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

			public int getBatterie() {
				return batterie;
			}

			public void setBatterie(int batterie) {
				this.batterie = batterie;
				
			}
		};
	}

	


}
