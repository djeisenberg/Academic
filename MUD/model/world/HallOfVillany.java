package model.world;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import model.creatures.Monster;
import model.creatures.humandungeon.Assassin;
import model.creatures.humandungeon.Avian;
import model.creatures.humandungeon.Cassandra1;
import model.creatures.humandungeon.Cassandra2;
import model.creatures.humandungeon.Cassandra3;
import model.creatures.humandungeon.Cassandra4;
import model.creatures.humandungeon.Cassandra5;
import model.creatures.humandungeon.GuardHound;
import model.creatures.humandungeon.Priestess;
import model.creatures.humandungeon.Skeleton;
import model.creatures.humandungeon.Soldier;
import model.creatures.humandungeon.Sorceress;
import model.creatures.humandungeon.Wraith;
import model.creatures.humandungeon.Zombie;


public class HallOfVillany implements Serializable, Zone{

	private static final long serialVersionUID = 1L;
	private int dID;
	private Room entrance;
	private ArrayList<Room> dungeon;
	private ArrayList<Room> bossroom;
	private ArrayList<Room> humanrooms;
	private ArrayList<Room> undeadrooms;
	private ArrayList<Room> avianrooms;
	private CopyOnWriteArrayList<Monster> mobs;
	private ArrayList<Monster> humans;
	private ArrayList<Monster> avians;
	private ArrayList<Monster> undead;
	
