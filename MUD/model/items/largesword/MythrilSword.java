package model.items.largesword;

import model.items.Weapon;

public class MythrilSword extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MythrilSword()
	{
		this.name = "Mythril Sword";
		this.description = "";
		
		this.iID = 201;
		this.ilevel = 4;
		this.cost = 0;

		this.imag = 2;
		this.iatt = 4;
		this.imdef = 2;
		this.iamod = 2;
		

		this.eqslot = slots.get(0).toString();
	}

}
