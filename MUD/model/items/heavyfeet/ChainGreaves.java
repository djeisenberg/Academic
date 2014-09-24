package model.items.heavyfeet;

import model.items.Armor;

public class ChainGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ChainGreaves()
	{
		this.name = "Chain Greaves";
		this.description = "";
		
		this.iID = 8;
		this.ilevel = 1;
		this.cost = 0;

		this.idef = 3;
		this.ieva = -2;
		this.imdef = 1;
		
		this.eqslot = slots.get(4).toString();

	}

}