package model.items.heavychest;

import model.items.Armor;

public class SteelPlate extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SteelPlate()
	{
		this.name = "Steel Plate";
		this.description = "";
		
		this.iID = 121;
		this.ilevel = 4;
		this.cost = 0;

		this.ihp = 20;
		this.istr = 2;
		this.ista = 6;
		this.idef = 9;
		this.ieva = -2;
		this.imdef = 3;
		
		this.eqslot = slots.get(2).toString();

	}

}