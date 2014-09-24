package model.items.largesword;

import model.items.Weapon;

public class GoblinSword extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GoblinSword()
	{
		this.name = "Goblin Sword";
		this.description = "";
		
		this.iID = 100;
		this.ilevel = 2;
		this.cost = 0;

		this.istr = 1;
		this.iatt = 3;
		this.iamod = 1.5;
		

		this.eqslot = slots.get(0).toString();
	}

}
