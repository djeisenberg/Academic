package model.items.largesword;

import model.items.Weapon;

public class EogSword extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EogSword()
	{
		this.name = "Eog Sword";
		this.description = "";
		
		this.iID = 203;
		this.ilevel = 8;
		this.cost = 0;

		this.imag = 4;
		this.iatt = 8;
		this.imdef = 4;
		this.iamod = 3;
		

		this.eqslot = slots.get(0).toString();
	}

}
