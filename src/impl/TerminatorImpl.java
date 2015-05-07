package impl;

import java.util.ArrayList;

import enumeration.Couleur;
import interfaces.ITerminator;
import skynet.Terminator;


public class TerminatorImpl extends Terminator {

	@Override
	protected ITerminator make_run() {
		return new ITerminator() {

			
			@Override
			public void run() {
				new Thread() {
					
				}.start();
			}

			@Override
			public ITerminator intialisation(Couleur couleur, int x, int y,
					ArrayList<ArrayList<TerminatorImpl>> listePion) {
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

			
		};
	}

	


}
