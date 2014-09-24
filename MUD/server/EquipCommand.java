package server;

import network.Packet;
import network.SafePlayerPacket;
import network.Type;
import model.creatures.Player;
import model.items.Equipable;
import model.items.Item;

/**
 * Command object handles player equip commands.
 * 
 * @author Daniel Eisenberg
 *
 */
public class EquipCommand extends Command {
	
	private Player player;
	private String iName;

	public EquipCommand(Player player, String msg) {
		this.player = player;
		this.iName = msg;
	}

	/* (non-Javadoc)
	 * @see server.Command#execute()
	 */
	void execute() {
		for (Item i : player.itemList())
			if (i instanceof Equipable && i.getName().equalsIgnoreCase(iName)) {
				player.equip((Equipable) i);
				try {
					Server.connections.get(player).outputQueue.put(new Packet(Type.SERVER_MESSAGE, "You equip " + i.getName() + "."));
					Server.connections.get(player).outputQueue.put(new SafePlayerPacket(player));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		try {
			Server.connections.get(player).outputQueue.put(new Packet(Type.ERROR, "Could not equip item: " + iName + "."));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
