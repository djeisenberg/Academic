package network;


/**
 * This class is a packet for sending NPC information to the client.
 * 
 * @author james_carpenter
 */

public class SafeCreaturePacket extends Packet {

	private static final long serialVersionUID = 1L;
	private SafeCreature sc;

	public SafeCreaturePacket(model.creatures.Creature creature) {
		super(Type.META_DATA, "");
		sc = (SafeCreature) creature;
	}

	public SafeCreature getSafeCreature() {
		return sc;
	}

}
