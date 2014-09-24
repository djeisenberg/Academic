package model.world;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import model.creatures.Monster;
import model.creatures.gobdungeon.Goblin;
import model.creatures.gobdungeon.GoblinKing1;
import model.creatures.gobdungeon.GoblinKing2;
import model.creatures.gobdungeon.GoblinKing3;
import model.creatures.gobdungeon.GoblinKing4;
import model.creatures.gobdungeon.GoblinKing5;
import model.creatures.gobdungeon.GoblinLt;
import model.creatures.gobdungeon.Warg;

public class GoblinDungeon implements Serializable, Zone{

	private static final long serialVersionUID = 1L;
	private int dID;
	private Room entrance;
	private ArrayList<Room> dungeon;
	private ArrayList<Room> bossroom;
	private ArrayList<Room> movablerooms;
	private CopyOnWriteArrayList<Monster> mobs;
	
	public GoblinDungeon(int size)
	{
		dungeon = new ArrayList<Room>();
		bossroom = new ArrayList<Room>();
		mobs = new CopyOnWriteArrayList<Monster>();
		movablerooms = new ArrayList<Room>();
		
		dID = 200;
		Random x = new Random();
		int number = 0;
		int temp = 0;
		
		dungeon.add(new Room(200, "A Room", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(201, "Another Room", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(202, "Still Another Room", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(203, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(204, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(205, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(206, "Throne Room", "icons" + File.separator + "default.png"));
		
		entrance = dungeon.get(0);
		dungeon.get(0).setSouth(dungeon.get(1));
		dungeon.get(1).setNorth(dungeon.get(0));
		dungeon.get(1).setSouth(dungeon.get(2));
		dungeon.get(2).setNorth(dungeon.get(1));
		dungeon.get(2).setSouth(dungeon.get(5));
		dungeon.get(2).setWest(dungeon.get(3));
		dungeon.get(2).setEast(dungeon.get(4));
		dungeon.get(3).setEast(dungeon.get(2));
		dungeon.get(4).setWest(dungeon.get(2));
		dungeon.get(5).setNorth(dungeon.get(2));
		dungeon.get(5).setDown(dungeon.get(6));
		dungeon.get(6).setUp(dungeon.get(5));
		
		bossroom.add(dungeon.get(6));
		
		movablerooms.addAll(dungeon);
		movablerooms.remove(6);
		movablerooms.remove(0);
		

		switch(size){
		default:
		case 1:
			mobs.add(new GoblinKing1());
			number = 10;
			
			temp = x.nextInt((1) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new GoblinLt(movablerooms));
			
			temp = x.nextInt((2) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Warg(movablerooms));
			
			for(int i = 0; i < number; i++)
				mobs.add(new Goblin(movablerooms));
			
			break;
		case 2:
			mobs.add(new GoblinKing2());
			number = 17;
			
			temp = x.nextInt((3) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new GoblinLt(movablerooms));
			
			temp = x.nextInt((4) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Warg(movablerooms));
			
			for(int i = 0; i < number; i++)
				mobs.add(new Goblin(movablerooms));
			
			break;
		case 3:
			mobs.add(new GoblinKing3());
			number = 24;
			
			temp = x.nextInt((5) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new GoblinLt(movablerooms));
			
			temp = x.nextInt((6) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Warg(movablerooms));
			
			for(int i = 0; i < number; i++)
				mobs.add(new Goblin(movablerooms));
			
			break;
		case 4:
			mobs.add(new GoblinKing4());
			number = 30;
			
			temp = x.nextInt((5) + 3);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new GoblinLt(movablerooms));
			
			temp = x.nextInt((6) + 3);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Warg(movablerooms));
			
			for(int i = 0; i < number; i++)
				mobs.add(new Goblin(movablerooms));
			
			break;
		case 5:
			mobs.add(new GoblinKing5());
			number = 35;
			
			temp = x.nextInt((5) + 8);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new GoblinLt(movablerooms));
			
			temp = x.nextInt((6) + 3);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Warg(movablerooms));
			
			for(int i = 0; i < number; i++)
				mobs.add(new Goblin(movablerooms));
			
			break;
		}
		
		mobs.get(0).setRoom(bossroom.get(0));
		for(int i = 1; i < mobs.size(); i++)
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
	
	public ArrayList<Room> listMovableRooms(){
		return movablerooms;
	}
	
	@Override
	public CopyOnWriteArrayList<Monster> listMobs(){
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
