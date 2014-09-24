package model.creatures.gobdungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Monster;
import model.items.Item;
import model.items.clothlegs.RuneLeggings;
import model.items.heavylegs.IronLeggings;
import model.items.heavylegs.SilverLeggings;
import model.items.lightlegs.HideLeggings;
import model.items.lightlegs.SuppleLeggings;
import model.items.useables.Ether;
import model.items.useables.Potion;
import model.world.Room;

public class GoblinLt extends Monster{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GoblinLt(ArrayList<Room> notbossrooms)
	{
		this.name = "Goblin Lieutenant";
		
		this.strength = 30;
		this.agility = 10;
		this.stamina = 15;
		this.magic = 5;
		
		this.attack = 55;
		this.defense = 30;
		this.evasion = 15;
		this.magicDefense = 5;
		
		this.hp = 50;
		this.mp = 10;
		this.level = 2;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		this.attackModifier = 2;
		this.gold = 20;
		
		this.mID = 2;
		movable = notbossrooms;
		this.delay = this.delay - (10*this.agility);
		
		this.img = "Goblin Dungeon Mod/Goblin Lieutenant.png";
	}

	@Override
	public void act() {

	}
	
	public Item stealList(int sroll){
		
		if(sroll <= 5){
			this.stolenflag = true;
			return new Ether();
		}else if(sroll <= 20){
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
			this.treasure.add(new IronLeggings());
		else if(y <= 6)
			this.treasure.add(new SilverLeggings());
		else if(y <= 9)
			this.treasure.add(new SuppleLeggings());
		else if(y <= 12)
			this.treasure.add(new HideLeggings());
		else if(y <= 15)
			this.treasure.add(new RuneLeggings());
		else if(y <= 20)
			this.treasure.add(new Ether());
		else if(y <= 50)
			this.treasure.add(new Potion());
		
		return this.treasure;
	}
}
