package model.items.staves;

import model.items.Weapon;

public class RedwoodStaff extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RedwoodStaff()
	{
		this.name = "Redwood Staff";
		this.description = "";
		
		this.iID = 502;
		this.ilevel = 6;
		this.cost = 0;

		this.imag = 6;
		this.imdef = 6;
		this.iamod = 2.5;
		
		this.eqslot = slots.get(0).toString();
	}

}