package model.items;

import java.util.List;

/**
 * Class starts the container items
 * 
 * @author Steven Chandler
 * 
 */

public abstract class Container extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Item> contents;

	public boolean remove(Item get) {
		return contents.remove(get);
	}

	public boolean put(Item item) {
		if (item instanceof Container)
			return false;
		return contents.add(item);
	}

	public List<Item> getContents() {
		return contents;
	}

	public String inventory() {
		String result = "";
		for (Item i : contents)
			result += "(" + getName() + ")" + i.getName() + "\n";
		return result;
	}

}
