package server;

import model.creatures.Player;
import network.Packet;

/**
 * Broadcast's execute() method places its Packet in each connection's
 * outputQueue.
 * 
 */
class Broadcast extends Command {

	private Packet packet;

	/**
	 * Broadcast command constructor takes a Packet object to broadcast to
	 * all connections.
	 * 
	 * @param bcp
	 *            - the Packet to be broadcast.
	 */
	public Broadcast(Packet bcp) {
		packet = bcp;
	}

	public void execute() {
		for (Player p : Server.connections.keySet())
			try {
				Server.connections.get(p).outputQueue.put(packet);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}