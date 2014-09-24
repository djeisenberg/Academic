package model.items.lightfeet;

import model.items.Armor;

public class ShadowGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ShadowGreaves()
	{
		this.name = "Shadow Greaves";
		this.description = "";
		
		this.iID = 362;
		this.ilevel = 6;
		this.cost = 0;

		this.ihp = 15;
		this.iagi = 6;
		this.ista = 3;
		this.iatt = 3;
		this.idef = 8;
		this.ieva = -1;
		this.imdef = 8;
		
		this.eqslot = slots.get(4).toString();

	}

}