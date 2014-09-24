package network;


/**
 * This class is a packet for sending updates to the client
 * about the status of their player character.
 * 
 * @author james_carpenter
 */

public class SafePlayerPacket extends Packet{

	private static final long serialVersionUID = 1L;
	private SafePlayer sp;
	
	public SafePlayerPacket(model.creatures.Player player) {
		super(Type.META_DATA, "");
		sp = (SafePlayer) player;
	}
	
	public SafePlayer getSafePlayer() {
		return sp;
	}

}
