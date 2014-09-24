package model.creatures;

import java.util.HashMap;

import model.items.heavychest.ChainMail;
import model.items.heavyfeet.ChainGreaves;
import model.items.heavylegs.ChainLeggings;
import model.items.largesword.Saber;

import account.User;

public class MagicKnight extends Player{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MagicKnight(String name, User user)
	{
		super(name, user);
		
		this.strength = 20;
		this.agility = 15;
		this.stamina = 15;
		this.magic = 20;
		
		this.attack = 50;
		this.defense = 30;
		this.evasion = 20;
		this.magicDefense = 25;
		
		this.hp = 100;
		this.mp = 20;
		this.level = 1;
		this.attackModifier = this.baseAttackModifier;
		
		this.abilities = new HashMap<String, String>();
		this.abilities.put("Mkn001", "Fire");
		
		Saber weapon = new Saber();
		ChainMail chest = new ChainMail();
		ChainLeggings legs = new ChainLeggings();
		ChainGreaves feet = new ChainGreaves();
		this.inventory.add(weapon);
		this.inventory.add(chest);
		this.inventory.add(legs);
		this.inventory.add(feet);
		this.equip(chest);
		this.equip(legs);
		this.equip(feet);
		this.equip(weapon);
	}

	@Override
	public void LvlUp() {
		// TODO Auto-generated method stub
		
		switch(level)
		{
		case 2:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 3;
			this.magic += 3;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 1;
			this.magicDefense += 3;
			this.hp += stamina;
			this.mp += (magic/2);
			this.abilities.put("Mkn002", "Ice");
			break;
		case 3:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 3;
			this.magic += 3;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 1;
			this.magicDefense += 3;
			this.hp += stamina;
			this.mp += (magic/2);
			this.abilities.put("Mkn003", "Wind");
			break;
		case 4:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 3;
			this.magic += 3;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 1;
			this.magicDefense += 3;
			this.hp += stamina;
			this.mp += (magic/2);
			this.abilities.put("Mkn004", "Earth");
			break;
		case 5:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 3;
			this.magic += 3;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 1;
			this.magicDefense += 3;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 6:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 3;
			this.magic += 3;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 1;
			this.magicDefense += 3;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 7:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 3;
			this.magic += 3;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 1;
			this.magicDefense += 3;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 8:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 3;
			this.magic += 3;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 1;
			this.magicDefense += 3;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 9:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 3;
			this.magic += 3;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 1;
			this.magicDefense += 3;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 10:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 3;
			this.magic += 3;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 1;
			this.magicDefense += 3;
			this.hp += stamina;
			this.mp += (magic/2);
			this.abilities.put("Mkn005", "Holy");
			break;
		}
		
	}

	@Override
	public String getJob() {
		return "Magic Knight";
	}

}
