package model.items.heavylegs;

import model.items.Armor;

public class DragonLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DragonLeggings()
	{
		this.name = "Dragon Leggings";
		this.description = "";
		
		this.iID = 143;
		this.ilevel = 8;
		this.cost = 0;

		this.ihp = 40;
		this.istr = 4;
		this.ista = 12;
		this.idef = 15;
		this.ieva = -2;
		this.imdef = 5;
		
		this.eqslot = slots.get(3).toString();

	}

}