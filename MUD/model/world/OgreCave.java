package model.world;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import model.creatures.Monster;
import model.creatures.ogredungeon.Beast;
import model.creatures.ogredungeon.Chieftain1;
import model.creatures.ogredungeon.Chieftain2;
import model.creatures.ogredungeon.Chieftain3;
import model.creatures.ogredungeon.Chieftain4;
import model.creatures.ogredungeon.Chieftain5;
import model.creatures.ogredungeon.Ogre;
import model.creatures.ogredungeon.Rager;
import model.creatures.ogredungeon.Shaman;

public class OgreCave implements Serializable, Zone{

	private static final long serialVersionUID = 1L;
	private int dID;
	private Room entrance;
	private ArrayList<Room> dungeon;
	private ArrayList<Room> bossroom;
	private ArrayList<Room> movablerooms;
	private CopyOnWriteArrayList<Monster> mobs;
	
	public OgreCave(int size)
	{
		dungeon = new ArrayList<Room>();
		bossroom = new ArrayList<Room>();
		mobs = new CopyOnWriteArrayList<Monster>();
		movablerooms = new ArrayList<Room>();
		
		dID = 300;
		Random x = new Random();
		int number = 0;
		int temp = 0;
		
		dungeon.add(new Room(300, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(301, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(302, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(303, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(304, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(305, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(306, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(307, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(308, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(309, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(310, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(311, "", "icons" + File.separator + "default.png"));

		
		entrance = dungeon.get(0);
		dungeon.get(0).setSouth(dungeon.get(1));
		dungeon.get(1).setNorth(dungeon.get(0));
		dungeon.get(1).setWest(dungeon.get(2));
		dungeon.get(1).setEast(dungeon.get(7));
		dungeon.get(2).setEast(dungeon.get(1));
		dungeon.get(2).setSouth(dungeon.get(3));
		dungeon.get(2).setDown(dungeon.get(8));
		dungeon.get(2).setUp(dungeon.get(10));
		dungeon.get(3).setNorth(dungeon.get(2));
		dungeon.get(3).setSouth(dungeon.get(4));
		dungeon.get(3).setEast(dungeon.get(5));
		dungeon.get(4).setNorth(dungeon.get(3));
		dungeon.get(4).setDown(dungeon.get(9));
		dungeon.get(5).setWest(dungeon.get(3));
		dungeon.get(5).setSouth(dungeon.get(6));
		dungeon.get(6).setNorth(dungeon.get(5));
		dungeon.get(6).setSouth(dungeon.get(11));
		dungeon.get(7).setWest(dungeon.get(1));
		dungeon.get(8).setSouth(dungeon.get(9));
		dungeon.get(8).setUp(dungeon.get(2));
		dungeon.get(9).setNorth(dungeon.get(8));
		dungeon.get(9).setUp(dungeon.get(4));
		dungeon.get(10).setNorth(dungeon.get(2));
		dungeon.get(11).setNorth(dungeon.get(6));

		bossroom.add(dungeon.get(11));
		
		movablerooms = dungeon;
		movablerooms.remove(11);
		movablerooms.remove(0);
		

		switch(size){
		
		case 1:
			mobs.add(new Chieftain1());
			number = 10;
			
			temp = x.nextInt(1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Shaman(movablerooms));
			
			temp = x.nextInt((1) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Rager(movablerooms));
			
			temp = x.nextInt((1) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Beast(movablerooms));

			for(int i = 0; i < number; i++)
				mobs.add(new Ogre(movablerooms));
			
			break;
		case 2:
			mobs.add(new Chieftain2());
			number = 15;
			
			temp = x.nextInt(2);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Shaman(movablerooms));
			
			temp = x.nextInt((2) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Rager(movablerooms));
			
			temp = x.nextInt((2) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Beast(movablerooms));

			for(int i = 0; i < number; i++)
				mobs.add(new Ogre(movablerooms));
			
			break;
		case 3:
			mobs.add(new Chieftain3());
			number = 20;
			
			temp = x.nextInt((2) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Shaman(movablerooms));
			
			temp = x.nextInt((3) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Rager(movablerooms));
			
			temp = x.nextInt((5) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Beast(movablerooms));

			for(int i = 0; i < number; i++)
				mobs.add(new Ogre(movablerooms));
			
			break;
		case 4:
			mobs.add(new Chieftain4());
			number = 25;
			
			temp = x.nextInt((2) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Shaman(movablerooms));
			
			temp = x.nextInt((5) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Rager(movablerooms));
			
			temp = x.nextInt((7) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Beast(movablerooms));

			for(int i = 0; i < number; i++)
				mobs.add(new Ogre(movablerooms));
			
			break;
		case 5:
			mobs.add(new Chieftain5());
			number = 30;
			
			temp = x.nextInt((3) + 2);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Shaman(movablerooms));
			
			temp = x.nextInt((5) + 3);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Rager(movablerooms));
			
			temp = x.nextInt((5) + 3);
			number -= temp;
			for(int i = 0; i < temp; i++)
				mobs.add(new Beast(movablerooms));

			for(int i = 0; i < number; i++)
				mobs.add(new Ogre(movablerooms));
			
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
