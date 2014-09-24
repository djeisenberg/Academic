package model.items.clothlegs;

import model.items.Armor;

public class ClothLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ClothLeggings()
	{
		this.name = "Cloth Leggings";
		this.description = "";
		
		this.iID = 14;
		this.ilevel = 1;
		this.cost = 0;

		this.idef = 1;
		this.imdef = 3;
		
		this.eqslot = slots.get(3).toString();

	}

}