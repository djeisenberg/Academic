package model.items.lightfeet;

import model.items.Armor;

public class SuppleGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SuppleGreaves()
	{
		this.name = "Supple Greaves";
		this.description = "";
		
		this.iID = 360;
		this.ilevel = 2;
		this.cost = 0;

		this.ihp = 5;
		this.iagi = 2;
		this.ista = 1;
		this.iatt = 1;
		this.idef = 4;
		this.ieva = -1;
		this.imdef = 4;
		
		this.eqslot = slots.get(4).toString();

	}

}