package model.creatures.finaldungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Boss;
import model.items.Item;
import model.items.clothchest.MabsRobe;
import model.items.clothchest.TitaniasRobe;
import model.items.heavychest.DanusMail;
import model.items.heavychest.NuadasPlate;
import model.items.lightchest.FiachasArmor;
import model.items.lightchest.LughsArmor;
import model.items.shield.Grimwall;
import model.items.useables.Rejuvination;

public class Astarte extends Boss{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Astarte() {
		this.name = "Astarte";

		this.strength = 150;
		this.agility = 100;
		this.stamina = 125;
		this.magic = 150;

		this.attack = 150;
		this.defense = 150;
		this.evasion = 20;
		this.magicDefense = 150;

		this.hp = 4000;
		this.mp = 900;
		this.level = 12;
		this.currenthp = this.hp;
		this.currentmp = this.mp;

		this.mID = 1031;
		
		this.immunities = immunities | (1 << 22);
		this.delay = this.delay - (10*this.agility);
		
		this.img = "Abomination/Astarte.png";
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item stealList(int sroll) {
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
		bossloot.add("w7");
		
		Collections.shuffle(bossloot);
		
		ArrayList<String> hold = new ArrayList<String>();

		hold.add(bossloot.get(0));
		hold.add(bossloot.get(1));
		
		for(String z:hold){
			if(z.equals("w1"))
				this.treasure.add(new NuadasPlate());
			else if(z.equals("w2"))
				this.treasure.add(new DanusMail());
			else if(z.equals("w3"))
				this.treasure.add(new FiachasArmor());
			else if(z.equals("w4"))
				this.treasure.add(new LughsArmor());
			else if(z.equals("w5"))
				this.treasure.add(new TitaniasRobe());
			else if(z.equals("w6"))
				this.treasure.add(new MabsRobe());
			else if(z.equals("w7"))
				this.treasure.add(new Grimwall());
		}
		
		int y = x.nextInt((5) + 1);
		
		for(int i = 0; i < y; i++)
			this.treasure.add(new Rejuvination());
		
		return treasure;
	}


}
