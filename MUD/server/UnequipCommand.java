package server;

import model.creatures.Player;
import model.items.Equipable;
import model.items.Item;
import network.Packet;
import network.SafePlayerPacket;
import network.Type;

/**
 * Command object handles player unequip commands.
 * 
 * @author Daniel Eisenberg
 *
 */
public class UnequipCommand extends Command {

	private Player player;
	private String iName;

	public UnequipCommand(Player player, String msg) {
		this.player = player;
		this.iName = msg;
	}

	/* (non-Javadoc)
	 * @see server.Command#execute()
	 */
	void execute() {
		for (Item i : player.itemList())
			if (i instanceof Equipable && i.getName().equalsIgnoreCase(iName)) {
				player.unequip((Equipable) i);
				try {
					Server.connections.get(player).outputQueue.put(new Packet(Type.SERVER_MESSAGE, "You unequip " + i.getName() + "."));
					Server.connections.get(player).outputQueue.put(new SafePlayerPacket(player));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		try {
			Server.connections.get(player).outputQueue.put(new Packet(Type.ERROR, "Could not unequip item: " + iName + "."));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
