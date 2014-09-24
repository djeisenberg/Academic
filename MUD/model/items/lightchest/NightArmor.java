package model.items.lightchest;

import model.items.Armor;

public class NightArmor extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NightArmor()
	{
		this.name = "Night Armor";
		this.description = "";
		
		this.iID = 323;
		this.ilevel = 8;
		this.cost = 0;

		this.ihp = 20;
		this.iagi = 8;
		this.ista = 4;
		this.iatt = 4;
		this.idef = 10;
		this.ieva = -1;
		this.imdef = 10;
		
		this.eqslot = slots.get(2).toString();

	}

}