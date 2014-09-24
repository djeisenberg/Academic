package model.items;

import model.creatures.Player;

/**
 * Interface defines methods for equipable objects
 * 
 * @author Steven Chandler
 *
 */
public interface Equipable{
	
	public void Equip(Player player);
	public void Dequip(Player player);
	public String getSlot();

}
