/**
 * 
 */
package server;

import network.SafeCreaturePacket;
import network.SafePlayerPacket;
import model.creatures.Creature;
import model.creatures.Player;

/**
 * Command object sends SafePlayerPacket or SafeCreaturePacket to client.
 * 
 * @author Daniel Eisenberg
 * 
 */
public class CreatureUpdate extends Command {

	private Player player;
	private Creature creature;

	public CreatureUpdate(Player player, Creature creature) {
		this.player = player;
		this.creature = creature;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.Command#execute()
	 */
	void execute() {
		if (creature instanceof Player)
			try {
				Server.connections.get(player).outputQueue
						.put(new SafePlayerPacket((Player) creature));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		else
			try {
				Server.connections.get(player).outputQueue
						.put(new SafeCreaturePacket(creature));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

}
