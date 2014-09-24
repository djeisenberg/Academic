package model.items.useables;

import model.creatures.Player;
import model.items.Consumable;

public class GreaterPotion extends Consumable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GreaterPotion() {
		this.name = "Greater Potion";
	}
	
	@Override
	public void use(Player player) {
		player.setCurrentHP(player.getCurrentHP() + 250);
		
	}

}
