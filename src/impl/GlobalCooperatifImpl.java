package impl;

import skynet.Global;
import skynet.IHM;
import skynet.Logger;
import skynet.Systeme;
import skynet.Terminator;

public class GlobalCooperatifImpl extends Global {

	@Override
	protected Systeme make_systeme() {
		return new SystemImpl();
	}

	@Override
	protected Terminator make_terminator() {
		return new TerminatorCooperatifImpl();
	}

	@Override
	protected Logger make_logService() {
		return new LoggerImpl();
	}

	@Override
	protected IHM make_ihm() {
		return new IHMImpl();
	}


}
