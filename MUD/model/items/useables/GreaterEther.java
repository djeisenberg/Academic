package model.items.useables;

import model.creatures.Player;
import model.items.Consumable;

public class GreaterEther extends Consumable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GreaterEther() {
		this.name = "Greater Ether";
	}
	
	@Override
	public void use(Player player) {
		player.setCurrentMP(player.getCurrentMP() + 150);
		
	}

}
