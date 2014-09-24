package model.items.heavylegs;

import model.items.Armor;

public class DanusLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DanusLeggings()
	{
		this.name = "Danu's Leggings";
		this.description = "";
		
		this.iID = 244;
		this.ilevel = 10;
		this.cost = 0;

		this.ihp = 25;
		this.imp = 25;
		this.ista = 10;
		this.imag = 5;
		this.idef = 18;
		this.ieva = -2;
		this.imdef = 10;
		
		this.eqslot = slots.get(3).toString();

	}

}