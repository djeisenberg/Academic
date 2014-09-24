package model.items.clothchest;

import model.items.Armor;

public class LightRobes extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LightRobes()
	{
		this.name = "Light Robes";
		this.description = "";
		
		this.iID = 721;
		this.ilevel = 4;
		this.cost = 0;

		this.ihp = 6;
		this.imp = 20;
		this.imag = 4;
		this.idef = 3;
		this.imdef = 9;
		
		this.eqslot = slots.get(2).toString();

	}

}