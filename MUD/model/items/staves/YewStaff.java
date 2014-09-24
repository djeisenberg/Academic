package model.items.staves;

import model.items.Weapon;

public class YewStaff extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public YewStaff()
	{
		this.name = "Yew Staff";
		this.description = "";
		
		this.iID = 500;
		this.ilevel = 2;
		this.cost = 0;

		this.imag = 2;
		this.imdef = 2;
		this.iamod = 1.5;
		
		this.eqslot = slots.get(0).toString();
	}

}