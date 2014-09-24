package model.items.clothlegs;

import model.items.Armor;

public class TitaniasLeggings extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TitaniasLeggings()
	{
		this.name = "Titania's Leggings";
		this.description = "";
		
		this.iID = 540;
		this.ilevel = 10;
		this.cost = 0;

		this.ihp = 15;
		this.imp = 50;
		this.imag = 10;
		this.idef = 6;
		this.imdef = 18;
		
		this.eqslot = slots.get(3).toString();

	}

}