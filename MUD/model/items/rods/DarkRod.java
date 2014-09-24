package model.items.rods;

import model.items.Weapon;

public class DarkRod extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DarkRod()
	{
		this.name = "Dark Rod";
		this.description = "";
		
		this.iID = 601;
		this.ilevel = 4;
		this.cost = 0;

		this.imag = 4;
		this.imdef = 4;
		this.iamod = 2;
		
		this.eqslot = slots.get(0).toString();
	}

}