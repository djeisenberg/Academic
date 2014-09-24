package model.items.heavychest;

import model.items.Armor;

public class SilverMail extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SilverMail()
	{
		this.name = "Silver Mail";
		this.description = "";
		
		this.iID = 220;
		this.ilevel = 2;
		this.cost = 0;

		this.ihp = 5;
		this.imp = 5;
		this.ista = 2;
		this.imag = 1;
		this.idef = 6;
		this.ieva = -2;
		this.imdef = 2;
		
		this.eqslot = slots.get(2).toString();

	}

}