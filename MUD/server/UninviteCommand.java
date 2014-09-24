package server;

import network.Packet;
import network.Type;
import model.creatures.Player;

/**
 * Command object handles party uninvite requests.
 * 
 * @author Daniel Eisenberg
 * 
 */
public class UninviteCommand extends Command {

	private Player player;
	private Player toRemove;

	public UninviteCommand(Player player, Player toRemove) {
		this.player = player;
		this.toRemove = toRemove;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.Command#execute()
	 */
	@Override
	void execute() {
		if (player.getParty().remove(toRemove)) {
			try {
				Server.connections.get(toRemove).outputQueue.put(new Packet(
						Type.SERVER_MESSAGE,
						"You have been removed from the party."));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			for (Player p : player.getParty()) {
				Packet packet = new Packet(Type.SERVER_MESSAGE,
						toRemove.getName()
								+ " has been removed from the party.");
				try {
					Server.connections.get(p).outputQueue.put(packet);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			// if no more are partied, reset party
			if (player.getParty().size() == 1) {
				player.setParty(null);
			}
		} else {
			try {
				Server.connections.get(toRemove).outputQueue.put(new Packet(
						Type.ERROR,
						toRemove.getName() + " is not in your group."));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
