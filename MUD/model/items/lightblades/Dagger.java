package model.items.lightblades;

import model.items.Weapon;

public class Dagger extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Dagger()
	{
		this.name = "Dagger";
		this.description = "";
		
		this.iID = 2;
		this.ilevel = 1;
		this.cost = 0;

		this.iamod = 1;

		this.eqslot = slots.get(0).toString();
	}

}
