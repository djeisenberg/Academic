package model.creatures.abomdungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Boss;
import model.items.Item;
import model.items.heavychest.DragonPlate;
import model.items.lightchest.NightArmor;
import model.items.lightchest.RagingArmor;
import model.items.useables.GreaterEther;
import model.items.useables.GreaterPotion;
import model.items.useables.Rejuvination;

public class Penance1 extends Boss{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Penance1() {
		this.name = "Penance";

		this.strength = 120;
		this.agility = 45;
		this.stamina = 80;
		this.magic = 80;

		this.attack = 125;
		this.defense = 80;
		this.evasion = 10;
		this.magicDefense = 60;

		this.hp = 600;
		this.mp = 300;
		this.level = 10;
		this.currenthp = this.hp;
		this.currentmp = this.mp;

		this.mID = 1020;
		
		this.immunities = immunities | (1 << 22);
		this.delay = this.delay - (10*this.agility);
		
		this.img = "Abomination/Penance.png";
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
			this.treasure.add(new DragonPlate());
		else if(bossloot.get(0).equals("w2"))
			this.treasure.add(new NightArmor());
		else if(bossloot.get(0).equals("w3"))
			this.treasure.add(new RagingArmor());

		
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
