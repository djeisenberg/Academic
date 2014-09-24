package server;

import java.util.ArrayList;
import java.util.List;

import network.Packet;
import network.Type;

import model.creatures.Player;

/**
 * Command object executes accepted party invitations.
 * 
 * @author Daniel Eisenberg
 * 
 */
public class JoinCommand extends Command {

	private Player leader;
	private Player join;

	public JoinCommand(Player leader, Player toJoin) {
		this.leader = leader;
		this.join = toJoin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.Command#execute()
	 */
	void execute() {
		List<Player> party = leader.getParty();
		if (party == null)
			leader.setParty(new ArrayList<Player>());
		if (party.size() >= 4)
			try {
				Server.connections.get(join).outputQueue.put(new Packet(
						Type.ERROR, "Party is full."));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		else {
			party.add(join); // add player to party
			join.setParty(party); // set player's party
			join.setRoom(leader.getRoom()); // move player to party's room
			Server.connections.get(join).dungeonKey = Server.connections
					.get(leader).dungeonKey; // set player's dungeon key to
													// leader's dungeon key
			for (Player p : party)
				try {
					Server.connections.get(p).outputQueue.put(new Packet(
							Type.SERVER_MESSAGE, join.getName()
									+ " joins the group."));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			try {
				Server.connections.get(join).outputQueue.put(new Packet(
						Type.LOOK, join.getRoom().look()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
