package impl;

import skynet.Global;
import skynet.IHM;
import skynet.Systeme;
import skynet.Terminator;

public class GlobalImpl extends Global {

	@Override
	protected Systeme make_systeme() {
		return new SystemImpl();
	}

	@Override
	protected Terminator make_terminator() {
		return new TerminatorImpl();
	}

//	@Override
//	protected IHM make_ihm() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}