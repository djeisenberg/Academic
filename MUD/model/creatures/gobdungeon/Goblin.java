package model.creatures.gobdungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.clothfeet.RuneGreaves;
import model.items.heavyfeet.IronGreaves;
import model.items.heavyfeet.SilverGreaves;
import model.items.lightfeet.HideGreaves;
import model.items.lightfeet.SuppleGreaves;
import model.items.useables.Ether;
import model.items.useables.Potion;
import model.world.Room;

public class Goblin extends Monster {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Goblin(ArrayList<Room> notbossrooms) {
		this.name = "Goblin";

		this.strength = 20;
		this.agility = 10;
		this.stamina = 10;
		this.magic = 5;

		this.attack = 50;
		this.defense = 20;
		this.evasion = 15;
		this.magicDefense = 5;

		this.hp = 25;
		this.mp = 5;
		this.level = 1;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		this.attackModifier = 2;
		this.gold = 10;

		this.mID = 1;
		movable = notbossrooms;
		this.delay = this.delay - (10*this.agility);
				
		this.img = "Goblin Dungeon Mod/Goblin.png";
	}

	@Override
	public void act() {

	}
	
	@Override
	public Item stealList(int sroll){
		
		if(sroll <= 5){
			this.stolenflag = true;
			return new Ether();
		}else if(sroll <= 15){
			this.stolenflag = true;
			return new Potion();
		}else
			return null;
		
	}

	@Override
	public LinkedList<Item> Treasure() {
		
		Random x = new Random();
		int y = x.nextInt((100) + 1);
		
		if(y <= 3)
			this.treasure.add(new IronGreaves());
		else if(y <= 6)
			this.treasure.add(new SilverGreaves());
		else if(y <= 9)
			this.treasure.add(new SuppleGreaves());
		else if(y <= 12)
			this.treasure.add(new HideGreaves());
		else if(y <= 15)
			this.treasure.add(new RuneGreaves());
		else if(y <= 20)
			this.treasure.add(new Ether());
		else if(y <= 35)
			this.treasure.add(new Potion());
		
		return this.treasure;
	}

}
