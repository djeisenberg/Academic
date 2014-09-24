package model.creatures.abomdungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Boss;
import model.items.Item;
import model.items.heavyweapons.SkyHammer;
import model.items.largesword.EogSword;
import model.items.largesword.EternalSword;
import model.items.lightblades.CrystalDagger;
import model.items.rods.VoidRod;
import model.items.staves.HornwoodStaff;
import model.items.useables.GreaterEther;
import model.items.useables.GreaterPotion;
import model.items.useables.Rejuvination;

public class Lilith1 extends Boss{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Lilith1() {
		this.name = "Lilith";

		this.strength = 125;
		this.agility = 50;
		this.stamina = 80;
		this.magic = 125;

		this.attack = 130;
		this.defense = 90;
		this.evasion = 15;
		this.magicDefense = 80;

		this.hp = 750;
		this.mp = 300;
		this.level = 10;
		this.currenthp = this.hp;
		this.currentmp = this.mp;

		this.mID = 1025;
		
		this.immunities = immunities | (1 << 22);
		this.delay = this.delay - (10*this.agility);
		
		this.img = "Abomination/Lilith.png";
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item stealList(int sroll){
		
		if(sroll <= 10)
		{
			this.stolenflag = true;
			return new Rejuvination();
		}else if(sroll <= 30){
			this.stolenflag = true;
			return new GreaterPotion();
		}else if(sroll <= 50){
			this.stolenflag = true;
			return new GreaterEther();
		}else
			return null;
	}

	@Override
	public LinkedList<Item> Treasure() {

		Random x = new Random();
		
		ArrayList<String> bossloot = new ArrayList<String>();
		bossloot.add("w1");
		bossloot.add("w2");
		bossloot.add("w3");
		bossloot.add("w4");
		bossloot.add("w5");
		bossloot.add("w6");
		
		Collections.shuffle(bossloot);
		

		if(bossloot.get(0).equals("w1"))
			this.treasure.add(new EternalSword());
		else if(bossloot.get(0).equals("w2"))
			this.treasure.add(new EogSword());
		else if(bossloot.get(0).equals("w3"))
			this.treasure.add(new CrystalDagger());
		else if(bossloot.get(0).equals("w4"))
			this.treasure.add(new SkyHammer());
		else if(bossloot.get(0).equals("w5"))
			this.treasure.add(new HornwoodStaff());
		else if(bossloot.get(0).equals("w6"))
			this.treasure.add(new VoidRod());
		
		int y = x.nextInt((100) + 1);
		
		if(y <= 5)
			this.treasure.add(new Rejuvination());
		else if(y <= 20)
			this.treasure.add(new GreaterEther());
		else if(y <= 45)
			this.treasure.add(new GreaterPotion());
		
		
		return treasure;
	}


}
