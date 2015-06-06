import impl.GlobalCooperatifImpl;
import impl.GlobalNominalImpl;
import interfaces.IEchequier;
import interfaces.IIHM;
import skynet.Global;


public class Main {

	public static void main(String[]args) {
		// cas coop
		Global.Component systeme = (new  GlobalCooperatifImpl()).newComponent();
		
		IEchequier echequier =  systeme.initalisation();
		systeme.lancementIHM().run();

		
		echequier.initialisation();
		// cas nominal
//		Global.Component systeme2 = (new  GlobalNominalImpl()).newComponent();
//		systeme2.initalisation().initialisation();;
	}
}
