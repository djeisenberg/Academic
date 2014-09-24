package model.items.lightlegs;

import model.items.Armor;

public class HideLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public HideLeggings()
	{
		this.name = "Hide Leggings";
		this.description = "";
		
		this.iID = 440;
		this.ilevel = 2;
		this.cost = 0;

		this.ihp = 5;
		this.istr = 2;
		this.iatt = 2;
		this.idef = 4;
		this.ieva = -1;
		this.imdef = 4;
		
		this.eqslot = slots.get(3).toString();

	}

}