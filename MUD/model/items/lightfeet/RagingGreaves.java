package model.items.lightfeet;

import model.items.Armor;

public class RagingGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RagingGreaves()
	{
		this.name = "Raging Greaves";
		this.description = "";
		
		this.iID = 463;
		this.ilevel = 8;
		this.cost = 0;

		this.ihp = 20;
		this.istr = 8;
		this.iatt = 8;
		this.idef = 10;
		this.ieva = -1;
		this.imdef = 10;
		
		this.eqslot = slots.get(4).toString();

	}

}