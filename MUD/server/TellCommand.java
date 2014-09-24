package server;

import model.creatures.Player;
import network.Packet;
import network.Type;

/**
 * TellCommand executes a client's tell command.
 * 
 * @author Daniel Eisenberg
 *
 */
class TellCommand extends Command {

	private Player from;
	private Player to;
	private String msg;

	
	/**
	 * Creates a TellCommand.
	 * 
	 * @param from - the player who sent the tell command.
	 * @param to - the intended recipient.
	 * @param message - the message.
	 */
	public TellCommand(Player from, Player to, String message) {
		this.from = from;
		this.to = to;
		msg = message;
	}

	public void execute() {
		try {
			Server.connections.get(from).outputQueue.put(new Packet(Type.TELL,
					"You tell " + to.getName() + ": " + msg));
			Server.connections.get(to).outputQueue.put(new Packet(Type.TELL, from.getName()
					+ " tells you: " + msg));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}