package model.items.largesword;

import model.items.Weapon;

public class BarrowSword extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BarrowSword()
	{
		this.name = "Barrow Sword";
		this.description = "";
		
		this.iID = 101;
		this.ilevel = 4;
		this.cost = 0;

		this.istr = 2;
		this.iatt = 6;
		this.iamod = 2;
		

		this.eqslot = slots.get(0).toString();
	}

}
