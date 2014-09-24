package server;

import network.Packet;
import network.Type;
import model.creatures.Player;

/**
 * Command object handles player character death events.
 * 
 * @author dje
 * 
 */
public class PlayerDeathCommand extends Command {

	private Player player;

	public PlayerDeathCommand(Player player) {
		this.player = player;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.Command#execute()
	 */
	@Override
	void execute() {
		for (Player p : player.getRoom().getPlayers()) // notify room
			try {
				if (p != player)
					Server.connections.get(p).outputQueue.put(new Packet(
							Type.SERVER_MESSAGE, player.getName() + " dies."));
				else
					Server.connections.get(p).outputQueue.put(new Packet(
							Type.SERVER_MESSAGE, "You have died.")); // notify player
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		player.setRoom(Server.getSpawn()); // respawn
		new LeavePartyCommand(player).execute(); // allows player movement
		try {
			Server.connections.get(player).outputQueue.put(new Packet(
					Type.LOOK, player.getRoom().look()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
