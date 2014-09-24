package model.items.heavyweapons;

import model.items.Weapon;

public class ReinforcedMaul extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ReinforcedMaul()
	{
		this.name = "Reinforced Maul";
		this.description = "";
		
		this.iID = 400;
		this.ilevel = 2;
		this.cost = 0;

		this.istr = 2;
		this.iatt = 2;
		this.iamod = 2;
		
		this.eqslot = slots.get(0).toString();
	}

}