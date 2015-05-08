package impl;

import interfaces.Element;
import interfaces.IEchequier;
import interfaces.ITerminator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import skynet.Systeme;
import enumeration.Couleur;

public class SystemImpl extends Systeme {

	@Override
	protected IEchequier make_gestMap() {
		return new IEchequier() {

			private List<ArrayList<Element>> liste;
			private final String PROPERTIES = "skynet.properties";

			@Override
			public List<ArrayList<Element>> getMap() {
				return liste;
			}

			@Override
			public void initialisation() {
				Properties properties = new Properties();
				try {
					FileInputStream in = new FileInputStream(PROPERTIES);
					properties.load(in);
					in.close();
				} catch (IOException e) {
					System.out.println("Unable to load config file.");
				}
				int taille = getInt(properties.getProperty("taille"));
				initRobots(properties, taille);
				
				initNids(properties);
			}

			private void initNids(Properties properties) {
				Element nid;
				String listeRobots = properties.getProperty("nids");
				String[] nids = listeRobots.split("[,]");
				System.out.println("ici " + nids.length);
				for (int i = 0; i < nids.length; i += 3) {
					nid = new NidImpl(getCouleur(nids[i+2]));
					liste.get(getInt(nids[i])).set(getInt(nids[i + 1]),
							nid);
				}
			}

			private void initRobots(Properties properties, int taille) {
				ITerminator t800;
				liste = new ArrayList<ArrayList<Element>>();
				for (int i = 0; i < taille; i++) {
					liste.add(new ArrayList<Element>());
					for(int j = 0; j < taille; j++) {
						liste.get(i).add(null);
					}
				}
				liste = Collections.synchronizedList(liste);
				String listeRobots = properties.getProperty("robots");
				String[] robots = listeRobots.split("[,]");
				for (int i = 0; i < robots.length; i += 3) {
					t800 = requires().manage().fabrique(
							getCouleur(robots[i + 2]), getInt(robots[i]),
							getInt(robots[i + 1]), liste);
					liste.get(getInt(robots[i])).set(getInt(robots[i + 1]),
							t800);
					t800.run();
				}
			}

			public int getInt(String chaine) {
				return Integer.parseInt(chaine);
			}

			public Couleur getCouleur(String chaine) {
				Couleur couleur = null;
				switch (chaine) {
				case "rouge":
					couleur = Couleur.ROUGE;
					break;
				case "vert":
					couleur = Couleur.VERT;
					break;
				case "bleu":
					couleur = Couleur.BLEU;
				}
				return couleur;
			}

		};

	}

}
