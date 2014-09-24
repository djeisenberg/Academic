package model.creatures.finaldungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Boss;
import model.items.Item;
import model.items.heavyweapons.Starfall;
import model.items.largesword.Ascalon;
import model.items.largesword.Sorcere;
import model.items.lightblades.Sorrow;
import model.items.rods.Ejus;
import model.items.staves.Acacia;
import model.items.useables.Rejuvination;

public class Andariel extends Boss{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Andariel() {
		this.name = "Andariel";

		this.strength = 175;
		this.agility = 100;
		this.stamina = 150;
		this.magic = 175;

		this.attack = 150;
		this.defense = 150;
		this.evasion = 20;
		this.magicDefense = 150;

		this.hp = 5000;
		this.mp = 1000;
		this.level = 13;
		this.currenthp = this.hp;
		this.currentmp = this.mp;

		this.mID = 1030;
		
		this.immunities = immunities | (1 << 22);
		this.delay = this.delay - (10*this.agility);
	
		this.img = "Abomination/Andariel.png";
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
		
		Collections.shuffle(bossloot);
		
		ArrayList<String> hold = new ArrayList<String>();

		hold.add(bossloot.get(0));
		hold.add(bossloot.get(1));
		
		for(String z:hold){
			if(z.equals("w1"))
				this.treasure.add(new Ascalon());
			else if(z.equals("w2"))
				this.treasure.add(new Sorcere());
			else if(z.equals("w3"))
				this.treasure.add(new Sorrow());
			else if(z.equals("w4"))
				this.treasure.add(new Starfall());
			else if(z.equals("w5"))
				this.treasure.add(new Acacia());
			else if(z.equals("w6"))
				this.treasure.add(new Ejus());
		}
		
		int y = x.nextInt((5) + 1);
		
		for(int i = 0; i < y; i++)
			this.treasure.add(new Rejuvination());
		
		return treasure;
	}



}
