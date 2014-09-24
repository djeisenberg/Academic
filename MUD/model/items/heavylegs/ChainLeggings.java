package model.items.heavylegs;

import model.items.Armor;

public class ChainLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ChainLeggings()
	{
		this.name = "Chain Leggings";
		this.description = "";
		
		this.iID = 7;
		this.ilevel = 1;
		this.cost = 0;


		this.idef = 3;
		this.ieva = -2;
		this.imdef = 1;
		
		this.eqslot = slots.get(3).toString();

	}

}