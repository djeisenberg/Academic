package server;

import java.util.List;

import network.Packet;
import network.SafePlayerPacket;
import network.Type;

import model.creatures.Player;
import model.items.Item;

/**
 * Command object retrieves an Item from a Room.
 * 
 * @author Daniel Eisenberg
 * 
 */
public class GetCommand extends Command {

	private Player player;
	private String item;

	public GetCommand(Player player, String item) {
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
		// find item
		List<Item> list = player.getRoom().listItems();
		for (Item i : list) {
			if (i.getName().equalsIgnoreCase(item)) {
				player.take(i); // get item
				try {
					Server.connections.get(player).outputQueue.put(new Packet(
							Type.SERVER_MESSAGE, "You receive " + i.getName()
									+ "."));
					Server.connections.get(player).outputQueue.put(new SafePlayerPacket(player));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		// otherwise
		try {
			Server.connections.get(player).outputQueue.put(new Packet(
					Type.ERROR, "Item not found."));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
