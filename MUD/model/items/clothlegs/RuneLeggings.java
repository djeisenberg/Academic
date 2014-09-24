package model.items.clothlegs;

import model.items.Armor;

public class RuneLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RuneLeggings()
	{
		this.name = "Rune Leggings";
		this.description = "";
		
		this.iID = 740;
		this.ilevel = 2;
		this.cost = 0;

		this.ihp = 3;
		this.imp = 10;
		this.imag = 2;
		this.idef = 1;
		this.imdef = 6;
		
		this.eqslot = slots.get(3).toString();

	}

}