package model.items.lightlegs;

import model.items.Armor;

public class ReapersLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ReapersLeggings()
	{
		this.name = "Reaper's Leggings";
		this.description = "";
		
		this.iID = 441;
		this.ilevel = 4;
		this.cost = 0;

		this.ihp = 10;
		this.istr = 4;
		this.iatt = 4;
		this.idef = 6;
		this.ieva = -1;
		this.imdef = 6;
		
		this.eqslot = slots.get(3).toString();

	}

}