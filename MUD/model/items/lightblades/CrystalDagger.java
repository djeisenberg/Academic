package model.items.lightblades;

import model.items.Weapon;

public class CrystalDagger extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CrystalDagger()
	{
		this.name = "Crystal Dagger";
		this.description = "";
		
		this.iID = 303;
		this.ilevel = 8;
		this.cost = 0;

		this.iagi = 4;
		this.iatt = 8;
		this.iamod = 3;
		
		this.eqslot = slots.get(0).toString();
	}

}