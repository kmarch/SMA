package impl;

import enumeration.Couleur;
import interfaces.Element;

public class BoiteImpl implements Element {

	private Couleur couleur;
	private int x;
	private int y;

	public BoiteImpl(Couleur couleur,int x,int y) {
		this.couleur = couleur;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean isBoite() {
		return true;
	}

	@Override
	public boolean isNid() {
		return false;
	}

	@Override
	public Couleur getCouleur() {
		return couleur;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}


}
