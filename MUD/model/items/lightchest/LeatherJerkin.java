package model.items.lightchest;

import model.items.Armor;

public class LeatherJerkin extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LeatherJerkin()
	{
		this.name = "Leather Jerkin";
		this.description = "";
		
		this.iID = 10;
		this.ilevel = 1;
		this.cost = 0;

		this.idef = 2;
		this.ieva = -1;
		this.imdef = 2;
		
		this.eqslot = slots.get(2).toString();

	}

}