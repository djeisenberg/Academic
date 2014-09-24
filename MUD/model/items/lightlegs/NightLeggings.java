package model.items.lightlegs;

import model.items.Armor;

public class NightLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NightLeggings()
	{
		this.name = "Night Leggings";
		this.description = "";
		
		this.iID = 343;
		this.ilevel = 8;
		this.cost = 0;

		this.ihp = 20;
		this.iagi = 8;
		this.ista = 4;
		this.iatt = 4;
		this.idef = 10;
		this.ieva = -1;
		this.imdef = 10;
		
		this.eqslot = slots.get(3).toString();

	}

}