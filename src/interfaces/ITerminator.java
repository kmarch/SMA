package interfaces;

import impl.TerminatorImpl;

import java.util.ArrayList;

import enumeration.Couleur;

public interface ITerminator extends Element{

	public void run();
	public ITerminator intialisation(Couleur couleur, int x, int y, ArrayList<ArrayList<TerminatorImpl>> listePion);
}
