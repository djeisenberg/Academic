package model.items.heavylegs;

import model.items.Armor;

public class SidheLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SidheLeggings()
	{
		this.name = "Sidhe Leggings";
		this.description = "";
		
		this.iID = 242;
		this.ilevel = 6;
		this.cost = 0;

		this.ihp = 15;
		this.imp = 15;
		this.ista = 6;
		this.imag = 3;
		this.idef = 12;
		this.ieva = -2;
		this.imdef = 6;
		
		this.eqslot = slots.get(3).toString();

	}

}