package model.items.lightchest;

import model.items.Armor;

public class ReapersArmor extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ReapersArmor()
	{
		this.name = "Reaper's Armor";
		this.description = "";
		
		this.iID = 421;
		this.ilevel = 4;
		this.cost = 0;

		this.ihp = 10;
		this.istr = 4;
		this.iatt = 4;
		this.idef = 6;
		this.ieva = -1;
		this.imdef = 6;
		
		this.eqslot = slots.get(2).toString();

	}

}