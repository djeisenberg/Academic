package model.items.rods;

import model.items.Weapon;

public class Ejus extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Ejus()
	{
		this.name = "Ejus";
		this.description = "";
		
		this.iID = 604;
		this.ilevel = 10;
		this.cost = 0;

		this.imag = 10;
		this.imdef = 10;
		this.iamod = 3.5;
		
		this.eqslot = slots.get(0).toString();
	}

}