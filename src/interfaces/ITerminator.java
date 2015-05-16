package interfaces;

import java.util.ArrayList;
import java.util.List;

import skynet.Systeme;
import enumeration.Couleur;

public interface ITerminator extends Element{

	public void run();
	public ITerminator intialisation(ILogger logger, int num, Couleur couleur, int x, int y, List<ArrayList<Element>> listePion, int batterie);
	public ITerminator fabrique(ILogger logger, int num, Couleur couleur, int i, int j, List<ArrayList<Element>> liste, int batterie);
	public void execution();
	public ArrayList<Element> listeBoite(List<ArrayList<Element>> liste);
	public Element boitePlusProche(ArrayList<Element> liste);
	public int distanceBoitePlusProche(Element boite);
	public ArrayList<Element> listeNids(List<ArrayList<Element>> liste);
	public int getBatterie();
	public void setBatterie(int batterie);
}

