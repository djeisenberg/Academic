package model.items.lightlegs;

import model.items.Armor;

public class LughsLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LughsLeggings()
	{
		this.name = "Lugh's Leggings";
		this.description = "";
		
		this.iID = 444;
		this.ilevel = 10;
		this.cost = 0;

		this.ihp = 25;
		this.istr = 10;
		this.iatt = 10;
		this.idef = 12;
		this.ieva = -1;
		this.imdef = 12;
		
		this.eqslot = slots.get(3).toString();

	}

}