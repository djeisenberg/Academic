package model.items.clothfeet;

import model.items.Armor;

public class ClothGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ClothGreaves()
	{
		this.name = "Cloth Greaves";
		this.description = "";
		
		this.iID = 15;
		this.ilevel = 1;
		this.cost = 0;

		this.idef = 1;
		this.imdef = 3;
		
		this.eqslot = slots.get(4).toString();

	}

}