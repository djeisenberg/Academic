package model.items.rods;

import model.items.Weapon;

public class NightRod extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NightRod()
	{
		this.name = "Night Rod";
		this.description = "";
		
		this.iID = 602;
		this.ilevel = 6;
		this.cost = 0;

		this.imag = 6;
		this.imdef = 6;
		this.iamod = 2.5;
		
		this.eqslot = slots.get(0).toString();
	}

}