package server;

import server.Server.MobActionThread;
import network.Packet;
import network.Type;
import model.creatures.Player;
import model.world.FinalDungeon;
import model.world.GoblinDungeon;
import model.world.HallOfAbominations;
import model.world.HallOfVillany;
import model.world.OgreCave;
import model.world.Zone;

/**
 * Command object creates a new dungeon for a player.
 * 
 * @author dje
 * 
 */
public class CreateDungeonCommand extends Command {

	private Player player;
	private MobActionThread thread;
	private String name;

	public CreateDungeonCommand(Player player, MobActionThread mat, String msg) {
		this.player = player;
		this.thread = mat;
		this.name = msg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.Command#execute()
	 */
	@Override
	void execute() {
		Zone zone;
		int size = 1;
		if (player.hasParty())
			size = player.getParty().size();
		if (name.toLowerCase().startsWith("goblin"))
			zone = new GoblinDungeon(size);
		else if (name.toLowerCase().startsWith("hall of v"))
			zone = new HallOfVillany(size);
		else if (name.toLowerCase().startsWith("hall of a"))
			zone = new HallOfAbominations(size);
		else if (name.toLowerCase().startsWith("ogre"))
			zone = new OgreCave(size);
		else if (name.toLowerCase().startsWith("final"))
			zone = new FinalDungeon(size);
		else {
			try {
				Server.connections.get(player).outputQueue.put(new Packet(
						Type.ERROR, "Could not find dungeon" + name + "."));
				return;
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
		Server.dungeonList.put((MobActionThread) thread, zone);
		if (player.hasParty()) {
			for (Player p : player.getParty())
				try {
					p.setRoom(zone.getEntrance());
					Server.connections.get(p).dungeonKey = thread;
					Server.connections.get(p).outputQueue.put(new Packet(
							Type.LOOK, zone.getEntrance().look()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		} else
			try {
				player.setRoom(zone.getEntrance());
				Server.connections.get(player).outputQueue.put(new Packet(
						Type.LOOK, zone.getEntrance().look()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		thread.start();
	}
}
