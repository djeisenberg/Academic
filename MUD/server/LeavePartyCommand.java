package server;

import java.util.List;

import network.Packet;
import network.Type;
import model.creatures.Player;

/**
 * Command executes a leave party command.
 * 
 * @author Daniel Eisenberg
 *
 */
public class LeavePartyCommand extends Command {

	private Player player;

	public LeavePartyCommand(Player player) {
		this.player = player;
	}

	/* (non-Javadoc)
	 * @see server.Command#execute()
	 */
	void execute() {
		List<Player> party = player.getParty();
		party.remove(player);
		player.setParty(null);
		player.setLeader(null);
		player.setRoom(Server.getSpawn());
		Server.connections.get(player).dungeonKey = null;
		try {
			Server.connections.get(player).outputQueue.put(new Packet(Type.SERVER_MESSAGE, "You leave the party."));
			for (Player p : party) {
				Server.connections.get(p).outputQueue.put(new Packet(Type.SERVER_MESSAGE, player.getName() + " leaves the party."));
				if (party.size() == 1) {
					p.setParty(null);
					p.setLeader(null);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
