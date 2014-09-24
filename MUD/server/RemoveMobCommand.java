/**
 * 
 */
package server;

import model.creatures.Mob;
import model.world.Zone;

/**
 * Cleanup Command object removes a slain mob from a dungeon.
 * 
 * @author Daniel Eisenberg
 *
 */
public class RemoveMobCommand extends Command {

	private Mob mob;
	private Zone zone;

	public RemoveMobCommand(Mob m, Zone zone) {
		this.mob = m;
		this.zone = zone;
	}

	/* (non-Javadoc)
	 * @see server.Command#execute()
	 */
	void execute() {
		zone.listMobs().remove(mob);
	}

}
