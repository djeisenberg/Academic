package model.creatures.ogredungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.clothlegs.LightLeggings;
import model.items.heavylegs.MythrilLeggings;
import model.items.heavylegs.SteelLeggings;
import model.items.lightlegs.DisplacingLeggings;
import model.items.lightlegs.ReapersLeggings;
import model.items.useables.Ether;
import model.items.useables.Potion;
import model.world.Room;

public class Ogre extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Ogre(ArrayList<Room> movablerooms) {
		this.name = "Ogre";

		this.strength = 50;
		this.agility = 10;
		this.stamina = 30;
		this.magic = 10;

		this.attack = 60;
		this.defense = 20;
		this.evasion = 15;
		this.magicDefense = 5;

		this.hp = 100;
		this.mp = 15;
		this.level = 3;
		this.currenthp = this.hp;
		this.currentmp = this.mp;

		this.mID = 4;
		movable = movablerooms;
		this.delay = this.delay - (10*this.agility);

		
		this.img = "Ogre Lair Mod/Ogre.png";
	}

	@Override
	public void act() {

	}

	@Override
	public Item stealList(int sroll){
		
		if(sroll <= 10){
			this.stolenflag = true;
			return new Ether();
		}else if(sroll <= 25){
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
			this.treasure.add(new SteelLeggings());
		else if(y <= 6)
			this.treasure.add(new MythrilLeggings());
		else if(y <= 9)
			this.treasure.add(new DisplacingLeggings());
		else if(y <= 12)
			this.treasure.add(new ReapersLeggings());
		else if(y <= 15)
			this.treasure.add(new LightLeggings());
		else if(y <= 20)
			this.treasure.add(new Ether());
		else if(y <= 50)
			this.treasure.add(new Potion());
		
		return this.treasure;
	}

}
