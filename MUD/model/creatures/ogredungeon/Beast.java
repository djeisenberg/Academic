package model.creatures.ogredungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.shield.OgresShield;
import model.items.useables.Potion;
import model.world.Room;

public class Beast extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Beast(ArrayList<Room> movablerooms) {
		this.name = "Beast";

		this.strength = 60;
		this.agility = 10;
		this.stamina = 40;
		this.magic = 10;

		this.attack = 50;
		this.defense = 20;
		this.evasion = 15;
		this.magicDefense = 5;

		this.hp = 125;
		this.mp = 15;
		this.level = 4;
		this.currenthp = this.hp;
		this.currentmp = this.mp;

		this.mID = 7;
		movable = movablerooms;
		this.delay = this.delay - (10*this.agility);

		
		this.img = "Ogre Lair Mod/Beast.png";
	}

	@Override
	public void act() {

	}

	@Override
	public Item stealList(int sroll){
		return null;
	}

	@Override
	public LinkedList<Item> Treasure() {
		
		Random x = new Random();
		int y = x.nextInt((100) + 1);
		
		if(y <= 5)
			this.treasure.add(new OgresShield());
		else if(y <= 10)
			this.treasure.add(new Potion());
		
		return treasure;
	}

}