package server;

import java.util.List;

import server.Server.MobActionThread;

import network.Packet;
import network.Type;

import model.creatures.Player;

/**
 * Command object declares a new leader in the event of a party leader
 * disconnecting.
 * 
 * @author Daniel Eisenberg
 * 
 */
public class ChangeLeader extends Command {

	private Player player;
	private Player newLeader;

	public ChangeLeader(Player leader) {
		this(leader, null);
	}

	public ChangeLeader(Player leader, Player toPromote) {
		player = leader;
		newLeader = toPromote;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.Command#execute()
	 */
	void execute() {
		List<Player> party = player.getParty();
		if (party != null) {
			if (newLeader == null) { // leader disconnected, choose new leader,
										// remove old leader
				for (Player p : party)
					// attempt to choose new leader
					if (p != player && newLeader == null)
						newLeader = p;
					else if (p == player)
						party.remove(p); // remove old leader
			}
			if (newLeader != null) { // if new leader found or declared, fix
										// party
				for (Player p : party) {
					p.setLeader(newLeader);
					try {
						Server.connections.get(p).outputQueue.put(new Packet(
								Type.SERVER_MESSAGE, newLeader.getName()
										+ " is now the group leader."));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else { // dissolve party
				for (Player p : party) {
					p.setLeader(p);
					p.setParty(null);
					MobActionThread temp = Server.connections.get(p).dungeonKey;
					if (temp != null) {
						p.setRoom(Server.getSpawn());
						Server.connections.get(p).dungeonKey = null;
						Server.dungeonList.remove(temp);
					}
				}
			}
		}
	}

}
