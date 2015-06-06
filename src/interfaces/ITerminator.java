package interfaces;

import java.util.ArrayList;
import java.util.List;

import enumeration.Couleur;

public interface ITerminator extends Element{

	public void run();
	public ITerminator intialisation(ILogger logger, int num, Couleur couleur, int x, int y, List<ArrayList<Element>> listePion, int batterie);
	/**
	 * Fabrication d'un composant ITerminator on va lui fournir tout les éléments pour qu'il puisse fonctionner de façon autonome
	 * @param logger, logger pour faire des logs
	 * @param num, identifiant du nouveau Terminator
	 * @param couleur, couleur du nouveau Terminator
	 * @param i, abscisse de départ
	 * @param j, ordonnée de départ
	 * @param liste, map 
	 * @param batterie
	 * @return
	 */
	public ITerminator fabrique(ILogger logger, int num, Couleur couleur, int i, int j, List<ArrayList<Element>> liste, int batterie);
	/**
	 * Démarrage du Terminator qui va commecner la collectes
	 */
	public void execution();
	public int getId();
	public int getBatterie();
	public int getMaxBatterie();
	public Element getBoite();
}

