package model.items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import model.creatures.Job;

/**
 * Class begins the item objects
 * 
 * @author Steven Chandler
 *
 */
public abstract class Item implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int iID;
	protected String name;
	protected String description;
	protected int cost;
	protected int ilevel;
	protected int istr;
	protected int iagi;
	protected int ista;
	protected int imag;
	protected int iatt;
	protected int idef;
	protected int imdef;
	protected int ieva;
	protected double iamod;
	protected int ihp;
	protected int imp;
	protected static List<ItemSlots> slots = Arrays.asList(ItemSlots.values());
	protected String eqslot = null;

	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
