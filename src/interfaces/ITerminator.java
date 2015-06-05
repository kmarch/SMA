package interfaces;

import java.util.ArrayList;
import java.util.List;

import enumeration.Couleur;

public interface ITerminator extends Element{

	public void run();
	public ITerminator intialisation(ILogger logger, int num, Couleur couleur, int x, int y, List<ArrayList<Element>> listePion, int batterie);
	public ITerminator fabrique(ILogger logger, int num, Couleur couleur, int i, int j, List<ArrayList<Element>> liste, int batterie);
	public void execution();
	public int getId();
	public int getBatterie();
}

