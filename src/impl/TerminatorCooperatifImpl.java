package impl;

import interfaces.Element;
import interfaces.ILogger;
import interfaces.ITerminator;

import java.util.ArrayList;
import java.util.List;

import skynet.Terminator;
import enumeration.Couleur;

public class TerminatorCooperatifImpl extends Terminator {

	@Override
	protected ITerminator make_manage() {
		return new ITerminator() {

			private int num;
			private int x;
			private int y;
			private Couleur couleur;
			private int batterie;
			private int maxBatterie;
			private List<ArrayList<Element>> liste;
			private Element boite;
			private ILogger logger;

			@Override
			public void run() {
				new Parallele(this).start();
			}

			public void execution() {
				int etape = 1;
				do {
					Element boitePlusProche;
					
					synchronized (liste) {
						boitePlusProche = boitePlusProche(listeBoite());
						ajoutDeNouveauxTerminator();
					}

					// robot de cette couleur
					if(boitePlusProche != null){
						int distance = distance(boitePlusProche);
						//On consid�re que le cout de d�placement d'une case est de 5
						//On regarde ici que la batterie est suffisante pour ramener la boite
						if(batterie > 5*distance){
							etape = 2;
							//tant que on a pas atteint les coordonn�es de la boite
							while((boitePlusProche.getX() != getX()) || (boitePlusProche.getY() != getY()) && etape == 2){
								//On regarde que la boite est toujours la
								if(liste.get(boitePlusProche.getX()).get(boitePlusProche.getY()) != null){
									deplacement(boitePlusProche);
									if(batterie == 0){
										System.out.println("Plus de batterie");
										liste.get(getX()).set(getY(),null); 
										etape = 0;
									}
									
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
									if(batterie == 0){
										System.out.println("Plus de batterie");
										liste.get(getX()).set(getY(),null); 
										etape = 0;
									}
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							}
							if(aCoteNid(nid)){
								logger.logPoseBoite(num, boitePlusProche, nid);
								setBoite(null);
								if(getCouleur().equals(nid.getCouleur())){
									batterie = batterie + ((2 * maxBatterie) /3);
								}
								else{
									batterie = batterie + ((1 * maxBatterie) /3);
								}
							}
							
							if(batterie > maxBatterie){
								batterie = maxBatterie;
							}
						}
						else{
							batterie --;
							if(batterie == 0){
								System.out.println("Plus de batterie");
								liste.get(getX()).set(getY(),null); 
								etape = 0;
							}
						}
					}
					}while(etape != 0);
			}

			private void ajoutDeNouveauxTerminator() {
				// on va regarder s'il faut cr�er un robot
				ArrayList<Element> listeBoite = listeBoite();
				ArrayList<ITerminator> listeRobots = listeRobots();
				int [] nbBoite = { 0, 0, 0 };
				int [] nbRobots = {0 , 0, 0};
				int dernierId = 0;
				for (Element boite : listeBoite) {
					switch (boite.getCouleur()) {
					case BLEU:
						nbBoite[0]++;
						break;
					case ROUGE:
						nbBoite[1]++;
						break;
					case VERT:
						nbBoite[2]++;
					}
				}
				for (ITerminator robot : listeRobots) {
					if (robot.getId() > dernierId) {
						dernierId = robot.getId();
					}
					switch (robot.getCouleur()) {
					case BLEU:
						nbRobots[0]++;
						break;
					case ROUGE:
						nbRobots[1]++;
						break;
					case VERT:
						nbRobots[2]++;
					}
				}
				dernierId++;

				if (nbBoite[0] > nbRobots[0] * 2) {
					dernierId = creationRobotParCouleur(dernierId, Couleur.BLEU);
				}
				if (nbBoite[1] > nbRobots[1] * 2) {
					dernierId = creationRobotParCouleur(dernierId, Couleur.ROUGE);
					
				}
				if (nbBoite[2] > nbRobots[2] * 2) {
					dernierId = creationRobotParCouleur(dernierId, Couleur.VERT);
				}
			}

			private int creationRobotParCouleur(int dernierId, Couleur couleur) {
				ITerminator t800;
				if ((x + 1) < liste.size() && liste.get(x + 1).get(y) == null) {
					t800 = fabrique(logger, dernierId, couleur, x + 1, y, liste, maxBatterie,null);
					liste.get(x + 1).set(y, t800);
					t800.run();
					logger.logCreeRobot(num, couleur);
					return dernierId++;
				} else if (x > 0 && liste.get(x - 1).get(y) == null) {
					t800 = fabrique(logger, dernierId, couleur, x - 1, y, liste, maxBatterie,null);
					liste.get(x - 1).set(y, t800);
					logger.logCreeRobot(num, couleur);
					t800.run();
					return dernierId++;
				} else if ((y + 1) < liste.size() && liste.get(x).get(y + 1) == null) {
					t800 = fabrique(logger, dernierId, couleur, x, y  + 1, liste, maxBatterie,null);
					liste.get(x).set(y + 1, t800);
					logger.logCreeRobot(num, couleur);
					t800.run();
					return dernierId++;
				} else if (y > 0 && liste.get(x).get(y - 1) == null) {
					t800 = fabrique(logger, dernierId, couleur, x, y - 1, liste, maxBatterie,null);
					liste.get(x).set(y - 1, t800);
					logger.logCreeRobot(num, couleur);
					t800.run();
					return dernierId++;
				}
				return -1;
			}

			@Override
			public ITerminator intialisation(ILogger logger, int num,
					Couleur couleur, int x, int y,
					List<ArrayList<Element>> listePion, int batterie, Element boite) {
				this.num = num;
				this.couleur = couleur;
				this.x = x;
				this.y = y;
				this.liste = listePion;
				this.batterie = batterie;
				this.maxBatterie = batterie;
				this.logger = logger;
				this.boite = boite;
				return this;
			}

			@Override
			public ITerminator fabrique(ILogger logger, int num,
					Couleur couleur, int i, int j,
					List<ArrayList<Element>> liste, int batterie, Element element) {
				Terminator.Component systeme = (new TerminatorCooperatifImpl())
						.newComponent();
				return systeme.manage().intialisation(logger, num, couleur, i,
						j, liste, batterie, element);

			}

			// Retourne la liste des boites
			public ArrayList<Element> listeBoite() {
				ArrayList<Element> listeBoite = new ArrayList<Element>();
				for (int i = 0; i < liste.size(); i++) {
					for (int j = 0; j < liste.size(); j++) {
						if (liste.get(i).get(j) != null) {
							if (liste.get(i).get(j).isBoite()) {
								listeBoite.add(liste.get(i).get(j));
							}
						}

					}
				}
				return listeBoite;
			}

			public ArrayList<ITerminator> listeRobots() {
				ArrayList<ITerminator> listeBoite = new ArrayList<ITerminator>();
				for (int i = 0; i < liste.size(); i++) {
					for (int j = 0; j < liste.size(); j++) {
						if (liste.get(i).get(j) != null
								&& !liste.get(i).get(j).isBoite()
								&& !liste.get(i).get(j).isNid()) {
							listeBoite.add((ITerminator) liste.get(i).get(j));

						}

					}
				}
				return listeBoite;
			}

			// Retourne la liste des nids
			public ArrayList<Element> listeNids() {
				ArrayList<Element> listeNids = new ArrayList<Element>();
				for (int i = 0; i < liste.size(); i++) {
					for (int j = 0; j < liste.size(); j++) {
						if (liste.get(i).get(j) != null) {
							if (liste.get(i).get(j).isNid()) {
								listeNids.add(liste.get(i).get(j));
							}
						}

					}
				}
				return listeNids;
			}

			// Retourne la boite la plus proche ayant la m�me couleur que le
			// robot
			public Element boitePlusProche(ArrayList<Element> boite) {

				int distance = 10000000;
				int dist;
				Element plusProche = null;
				for (Element elem : boite) {
					if ((getCouleur().equals(elem.getCouleur())) && (!(((BoiteImpl) elem)
							.isReserved()))) {
						int x = getX() - elem.getX();
						int y = getY() - elem.getY();
						dist = (Math.abs(x) + Math.abs(y));
						if (dist < distance) {
							distance = dist;
							plusProche = elem;
						}
					}
				}
				if(plusProche != null) {
					((BoiteImpl) plusProche).reserver();
				}
				return plusProche;
				
			}

			// Retoune la distance entre la boite la plus proche et le nid ayant
			// la m�me couleur
			public Element nidCorrespondant(Element boite,
					ArrayList<Element> nids) {
				Element nidCorrespondant = null;
				for (Element nid : nids) {
					if (nid.getCouleur().equals(boite.getCouleur())) {
						nidCorrespondant = nid;
					}
				}
				return nidCorrespondant;
			}

			// Retourne le distance de la boite la plus proche
			public int distanceBoitePlusProche(Element boite) {
				int x = getX() - boite.getX();
				int y = getY() - boite.getY();
				int dist = (Math.abs(x) + Math.abs(y));
				return dist;
			}

			// Retoune la distance entre la boite la plus proche et le nid ayant
			// la m�me couleur
			public int distanceBoitePlusProcheToNid(Element boite, Element nid) {
				int x = boite.getX() - nid.getX();
				int y = boite.getY() - nid.getY();
				int dist = (Math.abs(x) + Math.abs(y));
				return dist;
			}

			// Retourne la distance entre le robot et la boite plus la distance
			// entre la boite et le nids
			public int distance(Element boite) {
				int distanceToBoite = distanceBoitePlusProche(boite);
				int distanceBoiteToNid = distanceBoitePlusProcheToNid(boite,
						nidCorrespondant(boite, listeNids()));
				return distanceToBoite + distanceBoiteToNid;

			}

			// Deplace le robot
			public void deplacement(Element boite) {
				// boite en bas � droite par rapport au robot
				if ((boite.getX() > getX()) && (boite.getY() > getY())) {
					// deplace vers la droite
					if (liste.get(getX() + 1).get(getY()) == null) {
						Element e = liste.get(getX()).get(getY());
						liste.get(getX() + 1).set(getY(), e);
						liste.get(getX()).set(getY(), null);
						x++;
						batterie = batterie - 5;
					}
					// deplace vers le bas
					else if (liste.get(getX()).get(getY() + 1) == null) {
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()).set(getY() + 1, e);
						liste.get(getX()).set(getY(), null);
						y++;
						batterie = batterie - 5;
					}
					else{
						batterie--;
					}
				}

				// boite en haut � droite par rapport au robot
				else if ((boite.getX() > getX()) && (boite.getY() < getY())) {
					// deplace vers la droite
					if (liste.get(getX() + 1).get(getY()) == null) {
						Element e = liste.get(getX()).get(getY());
						liste.get(getX() + 1).set(getY(), e);
						liste.get(getX()).set(getY(), null);
						x++;
						batterie = batterie - 5;
					}
					// deplace vers le haut
					else if (liste.get(getX()).get(getY() - 1) == null) {
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()).set(getY() - 1, e);
						liste.get(getX()).set(getY(), null);
						y--;
						batterie = batterie - 5;
					}
					else{
						batterie--;
					}
				}

				// boite en bas � gauche par rapport au robot
				else if ((boite.getX() < getX()) && (boite.getY() > getY())) {
					// deplace vers la gauche
					if (liste.get(getX() - 1).get(getY()) == null) {
						Element e = liste.get(getX()).get(getY());
						liste.get(getX() - 1).set(getY(), e);
						liste.get(getX()).set(getY(), null);
						x--;
						batterie = batterie - 5;
					}
					// deplace vers le bas
					else if (liste.get(getX()).get(getY() + 1) == null) {
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()).set(getY() + 1, e);
						liste.get(getX()).set(getY(), null);
						y++;
						batterie = batterie - 5;
					}
					else{
						batterie--;
					}
				}

				// boite en haut � gauche par rapport au robot
				else if ((boite.getX() < getX()) && (boite.getY() < getY())) {
					// deplace vers la gauche
					if (liste.get(getX() - 1).get(getY()) == null) {
						Element e = liste.get(getX()).get(getY());
						liste.get(getX() - 1).set(getY(), e);
						liste.get(getX()).set(getY(), null);
						x--;
						batterie = batterie - 5;
					}
					// deplace vers le haut
					else if (liste.get(getX()).get(getY() - 1) == null) {
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()).set(getY() - 1, e);
						liste.get(getX()).set(getY(), null);
						y--;
						batterie = batterie - 5;
					}
					else{
						batterie--;
					}
				}
				// boite en dessous
				else if ((boite.getX() == getX()) && (boite.getY() > getY())) {
					if (liste.get(getX()).get(getY() + 1) == null) {
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()).set(getY() + 1, e);
						liste.get(getX()).set(getY(), null);
						y++;
						batterie = batterie - 5;
					}
					
