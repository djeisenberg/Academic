package model.items.heavychest;

import model.items.Armor;

public class AdamantitePlate extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AdamantitePlate()
	{
		this.name = "Adamantite Plate";
		this.description = "";
		
		this.iID = 122;
		this.ilevel = 6;
		this.cost = 0;

		this.ihp = 30;
		this.istr = 3;
		this.ista = 9;
		this.idef = 12;
		this.ieva = -2;
		this.imdef = 4;
		
		this.eqslot = slots.get(2).toString();

	}

}