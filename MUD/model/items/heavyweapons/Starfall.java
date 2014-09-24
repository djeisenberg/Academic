package model.items.heavyweapons;

import model.items.Weapon;

public class Starfall extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Starfall()
	{
		this.name = "Starfall";
		this.description = "";
		
		this.iID = 404;
		this.ilevel = 10;
		this.cost = 0;

		this.istr = 10;
		this.iatt = 10;
		this.iamod = 4;
		
		this.eqslot = slots.get(0).toString();
	}

}