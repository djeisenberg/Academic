package model.creatures.abomdungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.heavyfeet.DragonGreaves;
import model.items.heavylegs.DragonLeggings;
import model.items.useables.GreaterEther;
import model.items.useables.GreaterPotion;
import model.world.Room;

public class Belphagor extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Belphagor(ArrayList<Room> movablerooms)
	{
		this.name = "Belphagor";
		
		this.strength = 115;
		this.agility = 25;
		this.stamina = 50;
		this.magic = 20;
		
		this.attack = 110;
		this.defense = 50;
		this.evasion = 15;
		this.magicDefense = 25;
		
		this.hp = 350;
		this.mp = 50;
		this.level = 10;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		
		this.mID = 19;
		movable = movablerooms;
		this.delay = this.delay - (10*this.agility);
		
		this.img = "Abomination/Belphagor.png";
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
			this.treasure.add(new DragonLeggings());
		else if(y <= 10)
			this.treasure.add(new DragonGreaves());
		else if(y <= 20)
			this.treasure.add(new GreaterEther());
		else if(y <= 40)
			this.treasure.add(new GreaterPotion());
		
		return treasure;
	}

}
