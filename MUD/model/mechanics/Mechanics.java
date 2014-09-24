package model.mechanics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import model.creatures.Creature;
import model.creatures.Player;
import model.creatures.Rogue;


/**
 * Mechanics class for the mud.  This class contains static methods that are used by the mud to determine attacks, damage,
 * resistances, leveling up, and xp calculations
 * 
 * @author Steven Chandler
 *
 */
public class Mechanics {
	
	private static HashMap<Integer, String> resistTable;
	private static HashMap<Integer, String> abilityTypeTable;
	public static HashMap<Integer, Integer> xpTable;


	
	
	public Mechanics()
	{
		resistTable = new HashMap<Integer, String>();
		abilityTypeTable = new HashMap<Integer, String>();
		xpTable = new HashMap<Integer, Integer>();

		
		
		
		setResistTable();
		setAbilityTypeTable();
		setXPTable();
		
	}

	
	/**
	 * This method loads the resistance hash table with the flags for the mob resistances
	 * Values 0-4 are weakness (double damage)
	 * Values 5-9 are resistances (half damage)
	 * Values 10-14 are absorbs (heals for the damage amount)
	 * Values 14-22 are immunities (no damage for elemental spells and immune to listed effects
	 * 
	 * @author Steven Chandler
	 */
	private final void setResistTable()
	{
		//Weakness flag
		resistTable.put(0, "Fire");
		resistTable.put(1, "Water");
		resistTable.put(2, "Air");
		resistTable.put(3, "Earth");
		resistTable.put(4, "Holy");
		
		//Resist flag
		resistTable.put(5, "Fire");
		resistTable.put(6, "Water");
		resistTable.put(7, "Air");
		resistTable.put(8, "Earth");
		resistTable.put(9, "Holy");
		
		//Absorb flag
		resistTable.put(10, "Fire");
		resistTable.put(11, "Water");
		resistTable.put(12, "Air");
		resistTable.put(13, "Earth");
		resistTable.put(14, "Holy");	
		
		//Immune flag
		resistTable.put(15, "Fire");
		resistTable.put(16, "Water");
		resistTable.put(17, "Air");
		resistTable.put(18, "Earth");
		resistTable.put(19, "Holy");			
		resistTable.put(20, "Stun");
		resistTable.put(21, "Poison");
		resistTable.put(22, "Slug");
		
	}

	
	/**
	 * This method loads the ability type table, used to determine if attacks are successful against mobs or not
	 * 
	 * @author Steven Chandler
	 */
	private final void setAbilityTypeTable()
	{
		abilityTypeTable.put(0, "Fire");
		abilityTypeTable.put(1, "Water");
		abilityTypeTable.put(2, "Air");
		abilityTypeTable.put(3, "Earth");
		abilityTypeTable.put(4, "Holy");
		abilityTypeTable.put(5, "Stun");
		abilityTypeTable.put(6, "Poison");
		abilityTypeTable.put(7, "Slug");
		
	}

	
	/**
	 * This method loads the xp table, used to determine if a character has leveled
	 * 
	 * @author Steven Chandler
	 */	
	private final void setXPTable()
	{
		xpTable.put(0, 0);
		xpTable.put(1, 1000);
		xpTable.put(2, 3000);
		xpTable.put(3, 6000);
		xpTable.put(4, 10000);
		xpTable.put(5, 15000);
		xpTable.put(6, 20000);
		xpTable.put(7, 26000);
		xpTable.put(8, 33000);
		xpTable.put(9, 40000);
		
	}
	
	
	/**
	 * This method determines which player will be targeted by a mob
	 * 
	 * @param knightlist
	 * 					- a list containing all knight characters in a room
	 * @param playerlist
	 * 					- a list containing all players in a room
	 * @return
	 * 					- the targeted player
	 */
	public static Player Target(ArrayList<Player> knightlist, ArrayList<Player> playerlist)
	{
		
		Random dice = new Random();
		
		if(knightlist != null)
		{
			Collections.shuffle(knightlist);
			for(Player x:knightlist)
			{
				int roll = dice.nextInt(100)+1;
				if(roll <= 70)
					return x;
			}
		}
		
		Collections.shuffle(playerlist);
		return playerlist.get(0);
				
		

		
		
	}
	

	/**
	 * This static method determines whether an attack was successful.  The parameters are the attacker and the defender.
	 * The return is an int 0 for a miss, 1 for a light hit, 2 for a hit, and 3 for a critical strike.
	 * 
	 * @param attacker
	 * 				- the attacking creature
	 * @param defender
	 * 				- the defending creature
	 * @return 0, 1, 2, 3
	 * 				- the resulting flag for the attack
	 * 
	 * @author Steven Chandler
	 */
	public static int Attack(Creature attacker, Creature defender)
	{
		
		Random percentile = new Random();
		
		int attack = attacker.getAttack() + percentile.nextInt(100) + 1;
		int defend = defender.getDefense() + percentile.nextInt(100) + 1;
		
		if(attack > 2*defend)
		{
			//critical hit
			return 3;
		}
		if(defend > 2*attack)
		{
			//miss
			return 0;
		}
		if(attack > defend)
		{
			//hit
			if(defender.getEvasion() < (percentile.nextInt(100)+1))
			{
				//regular hit
				return 2;
			}
			else
			{
				//evade the hit
				return 0;
			}
		}
		if(defend >= attack)
		{
			//light hit
			return 1;
		}
		
		return 0;
		
		
	}


