import impl.GlobalImpl;
import skynet.Global;


public class Main {

	public static void main(String[]args) {
		Global.Component systeme = (new  GlobalImpl()).newComponent();
		systeme.initalisation().initialisation();;
	}
}
