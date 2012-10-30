
public class Upgrade {

	public static void main(String[] args) {
		Upgrader upgrader = new Upgrader();
		
		if(args.length==0) {
			upgrader.upgrade();
		}
		else {
			upgrader.upgrade(args[0]);
		}
	}

}