	/**
	 * This static method determines the damage done on a successful attack.  The parameters are the result of the Attack method,
	 * the attacker and the defender.
	 * 
	 * @param attacker  
	 * 				- the attacking creature
	 * @param defender
	 * 				- the defending creature
	 * @param result
	 * 				- the result from the Attack method
	 * 
	 * @return damage done
	 * 				- an int containing the amount of damage done
	 * 
	 * @author Steven Chandler
	 */
	public static int physicalDamage(Creature attacker, Creature defender, int result)
	{
		
		double base;
		
		if(attacker instanceof Rogue)
		{
			base = attacker.getAgility() * attacker.getAttackModifier();
		}else
		{
			base = attacker.getStrength() * attacker.getAttackModifier();
		}
			
		int damage = (int) (base - defender.getDefense() - defender.getStamina());		
		
		//ensures at least one point of damage done
		if(damage < 1){
			Random damrand = new Random();
			damage = damrand.nextInt((10) + 1);
		}
		
		if(result == 3)
			//crit damage
			return damage * 2;
		
		if(result == 2)
			//regular damage
			return damage;
		
		if(result == 1)
		{
			damage = damage / 4;
			//ensures at least one point of damage done
			if(damage < 1)
				damage = 1;
			
			return damage;
		}
		
		
		return 0;
		
	}


	/**
	 * This static method determines the damage done on casting a damaging spell, status spells use a different method.
	 * The parameters are as follows: attacker, defender, the weakness int of the defender, the type of spell damage
	 * 
	 * @param attacker
	 * 				- the attacking creature
	 * @param defender
	 * 				- the defending creature
	 * @param weak
	 * 				- the weakness integer bitmap
	 * @param type
	 * 				- the integer type representation of the spell attack
	 * @return
	 * 
	 * @author Steven Chandler
	 */
	public static int magicalDamage(Creature attacker, Creature defender, int type, int mod)
	{
		
		int base = attacker.getMagic() * mod;
		double damage = base - 2*defender.getMDefense();
		
		int w = 0, r = 0, a = 0, i = 0;

		
		//tests the bits of the weak int to determine if any of the bits for the type are 1
		if( (defender.getImmunities() & (1 << type)) != 0 ) 
			w = 1;
		
		if( (defender.getImmunities() & (1 << (type + 5))) != 0 ) 		
			r = 1;
		
		if( (defender.getImmunities() & (1 << (type + 10))) != 0 ) 	
			a = 1;
		
		if( (defender.getImmunities() & (1 << (type + 15))) != 0 ) 	
			i = 1;
		
		if(damage < 1)
			damage = 1;
		
		//damage if weak to element
		if(w == 1)		
			return (int) (damage*1.5);
		//damage if resistant to element
		if(r == 1)
			return (int) (damage*.5);
		//returns negative damage if absorbing element
		if(a == 1)
			return (int) (-damage);
		//returns 0 damage if immune to element
		if(i == 1)
			return 0;
		//return damage if none of the above if statements are true
		return (int) damage;
		
	}


	/**
	 * This boolean returns a false value if the defender is immune to the ability, or a true value if not
	 * 
	 * @param defender
	 * 				- the resistance int for the defender
	 * @param type
	 * 				- the spell/ability's damage type(just the key value though)
	 * @return
	 * 				- boolean
	 * 
	 * @author Steven Chandler
	 */
	public static boolean statusEffect(Creature defender, int type)
	{
		
		if( (defender.getImmunities() & (1 << (type + 15))) != 0 )
			return false;
		
		return true;
	}
	
	
	/**
	 * This boolean returns a false if the character doesn't level up
	 * 
	 * @param level
	 * 				- the characters level
	 * @param xp
	 * 				- the characters xp
	 * @return
	 * 
	 * @author Steven Chandler
	 */
	public static boolean didLevel(int level, int xp)
	{
		if(xp > xpTable.get(level))
			return true;
		
		return false;
	}


	/**
	 * This static method calculates the amount of xp that a character will get when a mob is defeated
	 * if the mob is 6+ levels over the player, or the player 5+ levels over the mob, then 0 xp is awarded
	 * 
	 * @param level
	 * 				- the characters level, or the average level of the party
	 * @param mlevel
	 * 				- the mobs level
	 *  
	 * @return
	 * 
	 * @author Steven Chandler
	 */
	public static int calculateXP(int level, int mlevel)
	{
		
		int base = mlevel * 100;
		//level is either player or party average level
		int diff = mlevel - level;
		
		switch (diff) {
		case 5:
			return base * 2;
		case 4:
			return (int) (base * 1.8);
		case 3:
			return (int) (base * 1.6);
		case 2:
			return (int) (base * 1.4);
		case 1:
			return (int) (base * 1.2);
		case 0:
			return base;
		case -1:
			return (int) (base * .8);
		case -2:
			return (int) (base * .6);
		case -3:
			return (int) (base * .4);
		case -4:
			return (int) (base * .2);
		default:
			return 0;
		}
		
	}
	
	
	public static String resultToString(Creature att, Creature def, int dam, String abiname)
	{
		String result = (att.getName() + " hits " + def.getName() + "for " + dam + "with " + abiname);
		return result;		
	}
	
	public static String healResultToString(Creature att, Creature def, int dam, String abiname)
	{
		String result = (att.getName() + " heals " + def.getName() + "for " + dam + "with " + abiname);
		return result;		
	}
		
	
	
	
	
	
	
	
	
	



}
