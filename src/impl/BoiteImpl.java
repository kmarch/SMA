package impl;

import enumeration.Couleur;
import interfaces.Element;

public class BoiteImpl implements Element {

	private Couleur couleur;

	public BoiteImpl(Couleur couleur) {
		this.couleur = couleur;
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

}
