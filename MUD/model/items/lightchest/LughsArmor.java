package model.items.lightchest;

import model.items.Armor;

public class LughsArmor extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LughsArmor()
	{
		this.name = "Lugh Armor";
		this.description = "";
		
		this.iID = 424;
		this.ilevel = 10;
		this.cost = 0;

		this.ihp = 25;
		this.istr = 10;
		this.iatt = 10;
		this.idef = 12;
		this.ieva = -1;
		this.imdef = 12;
		
		this.eqslot = slots.get(2).toString();

	}

}