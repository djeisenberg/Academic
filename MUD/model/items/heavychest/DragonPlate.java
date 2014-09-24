package model.items.heavychest;

import model.items.Armor;

public class DragonPlate extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DragonPlate()
	{
		this.name = "Dragon Plate";
		this.description = "";
		
		this.iID = 123;
		this.ilevel = 8;
		this.cost = 0;

		this.ihp = 40;
		this.istr = 4;
		this.ista = 12;
		this.idef = 15;
		this.ieva = -2;
		this.imdef = 5;
		
		this.eqslot = slots.get(2).toString();

	}

}