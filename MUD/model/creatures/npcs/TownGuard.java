package model.creatures.npcs;

import java.util.ArrayList;

import model.creatures.NPC;
import model.world.Room;

public class TownGuard extends NPC
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TownGuard(ArrayList<Room> city) {
		this.name = "Town Guard";

		this.strength = 1000;
		this.agility = 1000;
		this.stamina = 1000;
		this.magic = 1000;

		this.attack = 1000;
		this.defense = 1000;
		this.evasion = 100;
		this.magicDefense = 1000;

		this.hp = 5000;
		this.mp = 5000;
		this.level = 10;
		movable = city;

		this.mID = 2002;
		

	}
	
	@Override
	public void act() {
	
	}
}
