package server;

import network.Packet;
import network.SafePlayerPacket;
import network.Type;
import model.creatures.Player;
import model.items.Item;
import model.items.Usable;

/**
 * Command object uses a Usable Item.
 * 
 * @author Daniel Eisenberg
 *
 */
public class UseCommand extends Command {

	
	
	private Player player;
	private String iName;

	public UseCommand(Player player, String msg) {
		this.player = player;
		this.iName = msg;
	}

	/* (non-Javadoc)
	 * @see server.Command#execute()
	 */
	void execute() {
		for (Item i : player.itemList())
			if (i instanceof Usable && i.getName().equalsIgnoreCase(iName)) {
				player.useItem((Usable) i);
				try {
					Server.connections.get(player).outputQueue.put(new Packet(Type.SERVER_MESSAGE, "You use" + i.getName() + "."));
					Server.connections.get(player).outputQueue.put(new SafePlayerPacket(player));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		try {
			Server.connections.get(player).outputQueue.put(new Packet(Type.ERROR, "Could not use item: " + iName + "."));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
