package model.items.clothchest;

import model.items.Armor;

public class RuneArmor extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RuneArmor()
	{
		this.name = "Rune Armor";
		this.description = "";
		
		this.iID = 720;
		this.ilevel = 2;
		this.cost = 0;

		this.ihp = 3;
		this.imp = 10;
		this.imag = 2;
		this.idef = 2;
		this.imdef = 6;
		
		this.eqslot = slots.get(2).toString();

	}

}