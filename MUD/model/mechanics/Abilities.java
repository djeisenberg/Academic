package model.mechanics;

import java.util.List;
import java.util.Random;

import model.creatures.Creature;
import model.creatures.Mob;
import model.creatures.Monster;
import model.creatures.Player;
import model.items.Item;

public class Abilities {

	private static Random rand = new Random();

	public static String abilSwitch(Creature att, Creature def, String akey,
			List<Mob> mobs, List<Player> players) {

		if (akey.equals("Kni001"))
			return ShieldBash(att, def);
		if (akey.equals("Kni002"))
			return CrippleDefense(att, def);

//		if (akey.equals("Rog001"))
//			Steal(att, def);
		if (akey.equals("Rog002"))
			return Backstab(att, def);
		if (akey.equals("Rog003"))
			return Poison(att, def);
		if (akey.equals("Rog004"))
			return Vanish(att, def);

		if (akey.equals("Blm001"))
			return FireBall(att, def);
		if (akey.equals("Blm002"))
			return IceLance(att, def);
		if (akey.equals("Blm003"))
			return WindCutter(att, def);
		if (akey.equals("Blm004"))
			return StoneRain(att, def);
		if (akey.equals("Blm005"))
			return Slug(att, def);
		if (akey.equals("Blm006"))
			return FireBall2(att, def);
		if (akey.equals("Blm007"))
			return IceLance2(att, def);
		if (akey.equals("Blm008"))
			return WindCutter2(att, def);
		if (akey.equals("Blm009"))
			return StoneRain2(att, def);

		if (akey.equals("Whm001"))
			return Cure(att, def);
		if (akey.equals("Whm002"))
			return Smite(att, def);
		if (akey.equals("Whm003"))
			return ShieldingLight(att, def);
		if (akey.equals("Whm004"))
			return GroupHeal(att, players);
		if (akey.equals("Whm005"))
			return GreaterCure(att, def);
		if (akey.equals("Whm006"))
			return GreaterSmite(att, def);

		if (akey.equals("Mkn001"))
			return Fire(att, def);
		if (akey.equals("Mkn002"))
			return Ice(att, def);
		if (akey.equals("Mkn003"))
			return Wind(att, def);
		if (akey.equals("Mkn004"))
			return Earth(att, def);
		if (akey.equals("Mkn005"))
			return Holy(att, def);

		if (akey.equals("Ber001"))
			return PowerAttack(att, def);
		if (akey.equals("Ber002"))
			return StunningStrike(att, def);
		if (akey.equals("Ber003"))
			return Frenzy(att, mobs);
		if (akey.equals("Ber004"))
			return Whirlwind(att, mobs);
		return null;

	}

	private static String ShieldBash(Creature att, Creature def) {
		return null;
		// TODO Auto-generated method stub

	}

	private static String CrippleDefense(Creature att, Creature def) {
		return null;
		// TODO Auto-generated method stub

	}

	public static Item Steal(Creature att, Creature def) {
		
		if ((att.getCurrentMP() - 1) >= 0) {
			Item x = ((Monster) def).stealList((rand.nextInt(100) + 1));
			att.setCurrentMP(att.getCurrentMP() - 1);
			return x;
		}
		return null;
	}
	
	private static String Backstab(Creature att, Creature def) {

		int tdamage = 0;
		
		if ((att.getCurrentMP() - 1) >= 0) {
			int tattack = Mechanics.Attack(att, def);

			if (tattack == 0)
				tattack = 1;

			tdamage = Mechanics.physicalDamage(att, def, tattack);
			tdamage = (int) (tdamage * 1.5);
			def.setCurrentHP(def.getCurrentHP() - tdamage);
			att.setCurrentMP(att.getCurrentMP() - 1);
		}
		return Mechanics.resultToString(att, def, tdamage, "Backstab");
	}

	private static String Poison(Creature att, Creature def) {
		return null;
		// TODO Auto-generated method stub

	}

	private static String Vanish(Creature att, Creature def) {
		return null;
		// TODO Auto-generated method stub

	}

