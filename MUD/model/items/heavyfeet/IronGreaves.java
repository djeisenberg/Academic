package model.items.heavyfeet;

import model.items.Armor;

public class IronGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IronGreaves()
	{
		this.name = "Iron Greaves";
		this.description = "";
		
		this.iID = 160;
		this.ilevel = 2;
		this.cost = 0;

		this.ihp = 10;
		this.istr = 1;
		this.ista = 3;
		this.idef = 6;
		this.ieva = -2;
		this.imdef = 2;
		
		this.eqslot = slots.get(4).toString();

	}

}