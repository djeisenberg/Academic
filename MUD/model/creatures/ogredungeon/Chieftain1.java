package model.creatures.ogredungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Boss;
import model.items.Item;
import model.items.heavyweapons.OgresMaul;
import model.items.largesword.BarrowSword;
import model.items.largesword.MythrilSword;
import model.items.lightblades.DuskBlade;
import model.items.rods.DarkRod;
import model.items.staves.RosewoodStaff;
import model.items.useables.Ether;
import model.items.useables.Potion;
import model.items.useables.Rejuvination;

public class Chieftain1 extends Boss{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Chieftain1() {
		this.name = "Ogre Cheiftain";

		this.strength = 75;
		this.agility = 20;
		this.stamina = 50;
		this.magic = 20;

		this.attack = 75;
		this.defense = 40;
		this.evasion = 15;
		this.magicDefense = 20;

		this.hp = 200;
		this.mp = 50;
		this.level = 5;
		this.currenthp = this.hp;
		this.currentmp = this.mp;

		this.mID = 1005;
		this.delay = this.delay - (10*this.agility);
		
		this.immunities = immunities | (1 << 22);
		
		this.img = "Ogre Lair Mod/Chieftain.png";
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item stealList(int sroll){
		
		if(sroll <= 3)
		{
			this.stolenflag = true;
			return new Rejuvination();
		}else if(sroll <= 15){
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
		
		ArrayList<String> bossloot = new ArrayList<String>();
		bossloot.add("w1");
		bossloot.add("w2");
		bossloot.add("w3");
		bossloot.add("w4");
		bossloot.add("w5");
		bossloot.add("w6");
		
		Collections.shuffle(bossloot);
		

		if(bossloot.get(0).equals("w1"))
			this.treasure.add(new BarrowSword());
		else if(bossloot.get(0).equals("w2"))
			this.treasure.add(new MythrilSword());
		else if(bossloot.get(0).equals("w3"))
			this.treasure.add(new DuskBlade());
		else if(bossloot.get(0).equals("w4"))
			this.treasure.add(new OgresMaul());
		else if(bossloot.get(0).equals("w5"))
			this.treasure.add(new RosewoodStaff());
		else if(bossloot.get(0).equals("w6"))
			this.treasure.add(new DarkRod());
		
		int y = x.nextInt((100) + 1);
		
		if(y <= 5)
			this.treasure.add(new Rejuvination());
		else if(y <= 15)
			this.treasure.add(new Ether());
		else if(y <= 35)
			this.treasure.add(new Potion());
		
		
		return treasure;
	}

}
