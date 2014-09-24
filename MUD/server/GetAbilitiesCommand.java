package server;

import network.Packet;
import network.Type;
import model.creatures.Player;

/**
 * Command object returns a list of abilities.
 * 
 * @author Daniel Eisenberg
 *
 */
public class GetAbilitiesCommand extends Command {

	private Player player;

	public GetAbilitiesCommand(Player player) {
		this.player = player;
	}

	/* (non-Javadoc)
	 * @see server.Command#execute()
	 */
	void execute() {
		try {
			Server.connections.get(player).outputQueue.put(new Packet(Type.SERVER_MESSAGE, player.getAbilities()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
