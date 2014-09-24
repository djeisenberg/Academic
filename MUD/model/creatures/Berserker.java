package model.creatures;

import java.util.HashMap;

import model.items.heavyweapons.Maul;
import model.items.lightchest.LeatherJerkin;
import model.items.lightfeet.LeatherGreaves;
import model.items.lightlegs.LeatherLeggings;

import account.User;

public class Berserker extends Player{

	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Berserker(String name, User user)
	{
		super(name, user);
		
		this.strength = 30;
		this.agility = 15;
		this.stamina = 20;
		this.magic = 5;
		
		this.attack = 85;
		this.defense = 30;
		this.evasion = 5;
		this.magicDefense = 5;
		
		this.hp = 150;
		this.mp = 5;
		this.level = 1;
		this.attackModifier = this.baseAttackModifier;
		
		this.abilities = new HashMap<String, String>();
		this.abilities.put("Ber001", "Power Attack");
		
		Maul weapon = new Maul();
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
			this.strength += 5;
			this.agility += 1;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 5;
			this.evasion += 0;
			this.magicDefense += 0;
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
			this.evasion += 0;
			this.magicDefense += 0;
			this.hp += stamina;
			this.mp += (magic/2);
			this.abilities.put("Ber002", "Stunning Strike");
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
			this.evasion += 0;
			this.magicDefense += 0;
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
			this.abilities.put("Ber003", "Frenzy");
			break;
		case 8:
			this.strength += 5;
			this.agility += 1;
			this.stamina += 5;
			this.magic += 1;
			this.attack += 5;
			this.defense += 5;
			this.evasion += 0;
			this.magicDefense += 0;
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
			this.evasion += 0;
			this.magicDefense += 0;
			this.hp += stamina;
			this.mp += (magic/2);
			this.abilities.put("Ber004", "Whirlwind");
			break;
		}
		
	}

	@Override
	public String getJob() {
		return "Berserker";
	}
}
