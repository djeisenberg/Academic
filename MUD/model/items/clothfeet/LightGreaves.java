package model.items.clothfeet;

import model.items.Armor;

public class LightGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LightGreaves()
	{
		this.name = "Light Greaves";
		this.description = "";
		
		this.iID = 761;
		this.ilevel = 4;
		this.cost = 0;

		this.ihp = 6;
		this.imp = 20;
		this.imag = 4;
		this.idef = 3;
		this.imdef = 9;
		
		this.eqslot = slots.get(4).toString();

	}

}