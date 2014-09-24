package model.items.heavyfeet;

import model.items.Armor;

public class AdamantiteGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AdamantiteGreaves()
	{
		this.name = "Adamantite Greaves";
		this.description = "";
		
		this.iID = 162;
		this.ilevel = 6;
		this.cost = 0;

		this.ihp = 30;
		this.istr = 3;
		this.ista = 9;
		this.idef = 12;
		this.ieva = -2;
		this.imdef = 4;
		
		this.eqslot = slots.get(4).toString();

	}

}