package server;

import java.util.LinkedList;

import model.creatures.Mob;
import model.creatures.Monster;
import model.creatures.Player;

/**
 * MobCommand defines a Command object for executing model.Mob actions.
 * 
 * @author Daniel Eisenberg
 * 
 */
public class MobCommand extends Command {

	private Mob mob;
	private boolean move;

	/**
	 * Creates a new MobCommand for the specified mob.
	 * @param move 
	 */
	public MobCommand(Mob mob, boolean move) {
		this.mob = mob;
		this.move = move;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.Command#execute()
	 */
	void execute() {
		mob.act();
		mob.setLastActed(System.currentTimeMillis());
		if (move)
			mob.move();
	}

}
