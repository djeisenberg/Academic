package model.items.largesword;

import model.items.Weapon;

public class Ascalon extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Ascalon()
	{
		this.name = "Ascalon";
		this.description = "";
		
		this.iID = 104;
		this.ilevel = 10;
		this.cost = 0;

		this.istr = 5;
		this.iatt = 15;
		this.iamod = 3.5;
		

		this.eqslot = slots.get(0).toString();
	}

}
