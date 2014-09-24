package model.creatures.finaldungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Boss;
import model.items.Item;
import model.items.clothlegs.TitaniasLeggings;
import model.items.heavylegs.NuadasLeggings;
import model.items.lightlegs.FiachasLeggings;
import model.items.useables.Rejuvination;

public class Caliadne extends Boss{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Caliadne() {
		this.name = "Caliadne";

		this.strength = 100;
		this.agility = 125;
		this.stamina = 125;
		this.magic = 150;

		this.attack = 150;
		this.defense = 125;
		this.evasion = 15;
		this.magicDefense = 125;

		this.hp = 2500;
		this.mp = 800;
		this.level = 11;
		this.currenthp = this.hp;
		this.currentmp = this.mp;

		this.mID = 1033;
		
		this.immunities = immunities | (1 << 22);
		this.delay = this.delay - (10*this.agility);
		
		this.img = "Abomination/Caliadne.png";
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

		
		Collections.shuffle(bossloot);
		

		if(bossloot.get(0).equals("w1"))
			this.treasure.add(new NuadasLeggings());
		else if(bossloot.get(0).equals("w2"))
			this.treasure.add(new FiachasLeggings());
		else if(bossloot.get(0).equals("w3"))
			this.treasure.add(new TitaniasLeggings());

		
		int y = x.nextInt((5) + 1);
		
		for(int i = 0; i < y; i++)
			this.treasure.add(new Rejuvination());
		
		
		return treasure;
	}

}
