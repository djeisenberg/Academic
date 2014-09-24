package model.creatures.humandungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.clothlegs.DarkLeggings;
import model.items.heavylegs.AdamantiteLeggings;
import model.items.lightlegs.ShadowLeggings;
import model.items.useables.GreaterPotion;
import model.items.useables.Potion;
import model.world.Room;

public class Soldier extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Soldier(ArrayList<Room> humanrooms)
	{
		this.name = "Knight";
		
		this.strength = 75;
		this.agility = 20;
		this.stamina = 50;
		this.magic = 10;
		
		this.attack = 100;
		this.defense = 60;
		this.evasion = 1;
		this.magicDefense = 10;
		
		this.hp = 200;
		this.mp = 20;
		this.level = 6;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		
		this.mID = 11;
		movable = humanrooms;
		this.delay = this.delay - (10*this.agility);

		this.img = "Human Mod/Knight.png";
	}

	@Override
	public void act() {

	}

	@Override
	public Item stealList(int sroll){
		
		if(sroll == 1)
		{
			this.stolenflag = true;
			return new GreaterPotion();
		}else if(sroll <= 30){
			this.stolenflag = true;
			return new Potion();
		}else
			return null;
	}


	@Override
	public LinkedList<Item> Treasure() {
		
		Random x = new Random();
		int y = x.nextInt((100) + 1);
		
		if(y <= 5)
			this.treasure.add(new AdamantiteLeggings());
		else if(y <= 10)
			this.treasure.add(new ShadowLeggings());
		else if(y <= 15)
			this.treasure.add(new DarkLeggings());
		else if(y <= 20)
			this.treasure.add(new GreaterPotion());
		else if(y <= 40)
			this.treasure.add(new Potion());
		
		return treasure;
	}

}
