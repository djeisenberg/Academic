package model.creatures.abomdungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.lightfeet.RagingGreaves;
import model.items.lightlegs.RagingLeggings;
import model.items.useables.GreaterPotion;
import model.world.Room;

public class CorruptedGolem extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CorruptedGolem(ArrayList<Room> movablerooms)
	{
		this.name = "Corrupted Golem";
		
		this.strength = 80;
		this.agility = 25;
		this.stamina = 50;
		this.magic = 20;
		
		this.attack = 90;
		this.defense = 50;
		this.evasion = 10;
		this.magicDefense = 75;
		
		this.hp = 275;
		this.mp = 100;
		this.level = 9;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		
		this.mID = 20;
		movable = movablerooms;
		this.delay = this.delay - (10*this.agility);

		
		this.img = "Abomination/Corrupted Golem.png";
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
			this.treasure.add(new RagingLeggings());
		else if(y <= 10)
			this.treasure.add(new RagingGreaves());
		else if(y <= 30)
			this.treasure.add(new GreaterPotion());
		
		return treasure;
	}

}
