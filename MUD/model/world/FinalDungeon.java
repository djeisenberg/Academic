package model.world;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import model.creatures.Monster;
import model.creatures.finaldungeon.Andariel;
import model.creatures.finaldungeon.Astarte;
import model.creatures.finaldungeon.Ayperos;
import model.creatures.finaldungeon.Caliadne;
import model.creatures.finaldungeon.Eligos;
import model.creatures.finaldungeon.Orobas;

public class FinalDungeon implements Serializable, Zone{

	private static final long serialVersionUID = 1L;
	private int dID;
	private Room entrance;
	private ArrayList<Room> dungeon;
	private CopyOnWriteArrayList<Monster> mobs;
	
	public FinalDungeon(int size)
	{
		dungeon = new ArrayList<Room>();
		mobs = new CopyOnWriteArrayList<Monster>();
		
		dID = 600;
		
		dungeon.add(new Room(600, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(601, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(602, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(603, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(604, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(605, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(606, "", "icons" + File.separator + "default.png"));

		
		entrance = dungeon.get(0);
		dungeon.get(0).setSouth(dungeon.get(5));
		dungeon.get(0).setNorth(dungeon.get(6));
		dungeon.get(0).setWest(dungeon.get(1));
		dungeon.get(0).setEast(dungeon.get(2));
		dungeon.get(0).setUp(dungeon.get(3));
		dungeon.get(0).setDown(dungeon.get(4));
		dungeon.get(1).setEast(dungeon.get(0));
		dungeon.get(2).setWest(dungeon.get(0));
		dungeon.get(3).setDown(dungeon.get(0));
		dungeon.get(4).setUp(dungeon.get(0));
		dungeon.get(5).setNorth(dungeon.get(0));
		dungeon.get(6).setSouth(dungeon.get(0));

		
		mobs.add(new Andariel());
		mobs.add(new Astarte());
		mobs.add(new Ayperos());
		mobs.add(new Caliadne());
		mobs.add(new Eligos());
		mobs.add(new Orobas());
			

		mobs.get(0).setRoom(dungeon.get(6));
		mobs.get(1).setRoom(dungeon.get(5));
		mobs.get(2).setRoom(dungeon.get(3));
		mobs.get(3).setRoom(dungeon.get(1));
		mobs.get(4).setRoom(dungeon.get(2));
		mobs.get(5).setRoom(dungeon.get(4));

			
	}

	@Override
	public int getDungeonID(){
		return dID;
	}
	

	@Override
	public List<Monster> listMobs(){
		return mobs;
	}
	
	public ArrayList<Room> listRooms() {
		return dungeon;
	}

	@Override
	public Room getEntrance() {
		return entrance;
	}
}
