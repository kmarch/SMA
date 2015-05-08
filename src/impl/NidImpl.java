package impl;

import enumeration.Couleur;
import interfaces.Element;

public class NidImpl implements Element {

	private Couleur couleur;
	
	public NidImpl(Couleur couleur) {
		this.couleur  = couleur;
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

}
