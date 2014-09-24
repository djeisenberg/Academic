package model.items.heavychest;

import model.items.Armor;

public class MythrilMail extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MythrilMail()
	{
		this.name = "Mythril Mail";
		this.description = "";
		
		this.iID = 221;
		this.ilevel = 4;
		this.cost = 0;

		this.ihp = 10;
		this.imp = 10;
		this.ista = 4;
		this.imag = 2;
		this.idef = 9;
		this.ieva = -2;
		this.imdef = 4;
		
		this.eqslot = slots.get(2).toString();

	}

}