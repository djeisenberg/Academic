package model.creatures.abomdungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.clothfeet.WizardsGreaves;
import model.items.clothlegs.WizardsLeggings;
import model.items.useables.GreaterEther;
import model.world.Room;

public class ObsidianGolem extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ObsidianGolem(ArrayList<Room> movablerooms)
	{
		this.name = "Obsidian Golem";
		
		this.strength = 100;
		this.agility = 25;
		this.stamina = 75;
		this.magic = 20;
		
		this.attack = 100;
		this.defense = 75;
		this.evasion = 10;
		this.magicDefense = 30;
		
		this.hp = 300;
		this.mp = 50;
		this.level = 9;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		
		this.mID = 22;
		movable = movablerooms;
		this.delay = this.delay - (10*this.agility);
	
		this.img = "Abomination/Obsidian Golem.png";
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
			this.treasure.add(new WizardsLeggings());
		else if(y <= 10)
			this.treasure.add(new WizardsGreaves());
		else if(y <= 25)
			this.treasure.add(new GreaterEther());

		
		return treasure;
	}

}