	public HallOfVillany(int size)
	{
		dungeon = new ArrayList<Room>();
		bossroom = new ArrayList<Room>();
		mobs = new CopyOnWriteArrayList<Monster>();
		humanrooms = new ArrayList<Room>();
		undeadrooms = new ArrayList<Room>();
		avianrooms = new ArrayList<Room>();
		humans = new ArrayList<Monster>();
		avians = new ArrayList<Monster>();
		undead = new ArrayList<Monster>();
		
		dID = 500;
		Random x = new Random();
		int number = 0;
		int temp = 0;
		
		dungeon.add(new Room(500, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(501, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(502, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(503, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(504, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(505, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(506, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(507, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(508, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(509, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(510, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(511, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(512, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(513, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(514, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(515, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(516, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(517, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(518, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(519, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(520, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(521, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(522, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(523, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(524, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(525, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(526, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(527, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(528, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(529, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(530, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(531, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(532, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(533, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(534, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(535, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(536, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(537, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(538, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(539, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(540, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(541, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(542, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(543, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(544, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(545, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(546, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(547, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(548, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(549, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(550, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(551, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(552, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(553, "", "icons" + File.separator + "default.png"));
		dungeon.add(new Room(554, "", "icons" + File.separator + "default.png"));


		
		entrance = dungeon.get(0);
		dungeon.get(0).setSouth(dungeon.get(14));
		dungeon.get(0).setWest(dungeon.get(1));
		dungeon.get(0).setEast(dungeon.get(13));
		dungeon.get(1).setEast(dungeon.get(0));
		dungeon.get(1).setWest(dungeon.get(2));
		dungeon.get(2).setEast(dungeon.get(1));
		dungeon.get(2).setSouth(dungeon.get(3));
		dungeon.get(2).setUp(dungeon.get(15));
		dungeon.get(3).setNorth(dungeon.get(2));
		dungeon.get(3).setSouth(dungeon.get(4));
		dungeon.get(4).setNorth(dungeon.get(3));
		dungeon.get(4).setSouth(dungeon.get(5));
		dungeon.get(5).setNorth(dungeon.get(4));
		dungeon.get(5).setEast(dungeon.get(6));
		dungeon.get(5).setUp(dungeon.get(21));
		dungeon.get(6).setEast(dungeon.get(7));
		dungeon.get(6).setWest(dungeon.get(5));
		dungeon.get(7).setNorth(dungeon.get(14));
		dungeon.get(7).setSouth(dungeon.get(27));
		dungeon.get(7).setWest(dungeon.get(6));
		dungeon.get(7).setEast(dungeon.get(8));
		dungeon.get(8).setWest(dungeon.get(7));
		dungeon.get(8).setEast(dungeon.get(9));
		dungeon.get(9).setNorth(dungeon.get(10));
		dungeon.get(9).setWest(dungeon.get(8));
		dungeon.get(9).setUp(dungeon.get(19));
		dungeon.get(10).setNorth(dungeon.get(11));
		dungeon.get(10).setSouth(dungeon.get(9));
		dungeon.get(11).setNorth(dungeon.get(12));
		dungeon.get(11).setSouth(dungeon.get(10));
		dungeon.get(12).setSouth(dungeon.get(11));
		dungeon.get(12).setWest(dungeon.get(13));
		dungeon.get(12).setUp(dungeon.get(17));
		dungeon.get(13).setWest(dungeon.get(0));
		dungeon.get(13).setEast(dungeon.get(12));
		dungeon.get(14).setNorth(dungeon.get(0));
		dungeon.get(14).setSouth(dungeon.get(7));
		dungeon.get(15).setEast(dungeon.get(16));
		dungeon.get(15).setSouth(dungeon.get(22));
		dungeon.get(15).setUp(dungeon.get(23));
		dungeon.get(15).setDown(dungeon.get(2));
		dungeon.get(16).setEast(dungeon.get(17));
		dungeon.get(16).setWest(dungeon.get(15));
		dungeon.get(17).setWest(dungeon.get(16));
		dungeon.get(17).setSouth(dungeon.get(18));
		dungeon.get(17).setUp(dungeon.get(24));
		dungeon.get(17).setDown(dungeon.get(12));
		dungeon.get(18).setNorth(dungeon.get(17));
		dungeon.get(18).setSouth(dungeon.get(19));
		dungeon.get(19).setNorth(dungeon.get(18));
		dungeon.get(19).setWest(dungeon.get(20));
		dungeon.get(19).setUp(dungeon.get(26));
		dungeon.get(19).setDown(dungeon.get(9));
		dungeon.get(20).setWest(dungeon.get(21));
		dungeon.get(20).setEast(dungeon.get(19));
		dungeon.get(21).setNorth(dungeon.get(22));
		dungeon.get(21).setEast(dungeon.get(20));
		dungeon.get(21).setUp(dungeon.get(25));
		dungeon.get(21).setDown(dungeon.get(5));
		dungeon.get(22).setNorth(dungeon.get(15));
		dungeon.get(22).setSouth(dungeon.get(21));
		dungeon.get(23).setDown(dungeon.get(15));
		dungeon.get(24).setDown(dungeon.get(17));
		dungeon.get(25).setDown(dungeon.get(21));
		dungeon.get(26).setDown(dungeon.get(19));
		dungeon.get(27).setNorth(dungeon.get(7));
		dungeon.get(27).setSouth(dungeon.get(28));
		dungeon.get(27).setEast(dungeon.get(33));
		dungeon.get(27).setWest(dungeon.get(30));
		dungeon.get(28).setNorth(dungeon.get(27));
		dungeon.get(28).setSouth(dungeon.get(29));
		dungeon.get(28).setEast(dungeon.get(34));
		dungeon.get(28).setWest(dungeon.get(31));
		dungeon.get(28).setUp(dungeon.get(40));
		dungeon.get(28).setDown(dungeon.get(49));
		dungeon.get(29).setNorth(dungeon.get(28));
		dungeon.get(29).setSouth(dungeon.get(54));
		dungeon.get(29).setEast(dungeon.get(35));
		dungeon.get(29).setWest(dungeon.get(32));
		dungeon.get(30).setSouth(dungeon.get(31));
		dungeon.get(30).setEast(dungeon.get(27));
		dungeon.get(31).setNorth(dungeon.get(30));
		dungeon.get(31).setSouth(dungeon.get(32));
		dungeon.get(31).setEast(dungeon.get(28));
		dungeon.get(32).setNorth(dungeon.get(31));
		dungeon.get(32).setEast(dungeon.get(29));
		dungeon.get(33).setSouth(dungeon.get(34));
		dungeon.get(33).setWest(dungeon.get(27));
		dungeon.get(34).setNorth(dungeon.get(33));
		dungeon.get(34).setSouth(dungeon.get(35));
		dungeon.get(34).setWest(dungeon.get(28));
		dungeon.get(35).setNorth(dungeon.get(34));
		dungeon.get(35).setWest(dungeon.get(29));
		dungeon.get(37).setSouth(dungeon.get(40));
		dungeon.get(37).setEast(dungeon.get(38));
		dungeon.get(37).setWest(dungeon.get(36));
		dungeon.get(40).setNorth(dungeon.get(37));
		dungeon.get(40).setSouth(dungeon.get(43));
		dungeon.get(40).setEast(dungeon.get(41));
		dungeon.get(40).setWest(dungeon.get(39));
		dungeon.get(40).setDown(dungeon.get(28));
		dungeon.get(43).setNorth(dungeon.get(40));
		dungeon.get(43).setEast(dungeon.get(44));
		dungeon.get(43).setWest(dungeon.get(42));
		dungeon.get(36).setSouth(dungeon.get(39));
		dungeon.get(36).setEast(dungeon.get(37));
		dungeon.get(39).setNorth(dungeon.get(36));
		dungeon.get(39).setSouth(dungeon.get(42));
		dungeon.get(39).setEast(dungeon.get(40));
		dungeon.get(42).setNorth(dungeon.get(39));
		dungeon.get(42).setEast(dungeon.get(43));
		dungeon.get(38).setSouth(dungeon.get(41));
		dungeon.get(38).setWest(dungeon.get(37));
		dungeon.get(41).setNorth(dungeon.get(38));
		dungeon.get(41).setSouth(dungeon.get(44));
		dungeon.get(41).setWest(dungeon.get(40));
		dungeon.get(44).setNorth(dungeon.get(41));
		dungeon.get(44).setWest(dungeon.get(43));
		dungeon.get(46).setSouth(dungeon.get(49));
		dungeon.get(46).setEast(dungeon.get(47));
		dungeon.get(46).setWest(dungeon.get(45));
		dungeon.get(49).setNorth(dungeon.get(46));
		dungeon.get(49).setSouth(dungeon.get(52));
		dungeon.get(49).setEast(dungeon.get(50));
		dungeon.get(49).setWest(dungeon.get(48));
		dungeon.get(49).setUp(dungeon.get(28));
		dungeon.get(52).setNorth(dungeon.get(49));
		dungeon.get(52).setEast(dungeon.get(53));
		dungeon.get(52).setWest(dungeon.get(51));
		dungeon.get(45).setSouth(dungeon.get(46));
		dungeon.get(45).setEast(dungeon.get(48));
		dungeon.get(48).setNorth(dungeon.get(45));
		dungeon.get(48).setSouth(dungeon.get(51));
		dungeon.get(48).setEast(dungeon.get(49));
		dungeon.get(51).setNorth(dungeon.get(48));
		dungeon.get(51).setEast(dungeon.get(52));
		dungeon.get(47).setSouth(dungeon.get(50));
		dungeon.get(47).setWest(dungeon.get(46));
		dungeon.get(50).setNorth(dungeon.get(47));
		dungeon.get(50).setSouth(dungeon.get(53));
		dungeon.get(50).setWest(dungeon.get(49));
		dungeon.get(53).setNorth(dungeon.get(50));
		dungeon.get(53).setWest(dungeon.get(52));
		dungeon.get(54).setNorth(dungeon.get(29));
		
		bossroom.add(dungeon.get(54));
		
		avianrooms.add(dungeon.get(15));
		avianrooms.add(dungeon.get(16));
		avianrooms.add(dungeon.get(17));
		avianrooms.add(dungeon.get(18));
		avianrooms.add(dungeon.get(19));
		avianrooms.add(dungeon.get(20));
		avianrooms.add(dungeon.get(21));
		avianrooms.add(dungeon.get(22));
		avianrooms.add(dungeon.get(23));
		avianrooms.add(dungeon.get(24));
		avianrooms.add(dungeon.get(25));
		avianrooms.add(dungeon.get(26));
		
		undeadrooms.add(dungeon.get(45));
		undeadrooms.add(dungeon.get(46));
		undeadrooms.add(dungeon.get(47));
		undeadrooms.add(dungeon.get(48));
		undeadrooms.add(dungeon.get(49));
		undeadrooms.add(dungeon.get(50));
		undeadrooms.add(dungeon.get(51));
		undeadrooms.add(dungeon.get(52));
		undeadrooms.add(dungeon.get(53));
		
		humanrooms = dungeon;
		humanrooms.remove(54);
		humanrooms.remove(53);
		humanrooms.remove(52);
		humanrooms.remove(51);
		humanrooms.remove(50);
		humanrooms.remove(49);
		humanrooms.remove(48);
		humanrooms.remove(47);
		humanrooms.remove(46);
		humanrooms.remove(45);
		
		
	switch(size){
		
		case 1:
			mobs.add(new Cassandra1());
			number = 25;
			
			temp = x.nextInt((1) + 2);
			number -= temp;
			for(int i = 0; i < temp; i++)
				avians.add(new Avian(avianrooms));

			undead.add(new Wraith(undeadrooms));
			number --;
			
			temp = x.nextInt((2) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				undead.add(new Zombie(undeadrooms));
			
			temp = x.nextInt((1) + 2);
			number -= temp;
			for(int i = 0; i < temp; i++)
				undead.add(new Skeleton(undeadrooms));

			temp = x.nextInt(3);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new Assassin(humanrooms));
			
			temp = x.nextInt((1) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new Sorceress(humanrooms));

			temp = x.nextInt((2) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new Priestess(humanrooms));
			
			temp = x.nextInt((1) + 2);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new GuardHound(humanrooms));
			
			for(int i = 0; i < number; i++)
				humans.add(new Soldier(humanrooms));
			
			break;
		case 2:
			mobs.add(new Cassandra2());
			number = 50;
			
			temp = x.nextInt((2) + 4);
			number -= temp;
			for(int i = 0; i < temp; i++)
				avians.add(new Avian(avianrooms));

			temp = x.nextInt((1) + 1);
			number -= temp;
			for(int i = 0; i < temp; i++)
				undead.add(new Wraith(undeadrooms));
			
			temp = x.nextInt((3) + 3);
			number -= temp;
			for(int i = 0; i < temp; i++)
				undead.add(new Zombie(undeadrooms));
			
			temp = x.nextInt((2) + 4);
			number -= temp;
			for(int i = 0; i < temp; i++)
				undead.add(new Skeleton(undeadrooms));

			temp = x.nextInt((3) + 2);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new Assassin(humanrooms));
			
			temp = x.nextInt((2) + 2);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new Sorceress(humanrooms));

			temp = x.nextInt((3) + 3);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new Priestess(humanrooms));
			
			temp = x.nextInt((3) + 4);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new GuardHound(humanrooms));
			
			for(int i = 0; i < number; i++)
				humans.add(new Soldier(humanrooms));
			
			break;
		case 3:
			mobs.add(new Cassandra3());
			number = 75;
			
			temp = x.nextInt((3) + 5);
			number -= temp;
			for(int i = 0; i < temp; i++)
				avians.add(new Avian(avianrooms));

			temp = x.nextInt((2) + 2);
			number -= temp;
			for(int i = 0; i < temp; i++)
				undead.add(new Wraith(undeadrooms));
			
			temp = x.nextInt((3) + 5);
			number -= temp;
			for(int i = 0; i < temp; i++)
				undead.add(new Zombie(undeadrooms));
			
			temp = x.nextInt((3) + 6);
			number -= temp;
			for(int i = 0; i < temp; i++)
				undead.add(new Skeleton(undeadrooms));

			temp = x.nextInt((4) + 3);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new Assassin(humanrooms));
			
			temp = x.nextInt((3) + 4);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new Sorceress(humanrooms));

			temp = x.nextInt((3) + 5);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new Priestess(humanrooms));
			
			temp = x.nextInt((5) + 7);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new GuardHound(humanrooms));
			
			for(int i = 0; i < number; i++)
				humans.add(new Soldier(humanrooms));
			
			break;
		case 4:
			mobs.add(new Cassandra4());
			number = 100;
			
			temp = x.nextInt((5) + 6);
			number -= temp;
			for(int i = 0; i < temp; i++)
				avians.add(new Avian(avianrooms));

			temp = x.nextInt((4) + 2);
			number -= temp;
			for(int i = 0; i < temp; i++)
				undead.add(new Wraith(undeadrooms));
			
			temp = x.nextInt((4) + 6);
			number -= temp;
			for(int i = 0; i < temp; i++)
				undead.add(new Zombie(undeadrooms));
			
			temp = x.nextInt((4) + 8);
			number -= temp;
			for(int i = 0; i < temp; i++)
				undead.add(new Skeleton(undeadrooms));

			temp = x.nextInt((4) + 5);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new Assassin(humanrooms));
			
			temp = x.nextInt((4) + 6);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new Sorceress(humanrooms));

			temp = x.nextInt((4) + 7);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new Priestess(humanrooms));
			
			temp = x.nextInt((7) + 10);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new GuardHound(humanrooms));
			
			for(int i = 0; i < number; i++)
				humans.add(new Soldier(humanrooms));
			
			break;
		case 5:
			mobs.add(new Cassandra5());
			number = 125;
			
			temp = x.nextInt((6) + 8);
			number -= temp;
			for(int i = 0; i < temp; i++)
				avians.add(new Avian(avianrooms));

			temp = x.nextInt((5) + 5);
			number -= temp;
			for(int i = 0; i < temp; i++)
				undead.add(new Wraith(undeadrooms));
			
			temp = x.nextInt((5) + 8);
			number -= temp;
			for(int i = 0; i < temp; i++)
				undead.add(new Zombie(undeadrooms));
			
			temp = x.nextInt((6) + 10);
			number -= temp;
			for(int i = 0; i < temp; i++)
				undead.add(new Skeleton(undeadrooms));

			temp = x.nextInt((5) + 7);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new Assassin(humanrooms));
			
			temp = x.nextInt((5) + 8);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new Sorceress(humanrooms));

			temp = x.nextInt((6) + 8);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new Priestess(humanrooms));
			
			temp = x.nextInt((7) + 13);
			number -= temp;
			for(int i = 0; i < temp; i++)
				humans.add(new GuardHound(humanrooms));
			
			for(int i = 0; i < number; i++)
				humans.add(new Soldier(humanrooms));
			
			break;
		}
		
		mobs.get(0).setRoom(bossroom.get(0));
		
		for(int i = 0; i < avians.size(); i++)
		{
			Collections.shuffle(avianrooms);
			avians.get(i).setRoom(avianrooms.get(0));
		}
		
		for(int i = 0; i < undead.size(); i++)
		{
			Collections.shuffle(undeadrooms);
			undead.get(i).setRoom(undeadrooms.get(0));
		}
		
		for(int i = 0; i < humans.size(); i++)
		{
			Collections.shuffle(humanrooms);
			humans.get(i).setRoom(humanrooms.get(0));
		}
		
		for(Monster y:avians)
			mobs.add(y);
		
		for(Monster y:undead)
			mobs.add(y);
		
		for(Monster y:humans)
			mobs.add(y);
		
	}

	@Override
	public int getDungeonID(){
		return dID;
	}
	
	public ArrayList<Room> listBossRooms(){
		return bossroom;
	}
	
	public ArrayList<Room> listAvianRooms(){
		return avianrooms;
	}
	
	public ArrayList<Room> listUndeadRooms(){
		return undeadrooms;
	}
	
	public ArrayList<Room> listHumanRooms(){
		return humanrooms;
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
