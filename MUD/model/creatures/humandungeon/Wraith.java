package model.creatures.humandungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.clothchest.DarkRobes;
import model.items.useables.Ether;
import model.items.useables.GreaterEther;
import model.world.Room;

public class Wraith extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Wraith(ArrayList<Room> undeadrooms)
	{
		this.name = "Wraith";
		
		this.strength = 60;
		this.agility = 30;
		this.stamina = 15;
		this.magic = 65;
		
		this.attack = 80;
		this.defense = 30;
		this.evasion = 20;
		this.magicDefense = 25;
		
		this.hp = 175;
		this.mp = 50;
		this.level = 7;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		
		this.mID = 15;
		movable = undeadrooms;
		this.delay = this.delay - (10*this.agility);
	
		this.img = "Human Mod/Wraith.png";
	}

	@Override
	public void act() {

	}

	@Override
	public Item stealList(int sroll) {

		if(sroll <= 5){
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
			this.treasure.add(new DarkRobes());
		else if(y <= 10)
			this.treasure.add(new GreaterEther());
		else if(y <= 20)
			this.treasure.add(new Ether());
		
		return treasure;
	}

}
