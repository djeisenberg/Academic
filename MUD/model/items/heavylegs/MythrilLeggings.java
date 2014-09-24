package model.items.heavylegs;

import model.items.Armor;

public class MythrilLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MythrilLeggings()
	{
		this.name = "Mythril Leggings";
		this.description = "";
		
		this.iID = 241;
		this.ilevel = 4;
		this.cost = 0;

		this.ihp = 10;
		this.imp = 10;
		this.ista = 4;
		this.imag = 2;
		this.idef = 9;
		this.ieva = -2;
		this.imdef = 4;
		
		this.eqslot = slots.get(3).toString();

	}

}