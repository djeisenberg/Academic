package model.items.lightfeet;

import model.items.Armor;

public class NightGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NightGreaves()
	{
		this.name = "Night Greaves";
		this.description = "";
		
		this.iID = 363;
		this.ilevel = 8;
		this.cost = 0;

		this.ihp = 20;
		this.iagi = 8;
		this.ista = 4;
		this.iatt = 4;
		this.idef = 10;
		this.ieva = -1;
		this.imdef = 10;
		
		this.eqslot = slots.get(4).toString();

	}

}