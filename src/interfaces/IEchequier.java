package interfaces;

import impl.TerminatorImpl;

import java.util.ArrayList;

public interface IEchequier {

	public ArrayList<ArrayList<TerminatorImpl>> getMap();
	public void initialisation();

}
