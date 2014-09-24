package model.items.lightfeet;

import model.items.Armor;

public class FiachasGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FiachasGreaves()
	{
		this.name = "Fiacha's Greaves";
		this.description = "";
		
		this.iID = 364;
		this.ilevel = 10;
		this.cost = 0;

		this.ihp = 25;
		this.iagi = 10;
		this.ista = 5;
		this.iatt = 5;
		this.idef = 12;
		this.ieva = -1;
		this.imdef = 12;
		
		this.eqslot = slots.get(4).toString();

	}

}