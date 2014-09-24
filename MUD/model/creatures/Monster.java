package model.creatures;

import java.util.LinkedList;
import java.util.List;

import model.items.Item;

/**
 * Class Monster objects
 * 
 * @author Steven Chandler
 *
 */
public abstract class Monster extends Mob{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected boolean stolenflag = false;
	public abstract Item stealList(int sroll);
	
	public LinkedList<Item> Treasure() {
		return new LinkedList<Item>();
	}
	
	public void act() {
		
	}

	protected void setToMaxHPMP() {
		currenthp = hp;
		currentmp = mp;
	}

}
