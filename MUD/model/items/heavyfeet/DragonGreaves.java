package model.items.heavyfeet;

import model.items.Armor;

public class DragonGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DragonGreaves()
	{
		this.name = "Dragon Greaves";
		this.description = "";
		
		this.iID = 163;
		this.ilevel = 8;
		this.cost = 0;

		this.ihp = 40;
		this.istr = 4;
		this.ista = 12;
		this.idef = 15;
		this.ieva = -2;
		this.imdef = 5;
		
		this.eqslot = slots.get(4).toString();

	}

}