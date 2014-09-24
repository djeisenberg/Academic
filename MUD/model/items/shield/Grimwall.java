package model.items.shield;

import model.items.Armor;

public class Grimwall extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Grimwall()
	{
		this.name = "Grimwall";
		this.description = "";
		
		this.iID = 804;
		this.ilevel = 10;
		this.cost = 0;

		this.idef = 18;
		this.imdef = 6;
		
		this.eqslot = slots.get(1).toString();

	}

}