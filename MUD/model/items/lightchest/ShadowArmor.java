package model.items.lightchest;

import model.items.Armor;

public class ShadowArmor extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ShadowArmor()
	{
		this.name = "Shadow Armor";
		this.description = "";
		
		this.iID = 322;
		this.ilevel = 6;
		this.cost = 0;

		this.ihp = 15;
		this.iagi = 6;
		this.ista = 3;
		this.iatt = 3;
		this.idef = 8;
		this.ieva = -1;
		this.imdef = 8;
		
		this.eqslot = slots.get(2).toString();

	}

}