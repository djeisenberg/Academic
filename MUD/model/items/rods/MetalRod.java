package model.items.rods;

import model.items.Weapon;

public class MetalRod extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MetalRod()
	{
		this.name = "Metal Rod";
		this.description = "";
		
		this.iID = 3;
		this.ilevel = 1;
		this.cost = 0;

		this.iamod = 1;
		
		this.eqslot = slots.get(0).toString();
	}

}