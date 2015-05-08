package impl;
import interfaces.ITerminator;


public class Parallele extends Thread{

	private ITerminator terminator;
	public Parallele(ITerminator terminator) {
		super();
		this.terminator = terminator;
	}
	
	public void  run() {
		terminator.execution();
	}
}
