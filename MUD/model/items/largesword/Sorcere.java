package model.items.largesword;

import model.items.Weapon;

public class Sorcere extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Sorcere()
	{
		this.name = "Sorcere";
		this.description = "";
		
		this.iID = 204;
		this.ilevel = 10;
		this.cost = 0;

		this.imag = 5;
		this.iatt = 10;
		this.imdef = 5;
		this.iamod = 3.5;
		

		this.eqslot = slots.get(0).toString();
	}

}
