package model.items.lightfeet;

import model.items.Armor;

public class DisplacingGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DisplacingGreaves()
	{
		this.name = "Displacing Greaves";
		this.description = "";
		
		this.iID = 361;
		this.ilevel = 4;
		this.cost = 0;

		this.ihp = 10;
		this.iagi = 4;
		this.ista = 2;
		this.iatt = 2;
		this.idef = 6;
		this.ieva = -1;
		this.imdef = 6;
		
		this.eqslot = slots.get(4).toString();

	}

}