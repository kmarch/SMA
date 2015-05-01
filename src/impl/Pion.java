package impl;

import enumeration.Couleur;
import enumeration.Type;

public abstract class Pion {

	
	private int cordX;
	private int cordY;
	
	private Couleur couleur;
	private Type type;
	
	public Couleur getCouleur() {
		return couleur;
	}
	
	public Type getType() {
		return type;
	}
}
