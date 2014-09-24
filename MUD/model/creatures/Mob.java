package model.creatures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.items.Item;
import model.world.Direction;
import model.world.Room;

/**
 * Abstract class for the Mob objects in the game
 * 
 * @author Steven Chandler
 * 
 */
public abstract class Mob extends Creature {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int mID;
	protected LinkedList<Item> treasure = new LinkedList<Item>();
	protected ArrayList<Room> movable;

	public abstract void act();

	public void setRoom(Room room) {
		if (room == null) {
			location = null;
			return;
		}
		if (location != null)
			location.removeMob(this);
		location = room;
		room.addCreatures(this);
	}

	public void move() {
		if (movable != null) {
			Direction[] d = Direction.values();

			Random x = new Random();

			int y = x.nextInt(d.length);

			if (location != null)
				this.location.moveMob(this, d[y], movable);
		}
	}
}
