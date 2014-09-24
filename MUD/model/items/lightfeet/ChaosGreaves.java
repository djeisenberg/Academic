package model.items.lightfeet;

import model.items.Armor;

public class ChaosGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ChaosGreaves()
	{
		this.name = "Chaos Greaves";
		this.description = "";
		
		this.iID = 462;
		this.ilevel = 6;
		this.cost = 0;

		this.ihp = 15;
		this.istr = 6;
		this.iatt = 6;
		this.idef = 8;
		this.ieva = -1;
		this.imdef = 8;
		
		this.eqslot = slots.get(4).toString();

	}

}