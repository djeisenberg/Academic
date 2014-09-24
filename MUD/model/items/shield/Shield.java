package model.items.shield;

import model.items.Armor;

public class Shield extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Shield()
	{
		this.name = "Shield";
		this.description = "";
		
		this.iID = 9;
		this.ilevel = 1;
		this.cost = 0;

		this.idef = 3;
		this.imdef = 1;
		
		this.eqslot = slots.get(1).toString();

	}

}