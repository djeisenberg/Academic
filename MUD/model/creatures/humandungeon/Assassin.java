package model.creatures.humandungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.heavylegs.SidheLeggings;
import model.items.lightlegs.ChaosLeggings;
import model.items.useables.GreaterPotion;
import model.items.useables.Potion;
import model.items.useables.Rejuvination;
import model.world.Room;

public class Assassin extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Assassin(ArrayList<Room> humanrooms)
	{
		this.name = "Assassin";
		
		this.strength = 100;
		this.agility = 30;
		this.stamina = 20;
		this.magic = 10;
		
		this.attack = 100;
		this.defense = 30;
		this.evasion = 25;
		this.magicDefense = 10;
		
		this.hp = 175;
		this.mp = 20;
		this.level = 7;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		
		this.mID = 8;
		movable = humanrooms;
		this.delay = this.delay - (10*this.agility);

		this.img = "Human Mod/Assassin.png";
	}

	@Override
	public void act() {

	}

	@Override
	public Item stealList(int sroll){
		
		if(sroll <= 3)
		{
			this.stolenflag = true;
			return new GreaterPotion();
		}else if(sroll <= 10){
			this.stolenflag = true;
			return new Rejuvination();
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
			this.treasure.add(new SidheLeggings());
		else if(y <= 10)
			this.treasure.add(new ChaosLeggings());
		else if(y <= 20)
			this.treasure.add(new GreaterPotion());
		else if(y <= 30)
			this.treasure.add(new Rejuvination());
		else if(y <= 50)
			this.treasure.add(new Potion());
		
		return treasure;
	}

}
