package model.creatures.gobdungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.shield.HeavyShield;
import model.items.useables.Potion;
import model.world.Room;

public class Warg extends Monster{
	
	private static final long serialVersionUID = 1L;
	public Warg(ArrayList<Room> notbossrooms) {
		this.name = "Warg";

		this.strength = 20;
		this.agility = 10;
		this.stamina = 15;
		this.magic = 5;

		this.attack = 50;
		this.defense = 15;
		this.evasion = 20;
		this.magicDefense = 5;

		this.hp = 30;
		this.mp = 5;
		this.level = 1;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		this.attackModifier = 2;

		this.mID = 3;
		movable = notbossrooms;
		this.delay = this.delay - (10*this.agility);
		
		this.img = "Goblin Dungeon Mod/Warg.png";
	}

	@Override
	public void act() {

	}

	public Item stealList(int sroll){
		return null;
	}
	
	@Override
	public LinkedList<Item> Treasure() {
		
		Random x = new Random();
		int y = x.nextInt((100) + 1);
		
		if(y <= 5)
			this.treasure.add(new HeavyShield());
		else if(y <= 10)
			this.treasure.add(new Potion());
		
		return treasure;
	}
}
