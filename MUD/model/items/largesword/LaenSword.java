package model.items.largesword;

import model.items.Weapon;

public class LaenSword extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LaenSword()
	{
		this.name = "Laen Sword";
		this.description = "";
		
		this.iID = 202;
		this.ilevel = 6;
		this.cost = 0;

		this.imag = 3;
		this.iatt = 6;
		this.imdef = 3;
		this.iamod = 2.5;
		

		this.eqslot = slots.get(0).toString();
	}

}
