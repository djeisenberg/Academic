package model.items.heavychest;

import model.items.Armor;

public class ChainMail extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ChainMail()
	{
		this.name = "Chain Mail";
		this.description = "";
		
		this.iID = 6;
		this.ilevel = 1;
		this.cost = 0;

		this.idef = 3;
		this.ieva = -2;
		this.imdef = 1;
		
		this.eqslot = slots.get(2).toString();

	}

}