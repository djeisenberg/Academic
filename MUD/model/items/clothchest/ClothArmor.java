package model.items.clothchest;

import model.creatures.Player;
import model.items.Armor;

public class ClothArmor extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ClothArmor()
	{
		this.name = "Cloth Armor";
		this.description = "";
		
		this.iID = 13;
		this.ilevel = 1;
		this.cost = 0;


		this.idef = 1;
		this.imdef = 3;
		
		this.eqslot = slots.get(2).toString();

	}



}