package model.items.lightlegs;

import model.items.Armor;

public class DisplacingLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DisplacingLeggings()
	{
		this.name = "Displacing Leggings";
		this.description = "";
		
		this.iID = 341;
		this.ilevel = 4;
		this.cost = 0;

		this.ihp = 10;
		this.iagi = 4;
		this.ista = 2;
		this.iatt = 2;
		this.idef = 6;
		this.ieva = -1;
		this.imdef = 6;
		
		this.eqslot = slots.get(3).toString();

	}

}