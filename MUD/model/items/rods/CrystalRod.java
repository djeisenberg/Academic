package model.items.rods;

import model.items.Weapon;

public class CrystalRod extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CrystalRod()
	{
		this.name = "Crystal Rod";
		this.description = "";
		
		this.iID = 600;
		this.ilevel = 2;
		this.cost = 0;

		this.imag = 2;
		this.imdef = 2;
		this.iamod = 1.5;
		
		this.eqslot = slots.get(0).toString();
	}

}