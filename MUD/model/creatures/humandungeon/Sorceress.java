package model.creatures.humandungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.clothfeet.DarkGreaves;
import model.items.heavyfeet.AdamantiteGreaves;
import model.items.lightfeet.ShadowGreaves;
import model.items.useables.Ether;
import model.items.useables.GreaterEther;
import model.items.useables.Rejuvination;
import model.world.Room;

public class Sorceress extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Sorceress(ArrayList<Room> humanrooms)
	{
		this.name = "Sorceress";
		
		this.strength = 50;
		this.agility = 25;
		this.stamina = 25;
		this.magic = 80;
		
		this.attack = 50;
		this.defense = 30;
		this.evasion = 15;
		this.magicDefense = 55;
		
		this.hp = 175;
		this.mp = 75;
		this.level = 7;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		
		this.mID = 14;
		movable = humanrooms;
		this.delay = this.delay - (10*this.agility);

		this.img = "Human Mod/Sorceress.png";
	}

	@Override
	public void act() {

	}
	
	@Override
	public Item stealList(int sroll){
		
		if(sroll <= 3)
		{
			this.stolenflag = true;
			return new GreaterEther();
		}else if(sroll <= 10){
			this.stolenflag = true;
			return new Rejuvination();
		}else if(sroll <= 25){
			this.stolenflag = true;
			return new Ether();
		}else
			return null;
	}



	@Override
	public LinkedList<Item> Treasure() {
		
		Random x = new Random();
		int y = x.nextInt((100) + 1);
		
		if(y <= 5)
			this.treasure.add(new AdamantiteGreaves());
		else if(y <= 10)
			this.treasure.add(new ShadowGreaves());
		else if(y <= 15)
			this.treasure.add(new DarkGreaves());
		else if(y <= 20)
			this.treasure.add(new GreaterEther());
		else if(y <= 30)
			this.treasure.add(new Rejuvination());
		else if(y <= 50)
			this.treasure.add(new Ether());
		
		return treasure;
	}

}
