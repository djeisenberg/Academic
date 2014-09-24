package server;

import java.util.List;

import network.Packet;
import network.SafePlayerPacket;
import network.Type;

import model.creatures.Player;
import model.items.Item;

/**
 * Command object transfers an item between player's inventories.
 * 
 * @author Daniel Eisenberg
 * 
 */
public class GiveCommand extends Command {

	private Player player;
	private Player to;
	private String item;

	public GiveCommand(Player giver, Player recipient, String toGive) {
		player = giver;
		to = recipient;
		item = toGive;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.Command#execute()
	 */
	@Override
	void execute() {
		List<Item> list = player.itemList();
		for (Item i : list)
			if (i.getName().equalsIgnoreCase(item)) {
				if (player.give(i, to)) {
					try {
						Server.connections.get(player).outputQueue.put(new Packet(
								Type.SERVER_MESSAGE, "You give " + to.getName()
										+ " " + i.getName() + "."));
						Server.connections.get(to).outputQueue.put(new Packet(
								Type.SERVER_MESSAGE, player.getName()
										+ " gives you " + i.getName() + "."));
						Server.connections.get(player).outputQueue.put(new SafePlayerPacket(player));
						Server.connections.get(to).outputQueue.put(new SafePlayerPacket(to));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return;
				} else {
					try {
						Server.connections.get(player).outputQueue.put(new Packet(
								Type.ERROR, "You can't give " + to.getName()
										+ " that item."));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return;
				}
			}
	}
}
