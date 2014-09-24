package model.items.clothlegs;

import model.items.Armor;

public class LightLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LightLeggings()
	{
		this.name = "Light Leggings";
		this.description = "";
		
		this.iID = 741;
		this.ilevel = 4;
		this.cost = 0;

		this.ihp = 6;
		this.imp = 20;
		this.imag = 4;
		this.idef = 2;
		this.imdef = 9;
		
		this.eqslot = slots.get(3).toString();

	}

}