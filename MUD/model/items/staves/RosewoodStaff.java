package model.items.staves;

import model.items.Weapon;

public class RosewoodStaff extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RosewoodStaff()
	{
		this.name = "Rosewood Staff";
		this.description = "";
		
		this.iID = 501;
		this.ilevel = 4;
		this.cost = 0;

		this.imag = 4;
		this.imdef = 4;
		this.iamod = 2;
		
		this.eqslot = slots.get(0).toString();
	}

}