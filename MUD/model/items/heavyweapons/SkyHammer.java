package model.items.heavyweapons;

import model.items.Weapon;

public class SkyHammer extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SkyHammer()
	{
		this.name = "Sky Hammer";
		this.description = "";
		
		this.iID = 403;
		this.ilevel = 8;
		this.cost = 0;

		this.istr = 8;
		this.iatt = 8;
		this.iamod = 3.5;
		
		this.eqslot = slots.get(0).toString();
	}

}