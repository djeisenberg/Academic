package model.items.lightblades;

import model.items.Weapon;

public class GoblinKnife extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GoblinKnife()
	{
		this.name = "Goblin Knife";
		this.description = "";
		
		this.iID = 300;
		this.ilevel = 2;
		this.cost = 0;

		this.iagi = 1;
		this.iatt = 2;
		this.iamod = 1.5;
		
		this.eqslot = slots.get(0).toString();
	}

}
