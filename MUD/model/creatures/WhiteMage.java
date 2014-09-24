package model.creatures;

import java.util.HashMap;

import model.items.clothchest.ClothArmor;
import model.items.clothfeet.ClothGreaves;
import model.items.clothlegs.ClothLeggings;
import model.items.staves.OakStaff;

import account.User;

public class WhiteMage extends Player{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WhiteMage(String name, User user)
	{
		super(name, user);
		
		this.strength = 10;
		this.agility = 15;
		this.stamina = 15;
		this.magic = 30;
		
		this.attack = 10;
		this.defense = 10;
		this.evasion = 45;
		this.magicDefense = 60;
		
		this.hp = 75;
		this.mp = 25;
		this.level = 1;
		this.attackModifier = this.baseAttackModifier;
		
		this.abilities = new HashMap<String, String>();
		this.abilities.put("Whm001", "Cure");
		this.abilities.put("Whm002", "Smite");
		
		OakStaff weapon = new OakStaff();
		ClothArmor chest = new ClothArmor();
		ClothLeggings legs = new ClothLeggings();
		ClothGreaves feet = new ClothGreaves();
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
			this.agility += 2;
			this.stamina += 1;
			this.magic += 5;
			this.attack += 0;
			this.defense += 1;
			this.evasion += 0;
			this.magicDefense += 5;
			this.hp += stamina;
			this.mp += (magic/2);
			this.abilities.put("Whm003", "Shielding Light");
			break;
		case 3:
			this.strength += 1;
			this.agility += 2;
			this.stamina += 1;
			this.magic += 5;
			this.attack += 1;
			this.defense += 1;
			this.evasion += 0;
			this.magicDefense += 5;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 4:
			this.strength += 1;
			this.agility += 2;
			this.stamina += 1;
			this.magic += 5;
			this.attack += 0;
			this.defense += 1;
			this.evasion += 0;
			this.magicDefense += 5;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 5:
			this.strength += 1;
			this.agility += 2;
			this.stamina += 1;
			this.magic += 5;
			this.attack += 1;
			this.defense += 1;
			this.evasion += 0;
			this.magicDefense += 5;
			this.hp += stamina;
			this.mp += (magic/2);
			this.abilities.put("Whm004", "Group Heal");
			break;
		case 6:
			this.strength += 1;
			this.agility += 2;
			this.stamina += 1;
			this.magic += 5;
			this.attack += 0;
			this.defense += 1;
			this.evasion += 0;
			this.magicDefense += 5;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 7:
			this.strength += 1;
			this.agility += 2;
			this.stamina += 1;
			this.magic += 5;
			this.attack += 1;
			this.defense += 1;
			this.evasion += 0;
			this.magicDefense += 5;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 8:
			this.strength += 1;
			this.agility += 2;
			this.stamina += 1;
			this.magic += 5;
			this.attack += 0;
			this.defense += 1;
			this.evasion += 0;
			this.magicDefense += 5;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 9:
			this.strength += 1;
			this.agility += 2;
			this.stamina += 1;
			this.magic += 5;
			this.attack += 1;
			this.defense += 1;
			this.evasion += 0;
			this.magicDefense += 5;
			this.hp += stamina;
			this.mp += (magic/2);
			break;
		case 10:
			this.strength += 1;
			this.agility += 2;
			this.stamina += 1;
			this.magic += 5;
			this.attack += 0;
			this.defense += 1;
			this.evasion += 0;
			this.magicDefense += 5;
			this.hp += stamina;
			this.mp += (magic/2);
			this.abilities.put("Whm005", "Greater Cure");
			this.abilities.put("Whm006", "Greater Smite");
			break;
		}
	}

	@Override
	public String getJob() {
		return "White Mage";
	}
	

}
