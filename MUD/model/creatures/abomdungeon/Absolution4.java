package model.creatures.abomdungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Boss;
import model.items.Item;
import model.items.clothchest.WizardsRobes;
import model.items.heavychest.UnseelieMail;
import model.items.useables.GreaterEther;
import model.items.useables.GreaterPotion;
import model.items.useables.Rejuvination;

public class Absolution4 extends Boss{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Absolution4() {
		this.name = "Absolution";

		this.strength = 100;
		this.agility = 50;
		this.stamina = 85;
		this.magic = 145;

		this.attack = 100;
		this.defense = 85;
		this.evasion = 15;
		this.magicDefense = 110;

		this.hp = 1500;
		this.mp = 600;
		this.level = 10;
		this.currenthp = this.hp;
		this.currentmp = this.mp;

		this.mID = 1018;
		
		this.immunities = immunities | (1 << 22);
		this.delay = this.delay - (10*this.agility);
		
		this.img = "Abomination/Absolution.png";
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

		
		Collections.shuffle(bossloot);
		

		if(bossloot.get(0).equals("w1"))
			this.treasure.add(new UnseelieMail());
		else if(bossloot.get(0).equals("w2"))
			this.treasure.add(new WizardsRobes());
		else if(bossloot.get(0).equals("w3"))
			this.treasure.add(new GreaterEther());

		
		int y = x.nextInt((100) + 1);
		
		if(y <= 5)
			this.treasure.add(new Rejuvination());
		else if(y <= 15)
			this.treasure.add(new GreaterEther());
		else if(y <= 35)
			this.treasure.add(new GreaterPotion());
		
		
		return treasure;
	}

}
