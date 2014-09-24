package server;

import model.creatures.Player;
import network.Packet;
import network.SafePlayerPacket;
import network.Type;

/**
 * Command object returns player's inventory.
 * 
 * @author Daniel Eisenberg
 *
 */
public class InventoryCommand extends Command {

	private Player player;

	public InventoryCommand(Player player) {
		this.player = player;
	}

	/* (non-Javadoc)
	 * @see server.Command#execute()
	 */
	@Override
	void execute() {
		try {
			Server.connections.get(player).outputQueue.put(new Packet(Type.INVENTORY, player.inventory()));
			Server.connections.get(player).outputQueue.put(new SafePlayerPacket(player));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
