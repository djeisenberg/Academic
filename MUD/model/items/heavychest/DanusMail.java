package model.items.heavychest;

import model.items.Armor;

public class DanusMail extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DanusMail()
	{
		this.name = "Danu's Mail";
		this.description = "";
		
		this.iID = 224;
		this.ilevel = 10;
		this.cost = 0;

		this.ihp = 25;
		this.imp = 25;
		this.ista = 10;
		this.imag = 5;
		this.idef = 18;
		this.ieva = -2;
		this.imdef = 10;
		
		this.eqslot = slots.get(2).toString();

	}

}