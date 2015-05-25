package impl;

import enumeration.Couleur;
import interfaces.Element;

public class BoiteImpl implements Element {

	private Couleur couleur;
	private int x;
	private int y;
	private boolean reservee = false;

	public BoiteImpl(Couleur couleur,int x,int y,boolean reservee) {
		this.couleur = couleur;
		this.x = x;
		this.y = y;
		this.reservee = reservee;
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

	public boolean isReserved() {
		return reservee;
	}
	
	public void reserver() {
		reservee = true;
	}

}
