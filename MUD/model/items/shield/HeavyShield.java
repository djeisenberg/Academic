package model.items.shield;

import model.items.Armor;

public class HeavyShield extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public HeavyShield()
	{
		this.name = "Heavy Shield";
		this.description = "";
		
		this.iID = 800;
		this.ilevel = 2;
		this.cost = 0;

		this.idef = 6;
		this.imdef = 2;
		
		this.eqslot = slots.get(1).toString();

	}

}