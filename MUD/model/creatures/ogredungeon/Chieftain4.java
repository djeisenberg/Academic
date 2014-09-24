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

public class Chieftain4 extends Boss{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Chieftain4() {
		this.name = "Ogre Cheiftain";

		this.strength = 75;
		this.agility = 25;
		this.stamina = 60;
		this.magic = 20;

		this.attack = 85;
		this.defense = 50;
		this.evasion = 20;
		this.magicDefense = 30;

		this.hp = 650;
		this.mp = 60;
		this.level = 5;
		this.currenthp = this.hp;
		this.currentmp = this.mp;

		this.mID = 1008;
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
		
		if(sroll <= 5)
		{
			this.stolenflag = true;
			return new Rejuvination();
		}else if(sroll <= 20){
			this.stolenflag = true;
			return new Ether();
		}else if(sroll <= 35){
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
		
		ArrayList<String> hold = new ArrayList<String>();
		
		hold.add(bossloot.get(0));
		int y = x.nextInt((100) + 1);
		if(y <= 50)
			hold.add(bossloot.get(1));
		
		for(String z:hold){
			if(z.equals("w1"))
				this.treasure.add(new BarrowSword());
			else if(z.equals("w2"))
				this.treasure.add(new MythrilSword());
			else if(z.equals("w3"))
				this.treasure.add(new DuskBlade());
			else if(z.equals("w4"))
				this.treasure.add(new OgresMaul());
			else if(z.equals("w5"))
				this.treasure.add(new RosewoodStaff());
			else if(z.equals("w6"))
				this.treasure.add(new DarkRod());
		}
		
		y = x.nextInt((100) + 1);
		
		if(y <= 10)
			this.treasure.add(new Rejuvination());
		else if(y <= 50)
			this.treasure.add(new Ether());
		else
			this.treasure.add(new Potion());
		
		
		return treasure;
	}

}
