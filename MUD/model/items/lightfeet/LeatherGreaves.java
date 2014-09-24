package model.items.lightfeet;

import model.items.Armor;

public class LeatherGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LeatherGreaves()
	{
		this.name = "Leather Greaves";
		this.description = "";
		
		this.iID = 12;
		this.ilevel = 1;
		this.cost = 0;

		this.idef = 2;
		this.ieva = -1;
		this.imdef = 2;
		
		this.eqslot = slots.get(4).toString();

	}

}