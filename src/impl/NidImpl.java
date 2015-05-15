package impl;

import enumeration.Couleur;
import interfaces.Element;

public class NidImpl implements Element {

	private Couleur couleur;
	int x;
	int y;
	
	public NidImpl(Couleur couleur,int x,int y) {
		this.couleur  = couleur;
		this.x = x;
		this.y = y;
	}
	@Override
	public boolean isBoite() {
		return false;
	}

	@Override
	public boolean isNid() {
		return true;
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
		// TODO Auto-generated method stub
		return y;
	}


}
