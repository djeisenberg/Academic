package model.items.clothfeet;

import model.items.Armor;

public class DarkGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DarkGreaves()
	{
		this.name = "Dark Greaves";
		this.description = "";
		
		this.iID = 762;
		this.ilevel = 8;
		this.cost = 0;

		this.ihp = 9;
		this.imp = 30;
		this.imag = 6;
		this.idef = 4;
		this.imdef = 12;
		
		this.eqslot = slots.get(4).toString();

	}

}