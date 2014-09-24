package model.creatures.humandungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.heavychest.AdamantitePlate;
import model.items.heavychest.SidheMail;
import model.items.useables.Potion;
import model.world.Room;

public class Zombie extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Zombie(ArrayList<Room> undeadrooms)
	{
		this.name = "Zombie";
		
		this.strength = 80;
		this.agility = 5;
		this.stamina = 40;
		this.magic = 10;
		
		this.attack = 80;
		this.defense = 20;
		this.evasion = 1;
		this.magicDefense = 10;
		
		this.hp = 200;
		this.mp = 20;
		this.level = 6;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		
		this.mID = 16;
		movable = undeadrooms;
		this.delay = this.delay - (10*this.agility);

		this.img = "Human Mod/Zombie.png";
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
			this.treasure.add(new AdamantitePlate());
		else if(y <= 10)
			this.treasure.add(new SidheMail());
		else if(y <= 20)
			this.treasure.add(new Potion());
		
		return treasure;
	}

}
