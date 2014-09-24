package model.creatures.gobdungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import model.creatures.Boss;
import model.items.Item;
import model.items.clothchest.RuneArmor;
import model.items.heavychest.IronPlate;
import model.items.heavychest.SilverMail;
import model.items.heavyweapons.ReinforcedMaul;
import model.items.largesword.GoblinSword;
import model.items.largesword.SilverSword;
import model.items.lightblades.GoblinKnife;
import model.items.lightchest.HideArmor;
import model.items.lightchest.SuppleArmor;
import model.items.rods.CrystalRod;
import model.items.staves.YewStaff;
import model.items.useables.Ether;
import model.items.useables.Potion;
import model.items.useables.Rejuvination;

public class GoblinKing2 extends Boss{
	
	private static final long serialVersionUID = 1L;

	public GoblinKing2() {
		this.name = "Goblin King";

		this.strength = 35;
		this.agility = 20;
		this.stamina = 20;
		this.magic = 10;

		this.attack = 60;
		this.defense = 30;
		this.evasion = 20;
		this.magicDefense = 15;

		this.hp = 200;
		this.mp = 15;
		this.level = 3;
		this.currenthp = this.hp;
		this.currentmp = this.mp;
		this.attackModifier = 2;
		this.gold = 200;

		this.mID = 1001;
		
		this.immunities = immunities | (1 << 22);
		this.delay = this.delay - (10*this.agility);
		
		this.img = "Goblin Dungeon Mod/Goblin King.png";
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}

	public Item stealList(int sroll){
		
		if(sroll == 1)
		{
			this.stolenflag = true;
			return new Rejuvination();
		}else if(sroll <= 10){
			this.stolenflag = true;
			return new Ether();
		}else if(sroll <= 25){
			this.stolenflag = true;
			return new Potion();
		}else
			return null;
		
	}
	
	@Override
	public LinkedList<Item> Treasure() {

		Random x = new Random();
		
		ArrayList<String> bossloot = new ArrayList<String>();
		bossloot.add("h1");
		bossloot.add("h2");
		bossloot.add("l1");
		bossloot.add("l2");
		bossloot.add("c1");
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
			if(z.equals("h1"))
				this.treasure.add(new IronPlate());
			else if(z.equals("h2"))
				this.treasure.add(new SilverMail());
			else if(z.equals("l1"))
				this.treasure.add(new SuppleArmor());
			else if(z.equals("l2"))
				this.treasure.add(new HideArmor());
			else if(z.equals("c1"))
				this.treasure.add(new RuneArmor());
			else if(z.equals("w1"))
				this.treasure.add(new GoblinSword());
			else if(z.equals("w2"))
				this.treasure.add(new SilverSword());
			else if(z.equals("w3"))
				this.treasure.add(new GoblinKnife());
			else if(z.equals("w4"))
				this.treasure.add(new ReinforcedMaul());
			else if(z.equals("w5"))
				this.treasure.add(new YewStaff());
			else if(z.equals("w6"))
				this.treasure.add(new CrystalRod());
		}
		
		y = x.nextInt((100) + 1);
		
		if(y <= 5)
			this.treasure.add(new Rejuvination());
		else if(y <= 15)
			this.treasure.add(new Ether());
		else if(y <= 35)
			this.treasure.add(new Potion());
		
		
		return treasure;
	}
}
