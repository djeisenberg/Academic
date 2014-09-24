package model.items.useables;

import model.creatures.Player;
import model.items.Consumable;

public class Potion extends Consumable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Potion() {
		this.name = "Potion";
	}
	
	@Override
	public void use(Player player) {
		player.setCurrentHP(player.getCurrentHP() + 100);
		
	}

}
