package model.items.clothlegs;

import model.items.Armor;

public class DarkLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DarkLeggings()
	{
		this.name = "Dark Leggings";
		this.description = "";
		
		this.iID = 742;
		this.ilevel = 6;
		this.cost = 0;

		this.ihp = 9;
		this.imp = 30;
		this.imag = 6;
		this.idef = 4;
		this.imdef = 12;
		
		this.eqslot = slots.get(3).toString();

	}

}