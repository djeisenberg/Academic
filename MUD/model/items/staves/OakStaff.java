package model.items.staves;

import model.items.Weapon;

public class OakStaff extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OakStaff()
	{
		this.name = "Oak Staff";
		this.description = "";
		
		this.iID = 4;
		this.ilevel = 1;
		this.cost = 0;

		this.iamod = 1;
		
		this.eqslot = slots.get(0).toString();
	}

}