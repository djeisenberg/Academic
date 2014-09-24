package model.items.clothfeet;

import model.items.Armor;

public class TitaniasGreaves extends Armor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TitaniasGreaves()
	{
		this.name = "Titania's Greaves";
		this.description = "";
		
		this.iID = 560;
		this.ilevel = 10;
		this.cost = 0;

		this.ihp = 15;
		this.imp = 50;
		this.imag = 10;
		this.idef = 6;
		this.imdef = 18;
		
		this.eqslot = slots.get(4).toString();

	}

}