package interfaces;

import enumeration.Couleur;

public interface Element {

	public boolean isBoite();
	public boolean isNid();
	public int getX();
	public int getY();
	public Couleur getCouleur();
}
