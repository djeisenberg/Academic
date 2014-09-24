package model.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import model.creatures.Mob;
import model.creatures.Player;
import model.items.Item;
import model.items.SkeletonKey;

/**
 * 
 * 
 * @author Steven Chandler
 */

public class Room implements Serializable {

	private static final long serialVersionUID = 1L;
	private int rID;
	private String description;
	private String filepath;
	private Room north;
	private Room south;
	private Room east;
	private Room west;
	private Room up;
	private Room down;
	private boolean hasLockedExit;
	private EnumMap<Direction, Item> keys;
	private List<Item> items;
	private ConcurrentHashMap<Mob, Boolean> creatures;
	private transient ConcurrentHashMap<Player, Boolean> players;

	public Room(int prid, String desc, String filepath) {
		this.rID = prid;
		this.description = desc;
		this.filepath = filepath;
		this.north = null;
		this.south = null;
		this.east = null;
		this.west = null;
		this.up = null;
		this.down = null;

		items = new ArrayList<Item>();
		creatures = new ConcurrentHashMap<Mob, Boolean>();
	}

	public void setNorth(Room room) {
		this.north = room;
	}

	public void setSouth(Room room) {
		this.south = room;
	}

	public void setEast(Room room) {
		this.east = room;
	}

	public void setWest(Room room) {
		this.west = room;
	}

	public void setUp(Room room) {
		this.up = room;
	}

	public void setDown(Room room) {
		this.down = room;
	}

	/**
	 * Returns a String containing information about the room.
	 * 
	 * @return a String including the direction of each exit, and the mobs,
	 *         players, and items present.
	 */
	public String look() {
		String result = "\n" + description;
		result += "\nExits: ";
		if (north != null)
			result += "north, ";
		if (south != null)
			result += "south, ";
		if (west != null)
			result += "west, ";
		if (east != null)
			result += "east, ";
		if (up != null)
			result += "up, ";
		if (down != null)
			result += "down, ";
		result = result.substring(0, result.length() - 2) + ".";
		result += "\nIn this room:";
		for (Mob m : creatures.keySet())
			result += "\n  " + m.getName();
		result += "\nPlayers:";
		for (Player p : players.keySet())
			result += "\n  " + p.getName();
		if (!items.isEmpty()) {
			result += "\n Items:";
			for (Item i : items)
				result += "\n  " + i.getName();
		}
		return result + "\n";
	}

	public void addCreatures(Mob mob) {
		creatures.put(mob, true);
	}

	public List<Mob> listCreatures() {
		return new LinkedList<Mob>(creatures.keySet());
	}

	public void drop(Item item) {
		items.add(item);
	}

	public List<Item> listItems() {
		return items;
	}

	public boolean take(Item item) {
		return items.remove(item);
	}

	public Set<Player> getPlayers() {
		checkTableReference();
		return players.keySet();
	}

	public void addPlayer(Player player) {
		checkTableReference();
		players.put(player, true);
	}

	// TODO
	public void removeMob(Mob mob) {
		creatures.remove(mob);
	}

	public void removePlayer(Player player) {
		checkTableReference();
		players.remove(player);
	}

	// TODO
	public boolean moveMob(Mob amob, Direction direction, ArrayList<Room> movable) {
		switch (direction) {
		case DOWN:
			if (down != null) {
				if(movable.contains(down)){
					amob.setRoom(down);
					removeMob(amob);
					down.addCreatures(amob);
					return true;
				}
			}
			break;
		case EAST:
			if (east != null) {
				if(movable.contains(east)){
					amob.setRoom(east);
					removeMob(amob);
					east.addCreatures(amob);
					return true;
				}
			}
			break;
		case NORTH:
			if (north != null) {
				if(movable.contains(north)){
					amob.setRoom(north);
					removeMob(amob);
					north.addCreatures(amob);
					return true;
				}
			}
			break;
		case SOUTH:
			if (south != null) {
				if(movable.contains(south)){
					amob.setRoom(south);
					removeMob(amob);
					south.addCreatures(amob);
					return true;
				}
			}
			break;
		case UP:
			if (up != null) {
				if(movable.contains(up)){
					amob.setRoom(up);
					removeMob(amob);
					up.addCreatures(amob);
					return true;
				}
			}
			break;
		case WEST:
			if (west != null) {
				if(movable.contains(west)){
					amob.setRoom(west);
					removeMob(amob);
					west.addCreatures(amob);
					return true;
				}
			}
			break;
		default:
			return false;
		}
		return false;
	}

	/**
	 * Attempts to move a player in the specified direction.
	 * 
	 * @param player
	 *            - the player attempting to move.
	 * @param direction
	 *            - the movement direction.
	 * @return true if the player successfully moved.
	 */
	public boolean move(Player player, Direction direction) {
		if (canPass(player, direction))
			switch (direction) {
			case DOWN:
				if (down != null) {
					player.setRoom(down);
					removePlayer(player);
					down.addPlayer(player);
					return true;
				}
				break;
			case EAST:
				if (east != null) {
					player.setRoom(east);
					removePlayer(player);
					east.addPlayer(player);
					return true;
				}
				break;
			case NORTH:
				if (north != null) {
					player.setRoom(north);
					removePlayer(player);
					north.addPlayer(player);
					return true;
				}
				break;
			case SOUTH:
				if (south != null) {
					player.setRoom(south);
					removePlayer(player);
					south.addPlayer(player);
					return true;
				}
				break;
			case UP:
				if (up != null) {
					player.setRoom(up);
					removePlayer(player);
					up.addPlayer(player);
					return true;
				}
				break;
			case WEST:
				if (west != null) {
					player.setRoom(west);
					removePlayer(player);
					west.addPlayer(player);
					return true;
				}
				break;
			default:
				return false;
			}
		return false;
	}

	/**
	 * Determine if a player can move in a given direction by checking whether
	 * specified direction has a locked exit.
	 * 
	 * @param player
	 *            - a player making a movement request
	 * @param direction
	 *            - the direction to be checked for locks
	 * @return true if no locks prevent the player from moving in the given
	 *         direction.
	 */
	private boolean canPass(Player player, Direction direction) {
		if (hasLockedExit) { // is there a locked exit?
			if (player.itemList().contains(SkeletonKey.makeKey()))
				return true;
			Item key = keys.get(direction);
			if (key != null && !player.itemList().contains(key))
				return false; // player doesn't have key
		}
		return true; // no locked exit or player has key
	}

	/**
	 * Create the player list if none exists.
	 */
	private void checkTableReference() {
		if (players == null)
			players = new ConcurrentHashMap<Player, Boolean>();
	}

	/**
	 * Flags the room as having a locked exit and registers the relevant key.
	 * 
	 * @param dir
	 *            - the direction that is locked.
	 * @param item
	 *            - the key required to pass in the given direction.
	 */
	public void setLock(Direction dir, Item item) {
		hasLockedExit = true;
		keys.put(dir, item);
	}

	public String getFilePath() {
		return filepath;
	}
}
