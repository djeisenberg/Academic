package model.items.heavyfeet;

import model.items.Armor;

public class SteelGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SteelGreaves()
	{
		this.name = "Steel Greaves";
		this.description = "";
		
		this.iID = 161;
		this.ilevel = 4;
		this.cost = 0;

		this.ihp = 20;
		this.istr = 2;
		this.ista = 6;
		this.idef = 9;
		this.ieva = -2;
		this.imdef = 3;
		
		this.eqslot = slots.get(4).toString();

	}

}