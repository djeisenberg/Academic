package model.creatures;

import java.util.HashMap;

import model.items.clothchest.ClothArmor;
import model.items.clothfeet.ClothGreaves;
import model.items.clothlegs.ClothLeggings;
import model.items.rods.MetalRod;

import account.User;

public class BlackMage extends Player{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BlackMage(String name, User user)
	{
		super(name, user);
		
		this.strength = 10;
		this.agility = 20;
		this.stamina = 10;
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
		this.abilities.put("Blm001", "Fire Ball");
		
		MetalRod weapon = new MetalRod();
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
			this.abilities.put("Blm002", "Ice Lance");
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
			this.abilities.put("Blm003", "Wind Cutter");
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
			this.abilities.put("Blm004", "Stone Rain");
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
			this.abilities.put("Blm005", "Slug");
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
			this.abilities.put("Blm006", "Fire Ball 2");
			this.abilities.put("Blm007", "Ice Lance 2");
			this.abilities.put("Blm008", "Wind Cutter 2");
			this.abilities.put("Blm009", "Stone Rain 2");
			break;
		}
		
	}

	@Override
	public String getJob() {
		return "Black Mage";
	}

}
