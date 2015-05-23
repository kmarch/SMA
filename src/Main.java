import impl.GlobalCooperatifImpl;
import skynet.Global;


public class Main {

	public static void main(String[]args) {
		// cas nominal
//		Global.Component systeme = (new  GlobalNominalImpl()).newComponent();
//		systeme.initalisation().initialisation();;
		// cas coop
		Global.Component systeme2 = (new  GlobalCooperatifImpl()).newComponent();
		systeme2.initalisation().initialisation();;
	}
}
