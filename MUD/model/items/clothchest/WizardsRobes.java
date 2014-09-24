package model.items.clothchest;

import model.items.Armor;

public class WizardsRobes extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WizardsRobes()
	{
		this.name = "Wizard's Robe";
		this.description = "";
		
		this.iID = 723;
		this.ilevel = 8;
		this.cost = 0;

		this.ihp = 12;
		this.imp = 40;
		this.imag = 8;
		this.idef = 5;
		this.imdef = 15;
		
		this.eqslot = slots.get(2).toString();

	}

}