package server;

import network.Packet;
import network.Type;
import model.creatures.Player;

/**
 * Command object handles group invitations.
 * 
 * @author Daniel Eisenberg
 * 
 */
public class InviteCommand extends Command {

	private Player player;
	private Player toInvite;

	public InviteCommand(Player player, Player toInvite) {
		this.player = player;
		this.toInvite = toInvite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.Command#execute()
	 */
	@Override
	void execute() {
		// check party limit
		if (player.hasParty() && player.getParty().size() >= 4) {
			try {
				Connection c = Server.connections.get(player);
				c.outputQueue
						.put(new Packet(Type.ERROR, "Your party is full."));
				c.pendingInvites.clear(); // full party; empty pending invites
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			try {
				Server.connections.get(player).outputQueue.put(new Packet(
						Type.SERVER_MESSAGE, toInvite.getName() + " invited."));
				Server.connections.get(toInvite).outputQueue
						.put(new Packet(
								Type.SERVER_MESSAGE,
								player.getName()
										+ " invited you to join their group; type \"join "
										+ player.getName() + "\" to accept."));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
