package model.world;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import model.creatures.Creature;
import model.creatures.Mob;
import model.creatures.gobdungeon.Goblin;

/**
 * NOT USED
 * 
 * @author Steven Chandler
 */

public class Dungeon implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int dID;
	private Room entrance;
	private ArrayList<Room> dungeon;
	
	public Dungeon()
	{
		dungeon = new ArrayList<Room>();
		dID = 1;
		List<Mob> mobs; 
		
		dungeon.add(new Room(100, "A Room", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(100, "Another Room", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(102, "Still Another Room", "icons" + File.separator + "default.png"));
		
		entrance = dungeon.get(0);
		dungeon.get(0).setEast(dungeon.get(1));
		dungeon.get(1).setEast(dungeon.get(2));
		dungeon.get(1).setWest(dungeon.get(0));
		dungeon.get(2).setWest(dungeon.get(1));
		
		Random x = new Random();
		int temp = x.nextInt(dungeon.size());
	//	dungeon.get(temp).addCreatures(new Goblin());

		for(Room y:dungeon)
		{
			mobs = y.listCreatures();
				for(Creature z:mobs)
				{
					z.setRoom(y);
				}
		}
		
		
	}

	public ArrayList<Room> listRooms() {
		return dungeon;
	}

	public Room getEntrance() {
		return entrance;
	}
}
