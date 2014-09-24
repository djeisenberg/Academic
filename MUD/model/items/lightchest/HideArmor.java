package model.items.lightchest;

import model.items.Armor;

public class HideArmor extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public HideArmor()
	{
		this.name = "Hide Armor";
		this.description = "";
		
		this.iID = 420;
		this.ilevel = 2;
		this.cost = 0;

		this.ihp = 5;
		this.istr = 2;
		this.iatt = 2;
		this.idef = 4;
		this.ieva = -1;
		this.imdef = 4;
		
		this.eqslot = slots.get(2).toString();

	}

}