	private static String FireBall(Creature att, Creature def) {

		int tdamage = 0;
		if ((att.getCurrentMP() - 3) >= 0) {
			tdamage = Mechanics.magicalDamage(att, def, 0, 2);
			def.setCurrentHP(def.getCurrentHP() - tdamage);

			if (def.getCurrentHP() > def.getHP())
				def.setCurrentHP(def.getHP());

			att.setCurrentMP(att.getCurrentMP() - 3);
		}
		return Mechanics.resultToString(att, def, tdamage, "Fireball");
	}

	private static String IceLance(Creature att, Creature def) {
		
		int tdamage = 0;
		if ((att.getCurrentMP() - 3) >= 0) {
			tdamage = Mechanics.magicalDamage(att, def, 1, 2);
			def.setCurrentHP(def.getCurrentHP() - tdamage);

			if (def.getCurrentHP() > def.getHP())
				def.setCurrentHP(def.getHP());

			att.setCurrentMP(att.getCurrentMP() - 3);
		}
		return Mechanics.resultToString(att, def, tdamage, "Ice Lance");
	}

	private static String WindCutter(Creature att, Creature def) {

		int tdamage = 0;
		if ((att.getCurrentMP() - 3) >= 0) {
			tdamage = Mechanics.magicalDamage(att, def, 2, 2);
			def.setCurrentHP(def.getCurrentHP() - tdamage);

			if (def.getCurrentHP() > def.getHP())
				def.setCurrentHP(def.getHP());

			att.setCurrentMP(att.getCurrentMP() - 3);
		}
		return Mechanics.resultToString(att, def, tdamage, "Wind Cutter");
	}

	private static String StoneRain(Creature att, Creature def) {

		int tdamage = 0;
		if ((att.getCurrentMP() - 3) >= 0) {
			tdamage = Mechanics.magicalDamage(att, def, 3, 2);
			def.setCurrentHP(def.getCurrentHP() - tdamage);

			if (def.getCurrentHP() > def.getHP())
				def.setCurrentHP(def.getHP());

			att.setCurrentMP(att.getCurrentMP() - 3);
		}
		return Mechanics.resultToString(att, def, tdamage, "Stone Rain");
	}

	private static String Slug(Creature att, Creature def) {
		return null;
		// TODO Auto-generated method stub

	}

	private static String FireBall2(Creature att, Creature def) {

		int tdamage = 0;
		if ((att.getCurrentMP() - 10) >= 0) {
			tdamage = Mechanics.magicalDamage(att, def, 0, 5);
			def.setCurrentHP(def.getCurrentHP() - tdamage);

			if (def.getCurrentHP() > def.getHP())
				def.setCurrentHP(def.getHP());

			att.setCurrentMP(att.getCurrentMP() - 10);
		}
		return Mechanics.resultToString(att, def, tdamage, "Greater Fire Ball");
	}

	private static String IceLance2(Creature att, Creature def) {

		int tdamage = 0;
		if ((att.getCurrentMP() - 10) >= 0) {
			tdamage = Mechanics.magicalDamage(att, def, 1, 5);
			def.setCurrentHP(def.getCurrentHP() - tdamage);

			if (def.getCurrentHP() > def.getHP())
				def.setCurrentHP(def.getHP());

			att.setCurrentMP(att.getCurrentMP() - 10);
		}
		return Mechanics.resultToString(att, def, tdamage, "Greater Ice Lance");
	}

	private static String WindCutter2(Creature att, Creature def) {

		int tdamage = 0;
		if ((att.getCurrentMP() - 10) >= 0) {
			tdamage = Mechanics.magicalDamage(att, def, 2, 5);
			def.setCurrentHP(def.getCurrentHP() - tdamage);

			if (def.getCurrentHP() > def.getHP())
				def.setCurrentHP(def.getHP());

			att.setCurrentMP(att.getCurrentMP() - 10);
		}
		return Mechanics.resultToString(att, def, tdamage, "Greater Wind Cutter");
	}

	private static String StoneRain2(Creature att, Creature def) {

		int tdamage = 0;
		if ((att.getCurrentMP() - 10) >= 0) {
			tdamage = Mechanics.magicalDamage(att, def, 3, 5);
			def.setCurrentHP(def.getCurrentHP() - tdamage);

			if (def.getCurrentHP() > def.getHP())
				def.setCurrentHP(def.getHP());

			att.setCurrentMP(att.getCurrentMP() - 10);
		}
		return Mechanics.resultToString(att, def, tdamage, "Greater Stone Rain");
	}

