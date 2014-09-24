package server;

import network.LookPacket;
import network.Packet;
import network.Type;
import model.creatures.Player;
import model.world.Direction;
import model.world.Room;

/**
 * Command object moves a Player between model.world.Room objects.
 * 
 * @author Daniel Eisenberg
 * 
 */
class MoveCommand extends Command {

	private Player player;
	private Direction direction;

	public MoveCommand(Player player, Direction dir) {
		this.player = player;
		this.direction = dir;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.Command#execute()
	 */
	void execute() {
		Room current = player.getRoom();
		if (direction == null) {
			if (player.hasParty()) {
				for (Player p : player.getParty()) {
					p.setRoom(Server.getSpawn());
					Server.connections.get(p).dungeonKey = null;
					try {
						Server.connections.get(p).outputQueue.put(new LookPacket(
								Type.LOOK, p.getRoom().look(), p.getRoom().getFilePath()));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				player.setRoom(Server.getSpawn());
				try {
					Server.connections.get(player).outputQueue.put(new LookPacket(
							Type.LOOK, player.getRoom().look(), player.getRoom().getFilePath()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		// check player not partied or is the leader of a party
		if (player.isLeader() || !player.hasParty()) {
			if (current.move(player, direction)) { // move player
				try {
					Server.connections.get(player).outputQueue.put(new LookPacket(
							Type.LOOK, player.getRoom().look(), player.getRoom().getFilePath()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (player.hasParty()) // move party
					for (Player partied : player.getParty()) {
						if (partied != player) {
							current.move(partied, direction);
							try {
								Server.connections.get(partied).outputQueue
										.put(new LookPacket(Type.LOOK, player
												.getRoom().look(), player.getRoom().getFilePath()));
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				String partySuffix = (player.hasParty()) ? "'s group" : "";
				// announce departure from room
				for (Player inRoom : current.getPlayers())
					try {
						Server.connections.get(inRoom).outputQueue
								.put(new Packet(Type.SERVER_MESSAGE, player
										.getName()
										+ partySuffix
										+ " moves "
										+ direction.toString().toLowerCase()
										+ "."));
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				// announce entrance in new room
				for (Player inRoom : player.getRoom().getPlayers())
					try {
						if (inRoom != player)
							Server.connections.get(inRoom).outputQueue
									.put(new Packet(Type.SERVER_MESSAGE, player
											.getName()
											+ partySuffix
											+ " approaches."));
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
			} else { // move failed
				try {
					Server.connections.get(player).outputQueue.put(new Packet(
							Type.ERROR, "You can't go that way."));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else { // partied and not leader
			try {
				Server.connections.get(player).outputQueue
						.put(Packet.NOT_LEADER);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
