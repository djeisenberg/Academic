package model.items.lightlegs;

import model.items.Armor;

public class FiachasLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FiachasLeggings()
	{
		this.name = "Fiacha's Leggings";
		this.description = "";
		
		this.iID = 344;
		this.ilevel = 10;
		this.cost = 0;

		this.ihp = 25;
		this.iagi = 10;
		this.ista = 5;
		this.iatt = 5;
		this.idef = 12;
		this.ieva = -1;
		this.imdef = 12;
		
		this.eqslot = slots.get(3).toString();

	}

}