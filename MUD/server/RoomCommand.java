package server;

import network.Packet;
import network.Type;
import model.creatures.Player;
import model.world.Room;

/**
 * RoomCommand sends a message to every Player in a room.
 * 
 * @author Daniel Eisenberg
 *
 */
public class RoomCommand extends Command {

	private Room room;
	private String message;
	private Type type;

	/**
	 * Creates a new RoomCommand object.
	 * 
	 * @param room - the room to send a message to.
	 * @param msg - the preformatted message to send.
	 * @param t - the message's Type
	 */
	public RoomCommand(Room room, String msg, Type t) {
		this.room = room;
		this.message = msg;
		this.type = t;
	}

	/* (non-Javadoc)
	 * @see server.Command#execute()
	 */
	void execute() {
		for (Player p : room.getPlayers())
			try {
				Server.connections.get(p).outputQueue.put(new Packet(type, message));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

}
