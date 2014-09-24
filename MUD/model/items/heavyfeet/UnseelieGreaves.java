package model.items.heavyfeet;

import model.items.Armor;

public class UnseelieGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnseelieGreaves()
	{
		this.name = "Unseelie Greaves";
		this.description = "";
		
		this.iID = 263;
		this.ilevel = 8;
		this.cost = 0;

		this.ihp = 20;
		this.imp = 20;
		this.ista = 8;
		this.imag = 4;
		this.idef = 15;
		this.ieva = -2;
		this.imdef = 8;
		
		this.eqslot = slots.get(4).toString();

	}

}