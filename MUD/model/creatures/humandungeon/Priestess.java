package model.creatures.humandungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.heavyfeet.SidheGreaves;
import model.items.lightfeet.ChaosGreaves;
import model.items.useables.Ether;
import model.items.useables.GreaterEther;
import model.world.Room;

public class Priestess extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Priestess(ArrayList<Room> humanrooms)
	{
		this.name = "Priestess";
		
		this.strength = 50;
		this.agility = 25;
		this.stamina = 20;
		this.magic = 75;
		
		this.attack = 50;
		this.defense = 25;
		this.evasion = 15;
		this.magicDefense = 50;
		
		this.hp = 150;
		this.mp = 75;
		this.level = 6;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		
		this.mID = 12;
		movable = humanrooms;
		this.delay = this.delay - (10*this.agility);

		this.img = "Human Mod/Priestess.png";
	}

	@Override
	public void act() {

	}

	@Override
	public Item stealList(int sroll){
		
		if(sroll == 1)
		{
			this.stolenflag = true;
			return new GreaterEther();
		}else if(sroll <= 20){
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
			this.treasure.add(new SidheGreaves());
		else if(y <= 10)
			this.treasure.add(new ChaosGreaves());
		else if(y <= 15)
			this.treasure.add(new GreaterEther());
		else if(y <= 25)
			this.treasure.add(new Ether());
		
		return treasure;
	}

}
