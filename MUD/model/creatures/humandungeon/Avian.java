package model.creatures.humandungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.shield.GuardianShield;
import model.items.useables.Potion;
import model.world.Room;

public class Avian extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Avian(ArrayList<Room> avianrooms)
	{
		this.name = "Avian";
		
		this.strength = 75;
		this.agility = 30;
		this.stamina = 10;
		this.magic = 10;
		
		this.attack = 75;
		this.defense = 20;
		this.evasion = 20;
		this.magicDefense = 10;
		
		this.hp = 150;
		this.mp = 20;
		this.level = 5;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		
		this.mID = 9;
		movable = avianrooms;
		this.delay = this.delay - (10*this.agility);
	
		this.img = "Human Mod/Avian.png";
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
			this.treasure.add(new GuardianShield());
		else if(y <= 10)
			this.treasure.add(new Potion());
		
		return treasure;
	}

}
