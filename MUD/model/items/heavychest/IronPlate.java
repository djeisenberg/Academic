package model.items.heavychest;

import model.items.Armor;

public class IronPlate extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IronPlate()
	{
		this.name = "Iron Plate";
		this.description = "";
		
		this.iID = 120;
		this.ilevel = 2;
		this.cost = 0;

		this.ihp = 10;
		this.istr = 1;
		this.ista = 3;
		this.idef = 6;
		this.ieva = -2;
		this.imdef = 1;
		
		this.eqslot = slots.get(2).toString();

	}

}