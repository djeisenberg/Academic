package model.items.heavylegs;

import model.items.Armor;

public class AdamantiteLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AdamantiteLeggings()
	{
		this.name = "Adamantite Leggings";
		this.description = "";
		
		this.iID = 142;
		this.ilevel = 6;
		this.cost = 0;

		this.ihp = 30;
		this.istr = 3;
		this.ista = 9;
		this.idef = 12;
		this.ieva = -2;
		this.imdef = 4;
		
		this.eqslot = slots.get(3).toString();

	}

}