package model.creatures.humandungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.lightchest.ChaosArmor;
import model.items.lightchest.ShadowArmor;
import model.items.useables.Potion;
import model.world.Room;

public class Skeleton extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Skeleton(ArrayList<Room> undeadrooms)
	{
		this.name = "Skeleton";
		
		this.strength = 75;
		this.agility = 20;
		this.stamina = 15;
		this.magic = 10;
		
		this.attack = 75;
		this.defense = 25;
		this.evasion = 10;
		this.magicDefense = 10;
		
		this.hp = 150;
		this.mp = 20;
		this.level = 5;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
	
		this.mID = 13;
		movable = undeadrooms;
		this.delay = this.delay - (10*this.agility);

		this.img = "Human Mod/Skeleton.png";
	}

	@Override
	public void act() {

	}
	
	@Override
	public Item stealList(int sroll) {

		if(sroll <= 5){
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
			this.treasure.add(new ShadowArmor());
		else if(y <= 10)
			this.treasure.add(new ChaosArmor());
		else if(y <= 20)
			this.treasure.add(new Potion());
		
		return treasure;
	}
}
