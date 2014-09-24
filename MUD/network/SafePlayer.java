package network;

import java.util.List;

import model.items.Item;

/**
 * Interface for a safe model to be sent to the client
 * representing a player 
 * 
 * @author james_carpenter
 *
 */
public interface SafePlayer extends SafeCreature {
	
	public String getJob();
	public List<Item> itemList();

}
