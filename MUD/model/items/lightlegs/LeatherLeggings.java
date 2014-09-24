package model.items.lightlegs;

import model.items.Armor;

public class LeatherLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LeatherLeggings()
	{
		this.name = "Leather Leggings";
		this.description = "";
		
		this.iID = 11;
		this.ilevel = 1;
		this.cost = 0;

		this.idef = 2;
		this.ieva = -1;
		this.imdef = 2;
		
		this.eqslot = slots.get(3).toString();

	}

}