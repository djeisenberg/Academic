package model.items.heavyfeet;

import model.items.Armor;

public class NuadasGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NuadasGreaves()
	{
		this.name = "Nuada's Greaves";
		this.description = "";
		
		this.iID = 164;
		this.ilevel = 10;
		this.cost = 0;

		this.ihp = 50;
		this.istr = 5;
		this.ista = 15;
		this.idef = 18;
		this.ieva = -2;
		this.imdef = 6;
		
		this.eqslot = slots.get(4).toString();

	}

}