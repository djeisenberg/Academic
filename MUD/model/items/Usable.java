package model.items;

import model.creatures.Player;

/**
 * Interface giving methods for operation of consumable items
 * 
 * @author Steven Chandler
 *
 */
public interface Usable {

	/**
	 * Method for using an item
	 * 
	 * @author Steven Chandler
	 *
	 */
	public void use(Player player);
}
