package model.items.largesword;

import model.items.Weapon;

public class LongSword extends Weapon
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public LongSword()
	{
		this.name = "Long Sword";
		this.description = "";
		
		this.iID = 1;
		this.ilevel = 1;
		this.cost = 0;

		this.iamod = 1;
		

		this.eqslot = slots.get(0).toString();
	}
}
