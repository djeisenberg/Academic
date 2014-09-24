package model.items.heavylegs;

import model.items.Armor;

public class SteelLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SteelLeggings()
	{
		this.name = "Steel Leggings";
		this.description = "";
		
		this.iID = 141;
		this.ilevel = 4;
		this.cost = 0;

		this.ihp = 20;
		this.istr = 2;
		this.ista = 6;
		this.idef = 9;
		this.ieva = -2;
		this.imdef = 3;
		
		this.eqslot = slots.get(3).toString();

	}

}