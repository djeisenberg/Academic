package model.items.clothfeet;

import model.items.Armor;

public class RuneGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RuneGreaves()
	{
		this.name = "Rune Greaves";
		this.description = "";
		
		this.iID = 760;
		this.ilevel = 2;
		this.cost = 0;

		this.ihp = 3;
		this.imp = 10;
		this.imag = 2;
		this.idef = 2;
		this.imdef = 6;
		
		this.eqslot = slots.get(4).toString();

	}

}