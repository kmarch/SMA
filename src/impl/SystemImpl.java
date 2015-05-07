package impl;

import interfaces.IEchequier;

import java.util.ArrayList;

import skynet.Systeme;

public class SystemImpl extends Systeme {



	@Override
	protected IEchequier make_gestMap() {
		return new IEchequier() {

			private ArrayList<ArrayList<TerminatorImpl>> liste;

			@Override
			public ArrayList<ArrayList<TerminatorImpl>> getMap() {
				return liste;
			}

			@Override
			public void initialisation() {
				liste = new ArrayList<ArrayList<TerminatorImpl>>();
			}

		};
	}


}
