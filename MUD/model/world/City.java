package model.world;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import model.creatures.NPC;
import model.creatures.npcs.InnKeeper;
import model.creatures.npcs.ShopKeeper;
import model.creatures.npcs.TownGuard;

public class City implements Serializable{

	private static final long serialVersionUID = 1L;
	private int dID;
	private Room entrance;
	private Room portal;
	private ArrayList<Room> city;
	private CopyOnWriteArrayList<NPC> mobs;
	
	public City()
	{
		city = new ArrayList<Room>();
		mobs = new CopyOnWriteArrayList<NPC>();
		
		dID = 100;
		
		city.add(new Room(100, "City Gate", "icons" + File.separator + "default.png"));
		city.add(new Room(101, "Town Square", "icons" + File.separator + "default.png"));
		city.add(new Room(102, "Inn", "icons" + File.separator + "default.png"));
		city.add(new Room(103, "Shop", "icons" + File.separator + "default.png"));
		
		entrance = city.get(0);
		portal = city.get(0);
		city.get(0).setSouth(city.get(1));
		city.get(1).setNorth(city.get(0));
		city.get(1).setWest(city.get(2));
		city.get(1).setEast(city.get(3));
		city.get(2).setEast(city.get(1));
		city.get(3).setWest(city.get(1));

		mobs.add(new InnKeeper());
		mobs.add(new ShopKeeper());
		mobs.add(new TownGuard(city));
		mobs.add(new TownGuard(city));
		mobs.add(new TownGuard(city));
		mobs.add(new TownGuard(city));
		
		mobs.get(0).setRoom(city.get(2));
		mobs.get(1).setRoom(city.get(3));
		mobs.get(2).setRoom(city.get(0));
		mobs.get(3).setRoom(city.get(0));
		mobs.get(4).setRoom(city.get(1));
		mobs.get(5).setRoom(city.get(1));

	}
	
	public Room getPortal(){
		return portal;
	}

	public int getDungeonID(){
		return dID;
	}
	
	public List<NPC> listMobs(){
		return mobs;
	}
	
	public ArrayList<Room> listRooms() {
		return city;
	}

	public Room getEntrance() {
		return entrance;
	}
}
