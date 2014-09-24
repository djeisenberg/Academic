package model.items.lightblades;

import model.items.Weapon;

public class ShadowKnife extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ShadowKnife()
	{
		this.name = "Shadow Knife";
		this.description = "";
		
		this.iID = 302;
		this.ilevel = 6;
		this.cost = 0;

		this.iagi = 3;
		this.iatt = 6;
		this.iamod = 2.5;
		
		this.eqslot = slots.get(0).toString();
	}

}