	private static String Cure(Creature att, Creature def) {

		int theal = 0;
		if ((att.getCurrentMP() - 3) >= 0) {
			theal = att.getMagic() * 2;
			def.setCurrentHP(def.getCurrentHP() + theal);

			if (def.getCurrentHP() > def.getHP())
				def.setCurrentHP(def.getHP());

			att.setCurrentMP(att.getCurrentMP() - 3);
		}
		return Mechanics.healResultToString(att, def, theal, "Cure");
	}

	private static String Smite(Creature att, Creature def) {

		int tdamage = 0;
		if ((att.getCurrentMP() - 3) >= 0) {
			tdamage = Mechanics.magicalDamage(att, def, 4, 2);
			def.setCurrentHP(def.getCurrentHP() - tdamage);

			if (def.getCurrentHP() > def.getHP())
				def.setCurrentHP(def.getHP());

			att.setCurrentMP(att.getCurrentMP() - 3);
		}
		return Mechanics.resultToString(att, def, tdamage, "Smite");
	}

	private static String ShieldingLight(Creature att, Creature def) {
		return null;
		// TODO Auto-generated method stub

	}

	private static String GroupHeal(Creature att, List<Player> players) {

		int theal = 0;
		String result = "";
		if ((att.getCurrentMP() - 10) >= 0) {
			theal = (int) (att.getMagic() * 3.5);

			for (Player x : players) {
				x.setCurrentHP(x.getCurrentHP() + theal);

				if (x.getCurrentHP() > x.getHP())
					x.setCurrentHP(x.getHP());
				
				result.concat(Mechanics.healResultToString(att, x, theal, "Group Heal") + "\n");
			}
			att.setCurrentMP(att.getCurrentMP() - 10);
		}
		return result;
	}

	private static String GreaterCure(Creature att, Creature def) {

		int theal = 0;
		if ((att.getCurrentMP() - 3) >= 0) {
			theal = att.getMagic() * 5;
			def.setCurrentHP(def.getCurrentHP() + theal);

			if (def.getCurrentHP() > def.getHP())
				def.setCurrentHP(def.getHP());

			att.setCurrentMP(att.getCurrentMP() - 10);
		}
		return Mechanics.healResultToString(att, def, theal, "Greater Cure");	
	}

	private static String GreaterSmite(Creature att, Creature def) {

		int tdamage = 0;
		if ((att.getCurrentMP() - 10) >= 0) {
			tdamage = Mechanics.magicalDamage(att, def, 4, 5);
			def.setCurrentHP(def.getCurrentHP() - tdamage);

			if (def.getCurrentHP() > def.getHP())
				def.setCurrentHP(def.getHP());

			att.setCurrentMP(att.getCurrentMP() - 10);
		}
		return Mechanics.resultToString(att, def, tdamage, "Greater Smite");
	}

	private static String Fire(Creature att, Creature def) {
		
		int tdamage = 0;
		if ((att.getCurrentMP() - 3) >= 0) {
			int tattack = Mechanics.Attack(att, def);

			if (tattack == 0)
				tattack = 1;

			int pdamage = Mechanics.physicalDamage(att, def, tattack);
			int sdamage = Mechanics.magicalDamage(att, def, 0, 1);
			
			if (sdamage < 0) {
				tdamage = sdamage - pdamage;
				def.setCurrentHP(def.getCurrentHP() - tdamage);
				if (def.getCurrentHP() > def.getHP())
					def.setCurrentHP(def.getHP());
			} else {
				tdamage = sdamage + pdamage;
				def.setCurrentHP(def.getCurrentHP() - tdamage);
			}

			att.setCurrentMP(att.getCurrentMP() - 3);
		}
		return Mechanics.resultToString(att, def, tdamage, "Fire");
	}

