import impl.GlobalCooperatifImpl;
import impl.GlobalNominalImpl;
import interfaces.IEchequier;
import interfaces.IIHM;
import skynet.Global;


public class Main {

	public static void main(String[]args) {
		// cas nominal
		Global.Component systeme = (new  GlobalCooperatifImpl()).newComponent();
		
		IEchequier echequier =  systeme.initalisation();
		systeme.lancementIHM().run();

		
		echequier.initialisation();
		// cas coop
//		Global.Component systeme2 = (new  GlobalCooperatifImpl()).newComponent();
//		systeme2.initalisation().initialisation();;
	}
}
