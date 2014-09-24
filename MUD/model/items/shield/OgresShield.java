package model.items.shield;

import model.items.Armor;

public class OgresShield extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OgresShield()
	{
		this.name = "Ogre's Shield";
		this.description = "";
		
		this.iID = 801;
		this.ilevel = 4;
		this.cost = 0;

		this.idef = 9;
		this.imdef = 3;
		
		this.eqslot = slots.get(1).toString();

	}

}