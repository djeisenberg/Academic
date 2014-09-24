package model.creatures.abomdungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.heavyfeet.UnseelieGreaves;
import model.items.heavylegs.UnseelieLeggings;
import model.items.useables.GreaterPotion;
import model.world.Room;

public class QuicksilverGolem extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public QuicksilverGolem(ArrayList<Room> movablerooms)
	{
		this.name = "Quicksilver Golem";
		
		this.strength = 100;
		this.agility = 75;
		this.stamina = 50;
		this.magic = 20;
		
		this.attack = 100;
		this.defense = 50;
		this.evasion = 20;
		this.magicDefense = 30;
		
		this.hp = 250;
		this.mp = 50;
		this.level = 9;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		
		this.mID = 23;
		movable = movablerooms;
		this.delay = this.delay - (10*this.agility);
		
		this.img = "Abomination/Quicksilver Golem.png";
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
			this.treasure.add(new UnseelieLeggings());
		else if(y <= 10)
			this.treasure.add(new UnseelieGreaves());
		else if(y <= 30)
			this.treasure.add(new GreaterPotion());
		
		return treasure;
	}
}
