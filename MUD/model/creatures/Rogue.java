package model.creatures;

import java.util.HashMap;

import model.items.lightblades.Dagger;
import model.items.lightchest.LeatherJerkin;
import model.items.lightfeet.LeatherGreaves;
import model.items.lightlegs.LeatherLeggings;

import account.User;

public class Rogue extends Player{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Rogue(String name, User user)
	{
		super(name, user);
		
		this.strength = 15;
		this.agility = 30;
		this.stamina = 15;
		this.magic = 10;
		
		this.attack = 75;
		this.defense = 20;
		this.evasion = 25;
		this.magicDefense = 5;
		
		this.hp = 90;
		this.mp = 10;
		this.level = 1;
		this.attackModifier = this.baseAttackModifier;
		
		this.abilities = new HashMap<String, String>();
		this.abilities.put("Rog001", "Steal");
		
		Dagger weapon = new Dagger();
		LeatherJerkin chest = new LeatherJerkin();
		LeatherLeggings legs = new LeatherLeggings();
		LeatherGreaves feet = new LeatherGreaves();
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
			this.strength += 1;
			this.agility += 5;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 3;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 3:
			this.strength += 1;
			this.agility += 5;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 3;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 4:
			this.strength += 1;
			this.agility += 5;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 3;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			this.abilities.put("Rog002", "Backstab");
			break;
		case 5:
			this.strength += 1;
			this.agility += 5;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 3;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 6:
			this.strength += 1;
			this.agility += 5;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 3;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 7:
			this.strength += 1;
			this.agility += 5;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 3;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			this.abilities.put("Rog003", "Poison");
			break;
		case 8:
			this.strength += 1;
			this.agility += 5;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 3;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 9:
			this.strength += 1;
			this.agility += 5;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 3;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 10:
			this.strength += 1;
			this.agility += 5;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 3;
			this.evasion += 3;
			this.magicDefense += 1;
			this.hp += stamina;
			this.mp += (magic/2);
			this.abilities.put("Rog004", "Vanish");
			break;
		}
	}

	@Override
	public String getJob() {
		return "Rogue";
	}

}
