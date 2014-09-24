package model.items.clothfeet;

import model.items.Armor;

public class WizardsGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WizardsGreaves()
	{
		this.name = "Wizard's Greaves";
		this.description = "";
		
		this.iID = 763;
		this.ilevel = 8;
		this.cost = 0;

		this.ihp = 12;
		this.imp = 40;
		this.imag = 8;
		this.idef = 5;
		this.imdef = 15;
		
		this.eqslot = slots.get(4).toString();

	}

}