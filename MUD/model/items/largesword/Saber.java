package model.items.largesword;

import model.items.Weapon;

public class Saber extends Weapon
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public Saber()
	{
		this.name = "Saber";
		this.description = "";
		
		this.iID = 16;
		this.ilevel = 1;
		this.cost = 0;

		this.iamod = 1;
		

		this.eqslot = slots.get(0).toString();
	}
}
