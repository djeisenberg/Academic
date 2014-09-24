package model.items.lightchest;

import model.items.Armor;

public class FiachasArmor extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FiachasArmor()
	{
		this.name = "Fiacha's Armor";
		this.description = "";
		
		this.iID = 324;
		this.ilevel = 10;
		this.cost = 0;

		this.ihp = 25;
		this.iagi = 10;
		this.ista = 5;
		this.iatt = 5;
		this.idef = 12;
		this.ieva = -1;
		this.imdef = 12;
		
		this.eqslot = slots.get(2).toString();

	}

}