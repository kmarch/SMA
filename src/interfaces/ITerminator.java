package interfaces;

import java.util.ArrayList;
import java.util.List;

import enumeration.Couleur;

public interface ITerminator extends Element{

	public void run();
	public ITerminator intialisation(Couleur couleur, int x, int y, List<ArrayList<Element>> listePion);
	public ITerminator fabrique(Couleur couleur, int i, int j, List<ArrayList<Element>> liste);
	public void execution();
}

