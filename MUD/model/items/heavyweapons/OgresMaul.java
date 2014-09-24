package model.items.heavyweapons;

import model.items.Weapon;

public class OgresMaul extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OgresMaul()
	{
		this.name = "Ogre's Maul";
		this.description = "";
		
		this.iID = 401;
		this.ilevel = 4;
		this.cost = 0;

		this.istr = 4;
		this.iatt = 4;
		this.iamod = 2.5;
		
		this.eqslot = slots.get(0).toString();
	}

}