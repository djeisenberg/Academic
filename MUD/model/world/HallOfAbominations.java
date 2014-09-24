package model.world;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import model.creatures.Monster;
import model.creatures.abomdungeon.Absolution1;
import model.creatures.abomdungeon.Absolution2;
import model.creatures.abomdungeon.Absolution3;
import model.creatures.abomdungeon.Absolution4;
import model.creatures.abomdungeon.Absolution5;
import model.creatures.abomdungeon.Aeras;
import model.creatures.abomdungeon.Aquas;
import model.creatures.abomdungeon.Belphagor;
import model.creatures.abomdungeon.CorruptedGolem;
import model.creatures.abomdungeon.Ignas;
import model.creatures.abomdungeon.Lilith1;
import model.creatures.abomdungeon.Lilith2;
import model.creatures.abomdungeon.Lilith3;
import model.creatures.abomdungeon.Lilith4;
import model.creatures.abomdungeon.Lilith5;
import model.creatures.abomdungeon.ObsidianGolem;
import model.creatures.abomdungeon.Penance1;
import model.creatures.abomdungeon.Penance2;
import model.creatures.abomdungeon.Penance3;
import model.creatures.abomdungeon.Penance4;
import model.creatures.abomdungeon.Penance5;
import model.creatures.abomdungeon.QuicksilverGolem;
import model.creatures.abomdungeon.Terras;
import model.creatures.abomdungeon.Valforyn;

public class HallOfAbominations implements Serializable, Zone{

	private static final long serialVersionUID = 1L;
	private int dID;
	private Room entrance;
	private ArrayList<Room> dungeon;
	private ArrayList<Room> bossroom;
	private ArrayList<Room> movablerooms;
	private CopyOnWriteArrayList<Monster> mobs;
	
