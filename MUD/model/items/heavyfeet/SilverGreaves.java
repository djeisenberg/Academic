package model.items.heavyfeet;

import model.items.Armor;

public class SilverGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SilverGreaves()
	{
		this.name = "Silver Greaves";
		this.description = "";
		
		this.iID = 260;
		this.ilevel = 2;
		this.cost = 0;

		this.ihp = 5;
		this.imp = 5;
		this.ista = 2;
		this.imag = 1;
		this.idef = 6;
		this.ieva = -2;
		this.imdef = 2;
		
		this.eqslot = slots.get(4).toString();

	}

}