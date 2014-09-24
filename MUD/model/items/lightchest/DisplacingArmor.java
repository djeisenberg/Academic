package model.items.lightchest;

import model.items.Armor;

public class DisplacingArmor extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DisplacingArmor()
	{
		this.name = "Displacing Armor";
		this.description = "";
		
		this.iID = 321;
		this.ilevel = 4;
		this.cost = 0;

		this.ihp = 10;
		this.iagi = 4;
		this.ista = 2;
		this.iatt = 2;
		this.idef = 6;
		this.ieva = -1;
		this.imdef = 6;
		
		this.eqslot = slots.get(2).toString();

	}

}