	public HallOfAbominations(int size)
	{
		dungeon = new ArrayList<Room>();
		bossroom = new ArrayList<Room>();
		mobs = new CopyOnWriteArrayList<Monster>();
		movablerooms = new ArrayList<Room>();
		
		dID = 400;
		
		dungeon.add(new Room(400, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(401, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(402, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(403, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(404, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(405, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(406, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(407, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(408, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(409, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(410, "", "icons" + File.separator + "default.png"));

		
		entrance = dungeon.get(0);
		dungeon.get(0).setSouth(dungeon.get(1));
		dungeon.get(1).setNorth(dungeon.get(0));
		dungeon.get(1).setWest(dungeon.get(2));
		dungeon.get(1).setEast(dungeon.get(5));
		dungeon.get(1).setSouth(dungeon.get(10));
		dungeon.get(2).setEast(dungeon.get(1));
		dungeon.get(2).setSouth(dungeon.get(3));
		dungeon.get(3).setNorth(dungeon.get(2));
		dungeon.get(3).setSouth(dungeon.get(4));
		dungeon.get(4).setNorth(dungeon.get(3));
		dungeon.get(4).setSouth(dungeon.get(8));
		dungeon.get(5).setWest(dungeon.get(1));
		dungeon.get(5).setSouth(dungeon.get(6));
		dungeon.get(6).setNorth(dungeon.get(5));
		dungeon.get(6).setSouth(dungeon.get(7));
		dungeon.get(7).setNorth(dungeon.get(6));
		dungeon.get(7).setSouth(dungeon.get(9));
		dungeon.get(8).setNorth(dungeon.get(4));
		dungeon.get(9).setNorth(dungeon.get(7));
		dungeon.get(10).setNorth(dungeon.get(1));

		bossroom.add(dungeon.get(10));
		bossroom.add(dungeon.get(9));
		bossroom.add(dungeon.get(8));
		
		movablerooms = dungeon;
		movablerooms.remove(10);
		movablerooms.remove(9);
		movablerooms.remove(8);
		movablerooms.remove(0);
		

		switch(size){
		
		case 1:
			mobs.add(new Lilith1());
			mobs.add(new Absolution1());
			mobs.add(new Penance1());
			
			mobs.add(new Belphagor(movablerooms));
			mobs.add(new Valforyn(movablerooms));
			mobs.add(new CorruptedGolem(movablerooms));
			mobs.add(new ObsidianGolem(movablerooms));
			mobs.add(new QuicksilverGolem(movablerooms));
			mobs.add(new Aeras(movablerooms));
			mobs.add(new Aeras(movablerooms));
			mobs.add(new Aquas(movablerooms));
			mobs.add(new Aquas(movablerooms));
			mobs.add(new Ignas(movablerooms));
			mobs.add(new Ignas(movablerooms));
			mobs.add(new Terras(movablerooms));
			mobs.add(new Terras(movablerooms));
			break;
		case 2:
			mobs.add(new Lilith2());
			mobs.add(new Absolution2());
			mobs.add(new Penance2());
			
			mobs.add(new Belphagor(movablerooms));
			mobs.add(new Valforyn(movablerooms));
			mobs.add(new CorruptedGolem(movablerooms));
			mobs.add(new ObsidianGolem(movablerooms));
			mobs.add(new QuicksilverGolem(movablerooms));
			mobs.add(new Aeras(movablerooms));
			mobs.add(new Aeras(movablerooms));
			mobs.add(new Aeras(movablerooms));
			mobs.add(new Aquas(movablerooms));
			mobs.add(new Aquas(movablerooms));
			mobs.add(new Aquas(movablerooms));
			mobs.add(new Ignas(movablerooms));
			mobs.add(new Ignas(movablerooms));
			mobs.add(new Ignas(movablerooms));
			mobs.add(new Terras(movablerooms));
			mobs.add(new Terras(movablerooms));
			mobs.add(new Terras(movablerooms));
			break;
		case 3:
			mobs.add(new Lilith3());
			mobs.add(new Absolution3());
			mobs.add(new Penance3());
			
			mobs.add(new Belphagor(movablerooms));
			mobs.add(new Belphagor(movablerooms));
			mobs.add(new Valforyn(movablerooms));
			mobs.add(new Valforyn(movablerooms));
			mobs.add(new CorruptedGolem(movablerooms));
			mobs.add(new CorruptedGolem(movablerooms));
			mobs.add(new ObsidianGolem(movablerooms));
			mobs.add(new ObsidianGolem(movablerooms));
			mobs.add(new QuicksilverGolem(movablerooms));
			mobs.add(new QuicksilverGolem(movablerooms));
			mobs.add(new Aeras(movablerooms));
			mobs.add(new Aeras(movablerooms));
			mobs.add(new Aeras(movablerooms));
			mobs.add(new Aquas(movablerooms));
			mobs.add(new Aquas(movablerooms));
			mobs.add(new Aquas(movablerooms));
			mobs.add(new Ignas(movablerooms));
			mobs.add(new Ignas(movablerooms));
			mobs.add(new Ignas(movablerooms));
			mobs.add(new Terras(movablerooms));
			mobs.add(new Terras(movablerooms));
			mobs.add(new Terras(movablerooms));
			break;
		case 4:
			mobs.add(new Lilith4());
			mobs.add(new Absolution4());
			mobs.add(new Penance4());
			
			mobs.add(new Belphagor(movablerooms));
			mobs.add(new Belphagor(movablerooms));
			mobs.add(new Valforyn(movablerooms));
			mobs.add(new Valforyn(movablerooms));
			mobs.add(new CorruptedGolem(movablerooms));
			mobs.add(new CorruptedGolem(movablerooms));
			mobs.add(new ObsidianGolem(movablerooms));
			mobs.add(new ObsidianGolem(movablerooms));
			mobs.add(new QuicksilverGolem(movablerooms));
			mobs.add(new QuicksilverGolem(movablerooms));
			mobs.add(new Aeras(movablerooms));
			mobs.add(new Aeras(movablerooms));
			mobs.add(new Aeras(movablerooms));
			mobs.add(new Aeras(movablerooms));
			mobs.add(new Aquas(movablerooms));
			mobs.add(new Aquas(movablerooms));
			mobs.add(new Aquas(movablerooms));
			mobs.add(new Aquas(movablerooms));
			mobs.add(new Ignas(movablerooms));
			mobs.add(new Ignas(movablerooms));
			mobs.add(new Ignas(movablerooms));
			mobs.add(new Ignas(movablerooms));
			mobs.add(new Terras(movablerooms));
			mobs.add(new Terras(movablerooms));
			mobs.add(new Terras(movablerooms));
			mobs.add(new Terras(movablerooms));
		case 5:
			mobs.add(new Lilith5());
			mobs.add(new Absolution5());
			mobs.add(new Penance5());
			
			mobs.add(new Belphagor(movablerooms));
			mobs.add(new Belphagor(movablerooms));
			mobs.add(new Belphagor(movablerooms));
			mobs.add(new Valforyn(movablerooms));
			mobs.add(new Valforyn(movablerooms));
			mobs.add(new Valforyn(movablerooms));
			mobs.add(new CorruptedGolem(movablerooms));
			mobs.add(new CorruptedGolem(movablerooms));
			mobs.add(new CorruptedGolem(movablerooms));
			mobs.add(new ObsidianGolem(movablerooms));
			mobs.add(new ObsidianGolem(movablerooms));
			mobs.add(new ObsidianGolem(movablerooms));
			mobs.add(new QuicksilverGolem(movablerooms));
			mobs.add(new QuicksilverGolem(movablerooms));
			mobs.add(new QuicksilverGolem(movablerooms));
			mobs.add(new Aeras(movablerooms));
			mobs.add(new Aeras(movablerooms));
			mobs.add(new Aeras(movablerooms));
			mobs.add(new Aeras(movablerooms));
			mobs.add(new Aquas(movablerooms));
			mobs.add(new Aquas(movablerooms));
			mobs.add(new Aquas(movablerooms));
			mobs.add(new Aquas(movablerooms));
			mobs.add(new Ignas(movablerooms));
			mobs.add(new Ignas(movablerooms));
			mobs.add(new Ignas(movablerooms));
			mobs.add(new Ignas(movablerooms));
			mobs.add(new Terras(movablerooms));
			mobs.add(new Terras(movablerooms));
			mobs.add(new Terras(movablerooms));
			mobs.add(new Terras(movablerooms));
		}
		
		mobs.get(0).setRoom(bossroom.get(0));
		mobs.get(1).setRoom(bossroom.get(1));
		mobs.get(2).setRoom(bossroom.get(2));
		for(int i = 3; i < mobs.size(); i++)
		{
			Collections.shuffle(movablerooms);
			mobs.get(i).setRoom(movablerooms.get(0));
		}
			
	}

	@Override
	public int getDungeonID(){
		return dID;
	}
	
	public ArrayList<Room> listBossRooms(){
		return bossroom;
	}
	
	public ArrayList<Room> listNotBossRooms(){
		return movablerooms;
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
