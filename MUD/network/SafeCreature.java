package network;

/**
 * Interface for a safe model to be sent to the client
 * representing a creature 
 * 
 * @author james_carpenter
 *
 */
public interface SafeCreature {
	
	public String getName();
	
	public int getHP();

	public int getMP();
	
	public int getLevel();
	
	public int getAttack();
	
	public int getDefense();
	
	public int getEvasion();
	
	public int getMDefense();
	
	public int getStrength();
	
	public int getAgility();

	public int getStamina();

	public int getMagic();

}
