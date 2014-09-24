package server;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import network.Packet;
import network.SafeCreaturePacket;
import network.SafePlayerPacket;
import network.Type;
import model.creatures.Creature;
import model.creatures.Mob;
import model.creatures.Monster;
import model.creatures.Player;
import model.items.Item;
import model.mechanics.Mechanics;

/**
 * Command object handles player attack commands.
 * 
 * @author dje
 * 
 */
public class AttackCommand extends Command {

	private Player player;
	private String target;

	public AttackCommand(Player player, String msg) {
		this.player = player;
		this.target = msg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.Command#execute()
	 */
	@Override
	void execute() {
		for (Mob m : player.getRoom().listCreatures())
			if (m.getName().equalsIgnoreCase(target)) {
				int result = player.attack(m);
				m.damage(result);
				try {
					BlockingQueue<Packet> queue = Server.connections
							.get(player).outputQueue;
					queue.put(new Packet(Type.SERVER_MESSAGE,
							"Your attack does " + result + " damage to "
									+ m.getName() + "."));
					queue.put(new SafeCreaturePacket(m));
					queue.put(new SafePlayerPacket(player));
					if (m.getCurrentHP() <= 0) {
						queue.put(new Packet(Type.SERVER_MESSAGE,
								"You have slain " + m.getName() + "!"));
						if (player.hasParty()) {
							for (Player p : player.getParty()) {
								int prevXP = p.getXP();
								if (p.setXP(m))
									Server.connections.get(p).outputQueue
											.put(new Packet(
													Type.SERVER_MESSAGE,
													"Congratulations, you have reached level "
															+ p.getLevel()));
								else {
									int curXP = p.getXP();
									Server.connections.get(p).outputQueue
											.put(new Packet(
													Type.SERVER_MESSAGE,
													"You gain "
															+ (curXP - prevXP)
															+ "experience."));
								}
							}
						} else {
							int prevXP = player.getXP();
							if (player.setXP(m))
								queue.put(new Packet(Type.SERVER_MESSAGE,
										"Congratulations, you have reached level "
												+ player.getLevel()));
							else {
								int curXP = player.getXP();
								queue.put(new Packet(Type.SERVER_MESSAGE,
										"You gain " + (curXP - prevXP)
												+ "experience."));
							}
						}
						List<Item> treasure = ((Monster) m).Treasure();
						if (treasure != null) {
							String items = "";
							for (Item i : treasure) {
								if (i.getName() != null) {
									items += i.getName() + ", ";
									player.itemList().add(i);
								}
							}
							if (items.length() > 0) {
								items = items.substring(0, items.length() - 2);
								queue.put(new Packet(Type.SERVER_MESSAGE,
										"You loot " + treasure + "."));
							}
						}
						player.getRoom().removeMob(m);
						m.setRoom(null);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		try {
			Server.connections.get(player).outputQueue.put(new Packet(
					Type.ERROR, "Target not found."));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
