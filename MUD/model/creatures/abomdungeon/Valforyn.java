package model.creatures.abomdungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.lightfeet.NightGreaves;
import model.items.lightlegs.NightLeggings;
import model.items.useables.GreaterEther;
import model.items.useables.GreaterPotion;
import model.world.Room;

public class Valforyn extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Valforyn(ArrayList<Room> movablerooms)
	{
		this.name = "Valforyn";
		
		this.strength = 110;
		this.agility = 50;
		this.stamina = 50;
		this.magic = 25;
		
		this.attack = 115;
		this.defense = 50;
		this.evasion = 20;
		this.magicDefense = 25;
		
		this.hp = 325;
		this.mp = 50;
		this.level = 10;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		
		this.mID = 25;
		movable = movablerooms;
		this.delay = this.delay - (10*this.agility);
		
		this.img = "Abomination/Valforyn.png";
	}

	@Override
	public void act() {

	}

	@Override
	public Item stealList(int sroll) {

		if(sroll <= 10){
			this.stolenflag = true;
			return new GreaterPotion();
		}else if(sroll <= 20){
			this.stolenflag = true;
			return new GreaterEther();
		}else
			return null;
	}

	@Override
	public LinkedList<Item> Treasure() {
		
		Random x = new Random();
		int y = x.nextInt((100) + 1);
		
		if(y <= 5)
			this.treasure.add(new NightLeggings());
		else if(y <= 10)
			this.treasure.add(new NightGreaves());
		else if(y <= 20)
			this.treasure.add(new GreaterEther());
		else if(y <= 40)
			this.treasure.add(new GreaterPotion());
		
		return treasure;
	}

}
