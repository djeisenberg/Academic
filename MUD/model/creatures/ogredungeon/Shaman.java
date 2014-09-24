package model.creatures.ogredungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.clothchest.LightRobes;
import model.items.heavychest.MythrilMail;
import model.items.heavychest.SteelPlate;
import model.items.lightchest.DisplacingArmor;
import model.items.lightchest.ReapersArmor;
import model.items.useables.Ether;
import model.items.useables.Potion;
import model.world.Room;

public class Shaman extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Shaman(ArrayList<Room> movablerooms) {
		this.name = "Shaman";

		this.strength = 40;
		this.agility = 10;
		this.stamina = 30;
		this.magic = 50;

		this.attack = 50;
		this.defense = 20;
		this.evasion = 15;
		this.magicDefense = 40;

		this.hp = 100;
		this.mp = 50;
		this.level = 4;
		this.currenthp = this.hp;
		this.currentmp = this.mp;

		this.mID = 5;
		movable = movablerooms;
		this.delay = this.delay - (10*this.agility);
		
		this.img = "Ogre Lair Mod/Shaman.png";
	}

	@Override
	public void act() {

	}

	@Override
	public Item stealList(int sroll){
		
		if(sroll <= 20){
			this.stolenflag = true;
			return new Ether();
		}else if(sroll <= 30){
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
			this.treasure.add(new SteelPlate());
		else if(y <= 6)
			this.treasure.add(new MythrilMail());
		else if(y <= 9)
			this.treasure.add(new DisplacingArmor());
		else if(y <= 12)
			this.treasure.add(new ReapersArmor());
		else if(y <= 15)
			this.treasure.add(new LightRobes());
		else if(y <= 30)
			this.treasure.add(new Ether());
		else if(y <= 50)
			this.treasure.add(new Potion());
		
		return this.treasure;
	}

}
