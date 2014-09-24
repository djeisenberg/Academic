package model.items.lightfeet;

import model.items.Armor;

public class LughsGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LughsGreaves()
	{
		this.name = "Lugh's Greaves";
		this.description = "";
		
		this.iID = 464;
		this.ilevel = 10;
		this.cost = 0;

		this.ihp = 25;
		this.istr = 10;
		this.iatt = 10;
		this.idef = 12;
		this.ieva = -1;
		this.imdef = 12;
		
		this.eqslot = slots.get(4).toString();

	}

}