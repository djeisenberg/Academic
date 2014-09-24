package model.items.lightblades;

import model.items.Weapon;

public class DuskBlade extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DuskBlade()
	{
		this.name = "Dusk Blade";
		this.description = "";
		
		this.iID = 301;
		this.ilevel = 4;
		this.cost = 0;

		this.iagi = 2;
		this.iatt = 4;
		this.iamod = 2;
		
		this.eqslot = slots.get(0).toString();
	}

}