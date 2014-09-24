package model.items.lightchest;

import model.items.Armor;

public class RagingArmor extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RagingArmor()
	{
		this.name = "Raging Armor";
		this.description = "";
		
		this.iID = 423;
		this.ilevel = 8;
		this.cost = 0;

		this.ihp = 20;
		this.istr = 8;
		this.iatt = 8;
		this.idef = 10;
		this.ieva = -1;
		this.imdef = 10;
		
		this.eqslot = slots.get(2).toString();

	}

}