package model.creatures.abomdungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.shield.TowerShield;
import model.items.useables.GreaterPotion;
import model.world.Room;

public class Terras extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Terras(ArrayList<Room> movablerooms)
	{
		this.name = "Terras";
		
		this.strength = 100;
		this.agility = 30;
		this.stamina = 60;
		this.magic = 15;
		
		this.attack = 100;
		this.defense = 75;
		this.evasion = 5;
		this.magicDefense = 20;
		
		this.hp = 250;
		this.mp = 30;
		this.level = 8;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		
		this.mID = 24;
		movable = movablerooms;
		this.delay = this.delay - (10*this.agility);
		
		this.img = "Abomination/Terras.png";
	}

	@Override
	public void act() {

	}

	@Override
	public Item stealList(int sroll) {

		if(sroll <= 10){
			this.stolenflag = true;
			return new GreaterPotion();
		}else
			return null;
	}


	@Override
	public LinkedList<Item> Treasure() {
		
		Random x = new Random();
		int y = x.nextInt((100) + 1);
		
		if(y <= 3)
			this.treasure.add(new TowerShield());
		else if(y <= 20)
			this.treasure.add(new GreaterPotion());
		
		return treasure;
	}

}
