package model.items.largesword;

import model.items.Weapon;

public class DefenderSword extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DefenderSword()
	{
		this.name = "Defender Sword";
		this.description = "";
		
		this.iID = 102;
		this.ilevel = 6;
		this.cost = 0;

		this.istr = 3;
		this.iatt = 9;
		this.iamod = 2.5;
		

		this.eqslot = slots.get(0).toString();
	}

}
