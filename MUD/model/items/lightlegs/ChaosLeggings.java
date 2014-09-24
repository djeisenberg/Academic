package model.items.lightlegs;

import model.items.Armor;

public class ChaosLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ChaosLeggings()
	{
		this.name = "Chaos Leggings";
		this.description = "";
		
		this.iID = 442;
		this.ilevel = 6;
		this.cost = 0;

		this.ihp = 15;
		this.istr = 6;
		this.iatt = 6;
		this.idef = 8;
		this.ieva = -1;
		this.imdef = 8;
		
		this.eqslot = slots.get(3).toString();

	}

}