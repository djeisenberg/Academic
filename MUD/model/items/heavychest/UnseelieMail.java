package model.items.heavychest;

import model.items.Armor;

public class UnseelieMail extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnseelieMail()
	{
		this.name = "Unseelie Mail";
		this.description = "";
		
		this.iID = 223;
		this.ilevel = 8;
		this.cost = 0;

		this.ihp = 20;
		this.imp = 20;
		this.ista = 8;
		this.imag = 4;
		this.idef = 15;
		this.ieva = -2;
		this.imdef = 8;
		
		this.eqslot = slots.get(2).toString();

	}

}