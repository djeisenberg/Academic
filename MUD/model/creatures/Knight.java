package model.creatures;

import java.util.HashMap;

import model.items.heavychest.ChainMail;
import model.items.heavyfeet.ChainGreaves;
import model.items.heavylegs.ChainLeggings;
import model.items.largesword.LongSword;
import model.items.shield.Shield;

import account.User;

public class Knight extends Player{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Knight(String name, User user)
	{
		super(name, user);
		
		this.strength = 25;
		this.agility = 10;
		this.stamina = 30;
		this.magic = 5;
		
		this.attack = 60;
		this.defense = 55;
		this.evasion = 5;
		this.magicDefense = 5;
		
		this.hp = 125;
		this.mp = 5;
		this.level = 1;
		this.attackModifier = this.baseAttackModifier;
		
		this.abilities = new HashMap<String, String>();
		
		LongSword weapon = new LongSword();
		Shield shield = new Shield();
		ChainMail chest = new ChainMail();
		ChainLeggings legs = new ChainLeggings();
		ChainGreaves feet = new ChainGreaves();
		this.inventory.add(weapon);
		this.inventory.add(shield);
		this.inventory.add(chest);
		this.inventory.add(legs);
		this.inventory.add(feet);
		this.equip(chest);
		this.equip(legs);
		this.equip(feet);
		this.equip(weapon);
		this.equip(shield);
		
	}
	
	@Override
	public void LvlUp()
	{
		switch(level)
		{
		case 2:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 5;
			this.evasion += 1;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 3:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 5;
			this.evasion += 1;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 4:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 5;
			this.evasion += 1;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			this.abilities.put("Kni001", "Shield Bash");
			break;
		case 5:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 5;
			this.evasion += 1;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 6:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 5;
			this.evasion += 1;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 7:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 5;
			this.evasion += 1;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			this.abilities.put("Kni002", "Cripple Defense");
			break;
		case 8:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 5;
			this.evasion += 1;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 9:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 5;
			this.evasion += 1;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 10:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 5;
			this.evasion += 1;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			this.defense = (int) (defense * 1.5);
			break;
		
		}
	}

	@Override
	public String getJob() {
		return "Knight";
	}
}
