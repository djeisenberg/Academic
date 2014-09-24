package server;

import network.LookPacket;
import network.Packet;
import network.Type;
import model.creatures.Player;

public class LookCommand extends Command {

	private Player player;

	public LookCommand(Player player) {
		this.player = player;
	}

	@Override
	void execute() {
		try {
			Server.connections.get(player).outputQueue.put(new LookPacket(Type.LOOK,
					player.getRoom().look(), player.getRoom().getFilePath()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
