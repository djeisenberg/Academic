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
import model.mechanics.Abilities;

/**
 * Command object handles player cast commands.
 * 
 * @author Daniel Eisenberg
 * 
 */
public class CastCommand extends Command {

	private Player player;
	private String abil;
	private String target;

	public CastCommand(Player player, String abilityKey, String target) {
		this.player = player;
		this.abil = abilityKey;
		this.target = target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.Command#execute()
	 */
	void execute() {
		if (player.knowsAbility(abil)) {
			// steal handled separately
			if (abil.equalsIgnoreCase("Rog001")) {
				for (Mob m : player.getRoom().listCreatures())
					if (m.getName().equalsIgnoreCase(target)) {
						Item stolen = Abilities.Steal(player, m);
						if (stolen != null && stolen.getName() != null) {
							player.itemList().add(stolen);
							try {
								Server.connections.get(player).outputQueue
										.put(new Packet(Type.SERVER_MESSAGE,
												"You steal " + stolen.getName()
														+ " from "
														+ m.getName() + "."));
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} else {
							try {
								Server.connections.get(player).outputQueue
										.put(new Packet(Type.SERVER_MESSAGE,
												"Failed to steal from "
														+ m.getName() + "."));
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
			} else { // attack abilities

				for (Mob m : player.getRoom().listCreatures())
					if (m.getName().equalsIgnoreCase(target)) {
						int result = player.useAbility(abil, m);
						m.damage(result);
						String message = "You cast "
								+ player.getAbilityName(abil) + " at "
								+ m.getName() + ".\n" + m.getName() + " takes "
								+ result + " damage.";
						BlockingQueue<Packet> queue = Server.connections
								.get(player).outputQueue;
						try {
							queue.put(new Packet(Type.SERVER_MESSAGE, message));
							queue.put(new SafePlayerPacket(player));
							queue.put(new SafeCreaturePacket(m));

							if (m.getCurrentHP() <= 0) { // creature defeated
								queue.put(new Packet(Type.SERVER_MESSAGE,
										"You have slain " + m.getName() + "!"));
								if (player.hasParty()) {
									for (Player p : player.getParty()) {
										int prevXP = p.getXP();
										if (p.setXP(m)) // check for level
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
										queue.put(new Packet(
												Type.SERVER_MESSAGE,
												"Congratulations, you have reached level "
														+ player.getLevel()));
									else {
										int curXP = player.getXP();
										queue.put(new Packet(
												Type.SERVER_MESSAGE,
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
										items = items.substring(0,
												items.length() - 2);
										queue.put(new Packet(
												Type.SERVER_MESSAGE,
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
			}
		} else { // player does not know ability
			try {
				Server.connections.get(player).outputQueue.put(new Packet(
						Type.ERROR, "You don't know that ability."));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
