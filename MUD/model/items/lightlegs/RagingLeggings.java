package model.items.lightlegs;

import model.items.Armor;

public class RagingLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RagingLeggings()
	{
		this.name = "Raging Leggings";
		this.description = "";
		
		this.iID = 443;
		this.ilevel = 8;
		this.cost = 0;

		this.ihp = 20;
		this.istr = 8;
		this.iatt = 8;
		this.idef = 10;
		this.ieva = -1;
		this.imdef = 10;
		
		this.eqslot = slots.get(3).toString();

	}

}