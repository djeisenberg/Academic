package model.items.largesword;

import model.items.Weapon;

public class SilverSword extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SilverSword()
	{
		this.name = "Silver Sword";
		this.description = "";
		
		this.iID = 200;
		this.ilevel = 2;
		this.cost = 0;

		this.imag = 1;
		this.iatt = 2;
		this.imdef = 1;
		this.iamod = 1.5;
		

		this.eqslot = slots.get(0).toString();
	}

}
