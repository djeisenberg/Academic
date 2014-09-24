package model.world;

import java.util.List;

import model.creatures.Monster;

/**
 * Interface declares a zone's getters.
 * 
 * @author dje
 *
 */
public interface Zone {

	public int getDungeonID();

	public List<Monster> listMobs();

	public List<Room> listRooms();

	public Room getEntrance();

}