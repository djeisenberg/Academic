package model.items.lightchest;

import model.items.Armor;

public class ChaosArmor extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ChaosArmor()
	{
		this.name = "Chaos Armor";
		this.description = "";
		
		this.iID = 422;
		this.ilevel = 6;
		this.cost = 0;

		this.ihp = 15;
		this.istr = 6;
		this.iatt = 6;
		this.idef = 8;
		this.ieva = -1;
		this.imdef = 8;
		
		this.eqslot = slots.get(2).toString();

	}

}