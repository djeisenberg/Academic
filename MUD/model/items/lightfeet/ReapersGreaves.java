package model.items.lightfeet;

import model.items.Armor;

public class ReapersGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ReapersGreaves()
	{
		this.name = "Reapers Greaves";
		this.description = "";
		
		this.iID = 461;
		this.ilevel = 4;
		this.cost = 0;

		this.ihp = 10;
		this.istr = 4;
		this.iatt = 4;
		this.idef = 6;
		this.ieva = -1;
		this.imdef = 6;
		
		this.eqslot = slots.get(4).toString();

	}

}