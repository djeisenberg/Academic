package model.items.heavylegs;

import model.items.Armor;

public class SilverLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SilverLeggings()
	{
		this.name = "Silver Leggings";
		this.description = "";
		
		this.iID = 240;
		this.ilevel = 2;
		this.cost = 0;

		this.ihp = 5;
		this.imp = 5;
		this.ista = 2;
		this.imag = 1;
		this.idef = 6;
		this.ieva = -2;
		this.imdef = 2;
		
		this.eqslot = slots.get(3).toString();

	}

}