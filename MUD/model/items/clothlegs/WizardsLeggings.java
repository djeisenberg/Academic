package model.items.clothlegs;

import model.items.Armor;

public class WizardsLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WizardsLeggings()
	{
		this.name = "Wizard's Leggings";
		this.description = "";
		
		this.iID = 743;
		this.ilevel =8;
		this.cost = 0;

		this.ihp = 12;
		this.imp = 40;
		this.imag = 8;
		this.idef = 5;
		this.imdef = 15;
		
		this.eqslot = slots.get(3).toString();

	}

}