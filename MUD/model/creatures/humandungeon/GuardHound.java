package model.creatures.humandungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.shield.GuardianShield;
import model.items.useables.Potion;
import model.world.Room;

public class GuardHound extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GuardHound(ArrayList<Room> humanrooms)
	{
		this.name = "Guard Hound";
		
		this.strength = 75;
		this.agility = 20;
		this.stamina = 15;
		this.magic = 10;
		
		this.attack = 75;
		this.defense = 25;
		this.evasion = 15;
		this.magicDefense = 10;
		
		this.hp = 150;
		this.mp = 20;
		this.level = 5;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		
		this.mID = 10;
		movable = humanrooms;
		this.delay = this.delay - (10*this.agility);

		this.img = "Human Mod/Guard Hound.png";
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
		
		if(y <= 5)
			this.treasure.add(new GuardianShield());
		else if(y <= 10)
			this.treasure.add(new Potion());
		
		return treasure;
	}

}
