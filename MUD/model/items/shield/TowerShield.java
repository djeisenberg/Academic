package model.items.shield;

import model.items.Armor;

public class TowerShield extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TowerShield()
	{
		this.name = "Tower Shield";
		this.description = "";
		
		this.iID = 803;
		this.ilevel = 8;
		this.cost = 0;

		this.idef = 15;
		this.imdef = 5;
		
		this.eqslot = slots.get(1).toString();

	}

}