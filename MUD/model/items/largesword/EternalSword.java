package model.items.largesword;

import model.items.Weapon;

public class EternalSword extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EternalSword()
	{
		this.name = "Eternal Sword";
		this.description = "";
		
		this.iID = 103;
		this.ilevel = 8;
		this.cost = 0;

		this.istr = 4;
		this.iatt = 12;
		this.iamod = 3;
		

		this.eqslot = slots.get(0).toString();
	}

}
