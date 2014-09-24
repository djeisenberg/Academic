package model.items.heavyfeet;

import model.items.Armor;

public class MythrilGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MythrilGreaves()
	{
		this.name = "Mythril Greaves";
		this.description = "";
		
		this.iID = 261;
		this.ilevel = 4;
		this.cost = 0;

		this.ihp = 10;
		this.imp = 10;
		this.ista = 4;
		this.imag = 2;
		this.idef = 9;
		this.ieva = -2;
		this.imdef = 4;
		
		this.eqslot = slots.get(4).toString();

	}

}