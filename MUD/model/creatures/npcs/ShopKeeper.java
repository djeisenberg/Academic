package model.creatures.npcs;

import model.creatures.NPC;

public class ShopKeeper extends NPC{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ShopKeeper() {
		this.name = "Bill the Merchant";

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

		this.mID = 2001;
		

	}
	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void move(){
		return;
	}

}
