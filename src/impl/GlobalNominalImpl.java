package impl;

import skynet.Global;
import skynet.IHM;
import skynet.Logger;
import skynet.Systeme;
import skynet.Terminator;

public class GlobalNominalImpl extends Global {

	@Override
	protected Systeme make_systeme() {
		return new SystemImpl();
	}

	@Override
	protected Terminator make_terminator() {
		return new TerminatorNominalImpl();
	}

	@Override
	protected Logger make_logService() {
		return new LoggerImpl();
	}

//	@Override
//	protected IHM make_ihm() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
