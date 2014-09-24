package model.items.useables;

import model.creatures.Player;
import model.items.Consumable;

public class Ether extends Consumable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Ether() {
		this.name = "Ether";
	}
	
	@Override
	public void use(Player player) {
		player.setCurrentMP(player.getCurrentMP() + 50);
		
	}

}
