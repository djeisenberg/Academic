package server;

import network.Packet;
import network.SafePlayerPacket;
import network.Type;
import model.creatures.Player;
import model.items.Item;

/**
 * Command object drops an item a Player is carrying in the Room they are in.
 * 
 * @author Daniel Eisenberg
 * 
 */
public class DropCommand extends Command {

	private Player player;
	private String item;

	public DropCommand(Player player, String item) {
		this.player = player;
		this.item = item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.Command#execute()
	 */
	@Override
	void execute() {
		for (Item i : player.itemList()) {
			if (i.getName().equalsIgnoreCase(item)) {
				player.drop(i);
				try {
					Server.connections.get(player).outputQueue.put(new Packet(
							Type.SERVER_MESSAGE, "You drop " + i.getName() + "."));
					Server.connections.get(player).outputQueue.put(new SafePlayerPacket(player));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		try {
			Server.connections.get(player).outputQueue.put(new Packet(Type.ERROR,
					"You don't have that item."));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