					else if(boite.getY() == y + 1 && boite.isBoite()){
						synchronized (liste) {
						setBoite(liste.get(getX()).get(getY() + 1));
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()).set(getY() + 1, e);
						liste.get(getX()).set(getY(), null);
						y++;
						batterie = batterie - 5;
						}
					}
					else{
						batterie--;
					}
					
				}

				// boite en dessus
				else if ((boite.getX() == getX()) && (boite.getY() < getY())) {
					if (liste.get(getX()).get(getY() - 1) == null) {
						Element e = liste.get(getX()).get(getY());
						liste.get(getX()).set(getY() - 1, e);
						liste.get(getX()).set(getY(), null);
						y--;
						batterie = batterie - 5;
					}
				
					else if(boite.getY() == y - 1 && boite.isBoite()){
						synchronized (liste) {
							setBoite(liste.get(getX()).get(getY() - 1));
							Element e = liste.get(getX()).get(getY());
							liste.get(getX()).set(getY() - 1, e);
							liste.get(getX()).set(getY(), null);
							y--;
							batterie = batterie - 5;
						}
					}
					else{
						batterie--;
					}
				}

				// boite � gauche
				else if ((boite.getX() < getX()) && (boite.getY() == getY())) {
					// deplace vers la gauche
					if (liste.get(getX() - 1).get(getY()) == null) {
						Element e = liste.get(getX()).get(getY());
						liste.get(getX() - 1).set(getY(), e);
						liste.get(getX()).set(getY(), null);
						x--;
						batterie = batterie - 5;
					}
					else if(boite.getX() == x - 1 && boite.isBoite()){
						synchronized (liste) {
						setBoite(liste.get(getX() - 1).get(getY()));
						Element e = liste.get(getX()).get(getY());
						liste.get(getX() - 1).set(getY(), e);
						liste.get(getX()).set(getY(), null);
						x--;
						batterie = batterie - 5;
						}
					}
					else{
						batterie--;
					}
				}

				// boite � droite
				else if ((boite.getX() > getX()) && (boite.getY() == getY())) {
					// deplace vers la droite
					if (liste.get(getX() + 1).get(getY()) == null) {
						Element e = liste.get(getX()).get(getY());
						liste.get(getX() + 1).set(getY(), e);
						liste.get(getX()).set(getY(), null);
						x++;
						batterie = batterie - 5;
					}
					else if(boite.getX() == x + 1 && boite.isBoite()){
						synchronized (liste) {
						setBoite(liste.get(getX() + 1).get(getY()));
						Element e = liste.get(getX()).get(getY());
						liste.get(getX() + 1).set(getY(), e);
						liste.get(getX()).set(getY(), null);
						x++;
						batterie = batterie - 5;
					}
					}
					else{
						batterie--;
					}
					
				}
				logger.logDeplacement(num, x, y);
			}

			// Permet de savoir si on est � cot� du nid
			public boolean aCoteNid(Element nid) {
				// nid en dessous
				if ((nid.getX() == getX()) && (nid.getY() == y + 1)) {
					return true;
				}

				// nid en dessus
				else if ((nid.getX() == getX()) && (nid.getY() == y - 1)) {
					return true;
				}

				// boite � gauche
				else if ((nid.getX() == x - 1) && (nid.getY() == getY())) {
					// deplace vers la gauche
					return true;
				}

				// boite � droite
				else if ((nid.getX() == x + 1) && (nid.getY() == getY())) {
					// deplace vers la droite
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

			@Override
			public int getId() {
				return num;
			}
			
			public void setBoite(Element boite){
				this.boite = boite;
			}

			@Override
			public Element getBoite() {
				return boite;
			}

			@Override
			public int getMaxBatterie() {
				return maxBatterie;
			}
		};
	}

}
