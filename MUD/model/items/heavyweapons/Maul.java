package model.items.heavyweapons;

import model.items.Weapon;

public class Maul extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Maul()
	{
		this.name = "Maul";
		this.description = "";
		
		this.iID = 5;
		this.ilevel = 1;
		this.cost = 0;

		this.iamod = 1.5;
		
		this.eqslot = slots.get(0).toString();
	}

}