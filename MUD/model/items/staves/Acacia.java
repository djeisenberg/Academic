package model.items.staves;

import model.items.Weapon;

public class Acacia extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Acacia()
	{
		this.name = "Acacia";
		this.description = "";
		
		this.iID = 504;
		this.ilevel = 10;
		this.cost = 0;

		this.imag = 10;
		this.imdef = 10;
		this.iamod = 3.5;
		
		this.eqslot = slots.get(0).toString();
	}

}