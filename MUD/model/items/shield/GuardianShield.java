package model.items.shield;

import model.items.Armor;

public class GuardianShield extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GuardianShield()
	{
		this.name = "Guardian Shield";
		this.description = "";
		
		this.iID = 802;
		this.ilevel = 6;
		this.cost = 0;
		
		this.idef = 12;
		this.imdef = 4;
		
		this.eqslot = slots.get(1).toString();

	}

}