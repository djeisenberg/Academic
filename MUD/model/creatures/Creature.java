package model.creatures;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;

import javax.swing.ImageIcon;

import network.SafeCreature;

import model.world.Room;


/**
 * 
 * @author Steven Chandler
 * 
 */
public abstract class Creature implements Serializable, SafeCreature {
	
	private static final long serialVersionUID = 1L;
	protected String name;
	protected int strength;
	protected int agility;
	protected int stamina;
	protected int magic;

	protected int attack;
	protected int defense;
	protected int evasion;
	protected int magicDefense;

	protected int hp;
	protected int currenthp;
	protected int mp;
	protected int currentmp;
	protected int level;
	protected double attackModifier;
	protected double baseAttackModifier = 0.25;
	protected int gold = 0;
	
	protected HashMap<String, String> abilities;
	protected int immunities = 0;
	protected transient Room location;
	protected String img = "";


	protected long lastActed; // timestamp generated when last action was taken
	protected int delay; // interval from lastActed until next action may be
							// taken, in milliseconds

	public Creature() {
		lastActed = 0;
		delay = 3000;
	}

	public String getImage(){
		return img;
	}
	
	public int getCurrentHP()
	{
		return currenthp;
	}
	
	public int getCurrentMP()
	{
		return currentmp;
	}
	
	public void setCurrentHP(int php)
	{
		currenthp = php;
		if(currenthp > hp)
			currenthp = hp;
	}
	
	public void setCurrentMP(int pmp)
	{
		currentmp = pmp;
		if(currentmp > mp)
			currentmp = mp;
	}
	
	public Room getRoom() {
		return location;
	}
	
	public void setRoom(Room room) {
		location = room;
	}
	
	
	/**
	 * Returns this creature's name.
	 * 
	 * @return this creature's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method checks the last time this Creature acted and the delay it
	 * must wait before taking another action.
	 * 
	 * @return true if and only if this Creature is ready to act again.
	 */
	public boolean ready() {
		return lastActed == 0 || (lastActed - System.currentTimeMillis()) > delay;
	}

	public void setHP(int php){
		this.hp = php;		
	}
	
	public void setMP(int pmp){
		this.mp = pmp;
	}
	
	public void setStr(int pstr){
		this.strength = pstr;
	}
	
	public void setAgi(int pagi){
		this.agility = pagi;
	}
	
	public void setStam(int psta){
		this.stamina = psta;
	}
	
	public void setMag(int pmag){
		this.magic = pmag;
	}
	
	public void setAtt(int patt){
		this.attack = patt;
	}
	
	public void setDef(int pdef){
		this.defense = pdef;
	}
	
	public void setEva(int peva){
		this.evasion = peva;
	}
	
	public void setMDef(int pmdef){
		this.magicDefense = pmdef;
	}
	
	public void setAttackModifier(double pamod){
		this.attackModifier = pamod;		
	}
	
	public int getHP(){
		return hp;
	}

	public int getMP(){
		return mp;
	}
	
	public int getLevel(){
		return level;
	}
	
	public double getAttackModifier(){
		return attackModifier;
	}
	
	public double getBaseAttackModifier(){
		return baseAttackModifier;
	}
	
	public int getAttack() {
		return attack;
	}
	
	public int getDefense(){
		return defense;
	}
	
	public int getEvasion(){
		return evasion;
	}
	
	public int getMDefense(){
		return magicDefense;
	}
	
	public int getStrength(){
		return strength;
	}
	
	public int getAgility(){
		return agility;
	}

	public int getStamina(){
		return stamina;
	}

	public int getMagic(){
		return magic;
	}

	public int getImmunities()
	{
		return immunities;
	}
	
	public void setLastActed(long last) {
		lastActed = last;
	}

	public void damage(int damage) {
		currenthp -= damage;
	}
	
	public void heal() {
		currenthp += hp / 4;
		if (currenthp > hp)
			currenthp = hp;
	}

}
