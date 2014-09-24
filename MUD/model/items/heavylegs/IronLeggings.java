package model.items.heavylegs;

import model.items.Armor;

public class IronLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IronLeggings()
	{
		this.name = "Iron Leggings";
		this.description = "";
		
		this.iID = 140;
		this.ilevel = 2;
		this.cost = 0;

		this.ihp = 10;
		this.istr = 1;
		this.ista = 3;
		this.idef = 6;
		this.ieva = -2;
		this.imdef = 2;
		
		this.eqslot = slots.get(3).toString();

	}

}