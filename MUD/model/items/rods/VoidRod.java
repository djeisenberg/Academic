package model.items.rods;

import model.items.Weapon;

public class VoidRod extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public VoidRod()
	{
		this.name = "Void Rod";
		this.description = "";
		
		this.iID = 603;
		this.ilevel = 8;
		this.cost = 0;

		this.imag = 8;
		this.imdef = 8;
		this.iamod = 3;
		
		this.eqslot = slots.get(0).toString();
	}

}