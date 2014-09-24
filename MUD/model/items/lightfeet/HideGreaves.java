package model.items.lightfeet;

import model.items.Armor;

public class HideGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public HideGreaves()
	{
		this.name = "Hide Greaves";
		this.description = "";
		
		this.iID = 460;
		this.ilevel = 2;
		this.cost = 0;

		this.ihp = 5;
		this.istr = 2;
		this.iatt = 2;
		this.idef = 4;
		this.ieva = -1;
		this.imdef = 4;
		
		this.eqslot = slots.get(4).toString();

	}

}