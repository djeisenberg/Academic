package model.items.clothchest;

import model.items.Armor;

public class MabsRobe extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MabsRobe()
	{
		this.name = "Mab's Robe";
		this.description = "";
		
		this.iID = 620;
		this.ilevel = 10;
		this.cost = 0;

		this.ihp = 15;
		this.imp = 50;
		this.imag = 10;
		this.idef = 6;
		this.imdef = 10;
		
		this.eqslot = slots.get(2).toString();

	}

}