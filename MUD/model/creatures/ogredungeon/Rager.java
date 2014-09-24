package model.creatures.ogredungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.clothfeet.LightGreaves;
import model.items.heavyfeet.MythrilGreaves;
import model.items.heavyfeet.SteelGreaves;
import model.items.lightfeet.DisplacingGreaves;
import model.items.lightfeet.ReapersGreaves;
import model.world.Room;

public class Rager extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Rager(ArrayList<Room> movablerooms) {
		this.name = "Ogre Berserker";

		this.strength = 75;
		this.agility = 10;
		this.stamina = 30;
		this.magic = 10;

		this.attack = 50;
		this.defense = 5;
		this.evasion = 1;
		this.magicDefense = 5;

		this.hp = 125;
		this.mp = 15;
		this.level = 5;
		this.currenthp = this.hp;
		this.currentmp = this.mp;

		this.mID = 6;
		movable = movablerooms;
		this.delay = this.delay - (10*this.agility);

		
		this.immunities = immunities | (1 << 20);
		
		this.img = "Ogre Lair Mod/Berserker.png";
	}

	@Override
	public void act() {

	}

	@Override
	public Item stealList(int sroll) {
		return null;
	}

	@Override
	public LinkedList<Item> Treasure() {
		
		Random x = new Random();
		int y = x.nextInt((100) + 1);
		
		if(y <= 3)
			this.treasure.add(new SteelGreaves());
		else if(y <= 6)
			this.treasure.add(new MythrilGreaves());
		else if(y <= 9)
			this.treasure.add(new ReapersGreaves());
		else if(y <= 12)
			this.treasure.add(new DisplacingGreaves());
		else if(y <= 15)
			this.treasure.add(new LightGreaves());
		
		return this.treasure;
	}

}
