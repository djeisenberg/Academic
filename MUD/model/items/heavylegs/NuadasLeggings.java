package model.items.heavylegs;

import model.items.Armor;

public class NuadasLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NuadasLeggings()
	{
		this.name = "Nuada's Leggings";
		this.description = "";
		
		this.iID = 144;
		this.ilevel = 10;
		this.cost = 0;

		this.ihp = 50;
		this.istr = 5;
		this.ista = 15;
		this.idef = 18;
		this.ieva = -2;
		this.imdef = 6;
		
		this.eqslot = slots.get(3).toString();

	}

}