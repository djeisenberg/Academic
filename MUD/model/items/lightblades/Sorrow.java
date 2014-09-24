package model.items.lightblades;

import model.items.Weapon;

public class Sorrow extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Sorrow()
	{
		this.name = "Sorrow";
		this.description = "";
		
		this.iID = 304;
		this.ilevel = 10;
		this.cost = 0;

		this.iagi = 5;
		this.iatt = 10;
		this.iamod = 3.5;
		
		this.eqslot = slots.get(0).toString();
	}

}