	private static String Ice(Creature att, Creature def) {

		int tdamage = 0;
		if ((att.getCurrentMP() - 3) >= 0) {
			int tattack = Mechanics.Attack(att, def);

			if (tattack == 0)
				tattack = 1;

			int pdamage = Mechanics.physicalDamage(att, def, tattack);
			int sdamage = Mechanics.magicalDamage(att, def, 0, 1);
			
			if (sdamage < 0) {
				tdamage = sdamage - pdamage;
				def.setCurrentHP(def.getCurrentHP() - tdamage);
				if (def.getCurrentHP() > def.getHP())
					def.setCurrentHP(def.getHP());
			} else {
				tdamage = sdamage + pdamage;
				def.setCurrentHP(def.getCurrentHP() - tdamage);
			}

			att.setCurrentMP(att.getCurrentMP() - 3);
		}
		return Mechanics.resultToString(att, def, tdamage, "Ice");
	}

	private static String Wind(Creature att, Creature def) {

		int tdamage = 0;
		if ((att.getCurrentMP() - 3) >= 0) {
			int tattack = Mechanics.Attack(att, def);

			if (tattack == 0)
				tattack = 1;

			int pdamage = Mechanics.physicalDamage(att, def, tattack);
			int sdamage = Mechanics.magicalDamage(att, def, 0, 1);
			
			if (sdamage < 0) {
				tdamage = sdamage - pdamage;
				def.setCurrentHP(def.getCurrentHP() - tdamage);
				if (def.getCurrentHP() > def.getHP())
					def.setCurrentHP(def.getHP());
			} else {
				tdamage = sdamage + pdamage;
				def.setCurrentHP(def.getCurrentHP() - tdamage);
			}

			att.setCurrentMP(att.getCurrentMP() - 3);
		}
		return Mechanics.resultToString(att, def, tdamage, "Wind");
	}

	private static String Earth(Creature att, Creature def) {

		int tdamage = 0;
		if ((att.getCurrentMP() - 3) >= 0) {
			int tattack = Mechanics.Attack(att, def);

			if (tattack == 0)
				tattack = 1;

			int pdamage = Mechanics.physicalDamage(att, def, tattack);
			int sdamage = Mechanics.magicalDamage(att, def, 0, 1);
			
			if (sdamage < 0) {
				tdamage = sdamage - pdamage;
				def.setCurrentHP(def.getCurrentHP() - tdamage);
				if (def.getCurrentHP() > def.getHP())
					def.setCurrentHP(def.getHP());
			} else {
				tdamage = sdamage + pdamage;
				def.setCurrentHP(def.getCurrentHP() - tdamage);
			}

			att.setCurrentMP(att.getCurrentMP() - 3);
		}
		return Mechanics.resultToString(att, def, tdamage, "Earth");
	}

	private static String Holy(Creature att, Creature def) {

		int tdamage = 0;
		if ((att.getCurrentMP() - 3) >= 0) {
			int tattack = Mechanics.Attack(att, def);

			if (tattack == 0)
				tattack = 1;

			int pdamage = Mechanics.physicalDamage(att, def, tattack);
			int sdamage = Mechanics.magicalDamage(att, def, 0, 1);
			
			if (sdamage < 0) {
				tdamage = sdamage - pdamage;
				def.setCurrentHP(def.getCurrentHP() - tdamage);
				if (def.getCurrentHP() > def.getHP())
					def.setCurrentHP(def.getHP());
			} else {
				tdamage = sdamage + pdamage;
				def.setCurrentHP(def.getCurrentHP() - tdamage);
			}

			att.setCurrentMP(att.getCurrentMP() - 3);
		}
		return Mechanics.resultToString(att, def, tdamage, "Holy");
	}

	private static String PowerAttack(Creature att, Creature def) {

		int tdamage = 0;
		if ((att.getCurrentMP() - 1) >= 0) {
			int tattack = Mechanics.Attack(att, def);

			if (tattack == 0)
				tattack = 1;

			tdamage = Mechanics.physicalDamage(att, def, tattack);
			tdamage = (int) (tdamage * 1.25);
			def.setCurrentHP(def.getCurrentHP() - tdamage);
			att.setCurrentMP(att.getCurrentMP() - 1);
		}
		return Mechanics.resultToString(att, def, tdamage, "Power Attack");
	}

