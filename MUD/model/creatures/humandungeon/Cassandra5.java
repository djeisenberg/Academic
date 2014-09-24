package model.creatures.humandungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Boss;
import model.items.Item;
import model.items.heavyweapons.TitansMaul;
import model.items.largesword.DefenderSword;
import model.items.largesword.LaenSword;
import model.items.lightblades.ShadowKnife;
import model.items.rods.NightRod;
import model.items.staves.RedwoodStaff;
import model.items.useables.GreaterEther;
import model.items.useables.GreaterPotion;
import model.items.useables.Rejuvination;

public class Cassandra5 extends Boss{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Cassandra5() {
		this.name = "Cassandra";

		this.strength = 85;
		this.agility = 50;
		this.stamina = 80;
		this.magic = 120;

		this.attack = 100;
		this.defense = 70;
		this.evasion = 20;
		this.magicDefense = 90;

		this.hp = 1400;
		this.mp = 600;
		this.level = 7;
		this.currenthp = this.hp;
		this.currentmp = this.mp;

		this.mID = 1014;
		
		this.immunities = immunities | (1 << 22);
		this.delay = this.delay - (10*this.agility);
	
		this.img = "Human Mod/Cassandra.png";
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
		}else if(sroll <= 20){
			this.stolenflag = true;
			return new GreaterEther();
		}else if(sroll <= 30){
			this.stolenflag = true;
			return new GreaterPotion();
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
		y = x.nextInt((100) + 1);
		if(y <= 50)
			hold.add(bossloot.get(2));
		
		for(String z:hold){
			if(z.equals("w1"))
				this.treasure.add(new DefenderSword());
			else if(z.equals("w2"))
				this.treasure.add(new LaenSword());
			else if(z.equals("w3"))
				this.treasure.add(new ShadowKnife());
			else if(z.equals("w4"))
				this.treasure.add(new TitansMaul());
			else if(z.equals("w5"))
				this.treasure.add(new RedwoodStaff());
			else if(z.equals("w6"))
				this.treasure.add(new NightRod());
		}
		
		y = x.nextInt((100) + 1);
		
		if(y <= 10)
			this.treasure.add(new Rejuvination());
		else if(y <= 25)
			this.treasure.add(new GreaterEther());
		else
			this.treasure.add(new GreaterPotion());
		
		
		return treasure;
	}

}
