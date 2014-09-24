package server;

import network.Packet;
import network.Type;
import model.creatures.Player;

/**
 * Command object sends a message to each member of a party.
 * 
 * @author Daniel Eisenberg
 * 
 */
public class PartyCommand extends Command {

	private Player player;
	private String message;

	/**
	 * Creates a party chat Command object with the specified message
	 * originating from the specified player.
	 * 
	 * @param player
	 *            - the player sending the message.
	 * @param message
	 *            - the party chat message.
	 */
	public PartyCommand(Player player, String message) {
		this.player = player;
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.Command#execute()
	 */
	void execute() {
		for (Player p : player.getParty())
			try {
				Server.connections.get(p).outputQueue.put(new Packet(Type.PARTY,
						player.getName() + ": " + message));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

}
