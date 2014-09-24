package model.items.useables;

import model.creatures.Player;
import model.items.Consumable;

public class Rejuvination extends Consumable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Rejuvination() {
		this.name = "Rejuvination";
	}
	
	@Override
	public void use(Player player) {
		player.setCurrentHP(player.getCurrentHP() + 200);
		player.setCurrentMP(player.getCurrentMP() + 100);
		
	}

}
