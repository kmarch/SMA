package impl;

import interfaces.Element;
import interfaces.ITerminator;

import java.util.ArrayList;
import java.util.List;

import skynet.Global;
import skynet.Terminator;
import enumeration.Couleur;


public class TerminatorImpl extends Terminator {

	@Override
	protected ITerminator make_manage() {
		return new ITerminator() {

			private int x;
			private int y;
			
			@Override
			public void run() {
				new Parallele(this).start();
			}

			public void execution() {
				System.out.println(x + " " + y);
			}
			
			@Override
			public ITerminator intialisation(Couleur couleur, int x, int y,
					List<ArrayList<Element>> listePion) {
				this.x = x;
				this.y = y;
				return this;
			}

			@Override
			public boolean isBoite() {
				return false;
			}

			@Override
			public boolean isNid() {
				return false;
			}

			@Override
			public Couleur getCouleur() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ITerminator fabrique(Couleur couleur, int i, int j, List<ArrayList<Element>> liste){
				
				Terminator.Component systeme = (new  TerminatorImpl()).newComponent();
				return systeme.manage().intialisation(couleur, i, j, liste);

			}

			
		};
	}

	


}
