package model.items.heavyfeet;

import model.items.Armor;

public class DanusGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DanusGreaves()
	{
		this.name = "Danu's Greaves";
		this.description = "";
		
		this.iID = 264;
		this.ilevel = 10;
		this.cost = 0;

		this.ihp = 25;
		this.imp = 25;
		this.ista = 10;
		this.imag = 5;
		this.idef = 18;
		this.ieva = -2;
		this.imdef = 10;
		
		this.eqslot = slots.get(4).toString();

	}

}