	private static String StunningStrike(Creature att, Creature def) {
		return null;
		// TODO Auto-generated method stub

	}

	private static String Frenzy(Creature att, List<Mob> mobs) {

		int tdamage = 0;
		String result = "";
		if ((att.getCurrentMP() - 3) >= 0) {
			for (int i = 1; i <= 2; i++) {
				int x = rand.nextInt(mobs.size());

				int tattack = Mechanics.Attack(att, mobs.get(x));

				if (tattack == 0)
					tattack = 1;

				tdamage = Mechanics.physicalDamage(att, mobs.get(x),
						tattack);
				mobs.get(x).setCurrentHP(mobs.get(x).getCurrentHP() - tdamage);
				
				result.concat(Mechanics.resultToString(att, mobs.get(x), tdamage, "Frenzy") + "\n");
			}
			att.setCurrentMP(att.getCurrentMP() - 3);
		}
		return result;
	}

	private static String Whirlwind(Creature att, List<Mob> mobs) {

		int tdamage = 0;
		String result = "";
		
		if ((att.getCurrentMP() - 5) >= 0) {
			for (int i = 1; i <= 3; i++) {
				int x = rand.nextInt(mobs.size());

				int tattack = Mechanics.Attack(att, mobs.get(x));

				if (tattack == 0)
					tattack = 1;

				tdamage = Mechanics.physicalDamage(att, mobs.get(x),
						tattack);
				mobs.get(x).setCurrentHP(mobs.get(x).getCurrentHP() - tdamage);

				result.concat(Mechanics.resultToString(att, mobs.get(x), tdamage, "Frenzy") + "\n");
				
			}
			att.setCurrentMP(att.getCurrentMP() - 5);
		}
		return result;
	}

	/**
	 * Returns an ability from a String beginning with the name of an ability.
	 * 
	 * @param ability
	 *            A String which starts with the name of an ability.
	 * @return - a String key corresponding to the named ability, or null if
	 *         argument is not a valid ability.
	 */
	public static String getAbilityByName(String ability) {
		String check = ability.toLowerCase();
		
		if (check.startsWith("power attack"))
			return "Ber001";
		if (check.startsWith("stunning strike"))
			return "Ber002";
		if (check.startsWith("frenzy"))
			return "Ber003";
		if (check.startsWith("whirlwind"))
			return "Ber004";

		// check 2nd rank before 1st or will never return correct spell
		if (check.startsWith("fire ball 2"))
			return "Blm006";
		if (check.startsWith("ice lance 2"))
			return "Blm007";
		if (check.startsWith("wind cutter 2"))
			return "Blm008";
		if (check.startsWith("stone rain 2"))
			return "Blm009";
		if (check.startsWith("fire ball"))
			return "Blm001";
		if (check.startsWith("ice lance"))
			return "Blm002";
		if (check.startsWith("wind cutter"))
			return "Blm003";
		if (check.startsWith("stone rain"))
			return "Blm004";
		if (check.startsWith("slug"))
			return "Blm005";

		if (check.startsWith("shield bash"))
			return "Kni001";
		if (check.startsWith("cripple defense"))
			return "Kni002";

		if (check.startsWith("fire"))
			return "Mkn001";
		if (check.startsWith("ice"))
			return "Mkn002";
		if (check.startsWith("wind"))
			return "Mkn003";
		if (check.startsWith("earth"))
			return "Mkn004";
		if (check.startsWith("holy"))
			return "Mkn005";

		if (check.startsWith("steal"))
			return "Rog001";
		if (check.startsWith("backstab"))
			return "Rog002";
		if (check.startsWith("poison"))
			return "Rog003";
		if (check.startsWith("vanish"))
			return "Rog004";

		if (check.startsWith("cure"))
			return "Whm001";
		if (check.startsWith("smite"))
			return "Whm002";
		if (check.startsWith("shielding light"))
			return "Whm003";
		if (check.startsWith("group heal"))
			return "Whm004";
		if (check.startsWith("greater cure"))
			return "Whm005";
		if (check.startsWith("greater smite"))
			return "Whm006";

		return null;
	}

}
