package impl;
import interfaces.IEchequier;
import interfaces.IIHM;
import interfaces.IIHM;


public class ParalleleIHM extends Thread{

	private IIHM ihm;
	public ParalleleIHM(IIHM ihm) {
		super();
		this.ihm = ihm;
	}

	public void  run() {
		ihm.execution();
	}
}
