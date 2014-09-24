package model.items.staves;

import model.items.Weapon;

public class HornwoodStaff extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public HornwoodStaff()
	{
		this.name = "Hornwood Staff";
		this.description = "";
		
		this.iID = 503;
		this.ilevel = 8;
		this.cost = 0;

		this.imag = 8;
		this.imdef = 8;
		this.iamod = 3;
		
		this.eqslot = slots.get(0).toString();
	}

}