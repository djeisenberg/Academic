package model.items.heavyweapons;

import model.items.Weapon;

public class TitansMaul extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TitansMaul()
	{
		this.name = "Titan's Maul";
		this.description = "";
		
		this.iID = 402;
		this.ilevel = 6;
		this.cost = 0;

		this.istr = 6;
		this.iatt = 6;
		this.iamod = 3;
		
		this.eqslot = slots.get(0).toString();
	}

}