package model.items.heavychest;

import model.items.Armor;

public class NuadasPlate extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NuadasPlate()
	{
		this.name = "Nuada's Plate";
		this.description = "";
		
		this.iID = 124;
		this.ilevel = 10;
		this.cost = 0;

		this.ihp = 50;
		this.istr = 5;
		this.ista = 15;
		this.idef = 18;
		this.ieva = -2;
		this.imdef = 6;
		
		this.eqslot = slots.get(2).toString();

	}

}