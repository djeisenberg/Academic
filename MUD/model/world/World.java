package model.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author Steven Chandler
 */

public class World implements Serializable {


	private static final long serialVersionUID = 1L;
	private City city;
	private Room spawnLocation;
	
	public World()
	{
		city = new City();
		spawnLocation = city.getEntrance();
	}
	
	public Room getSpawn() {
		return spawnLocation;
	}
	
	public City getCity() {
		return city;
	}
}
