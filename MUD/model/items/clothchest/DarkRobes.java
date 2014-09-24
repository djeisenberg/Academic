package model.items.clothchest;

import model.items.Armor;

public class DarkRobes extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DarkRobes()
	{
		this.name = "Dark Robes";
		this.description = "";
		
		this.iID = 722;
		this.ilevel = 6;
		this.cost = 0;

		this.ihp = 9;
		this.imp = 30;
		this.imag = 6;
		this.idef = 4;
		this.imdef = 12;
		
		this.eqslot = slots.get(2).toString();

	}

}