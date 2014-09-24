package model.items;

import model.creatures.Player;

/**
 * Class begins the weapon objects
 * 
 * @author Steven Chandler
 *
 */
public abstract class Weapon extends Item implements Equipable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void Equip(Player player){
		player.setHP(player.getHP() + this.ihp);
		player.setCurrentHP(player.getCurrentHP() + this.ihp);
		player.setMP(player.getMP() + this.imp);
		player.setCurrentMP(player.getCurrentMP() + this.imp);
		player.setStr(player.getStrength() + this.istr);
		player.setAgi(player.getAgility() + this.iagi);
		player.setStam(player.getStamina() + this.ista);
		player.setMag(player.getMagic() + this.imag);
		player.setAtt(player.getAttack() + this.iatt);
		player.setDef(player.getDefense() + this.idef);
		player.setEva(player.getEvasion() + this.ieva);
		player.setMDef(player.getMDefense() + this.imdef);
		player.setAttackModifier(this.iamod);
	}
	
	@Override
	public void Dequip(Player player)
	{
		player.setHP(player.getHP() - this.ihp);
		if(player.getCurrentHP() > player.getHP())
			player.setCurrentHP(player.getHP());
		
		player.setMP(player.getMP() - this.imp);
		if(player.getCurrentMP() > player.getMP())
			player.setCurrentMP(player.getMP());
		
		player.setStr(player.getStrength() - this.istr);
		player.setAgi(player.getAgility() - this.iagi);
		player.setStam(player.getStamina() - this.ista);
		player.setMag(player.getMagic() - this.imag);
		player.setAtt(player.getAttack() - this.iatt);
		player.setDef(player.getDefense() - this.idef);
		player.setEva(player.getEvasion() - this.ieva);
		player.setMDef(player.getMDefense() - this.imdef);
		player.setAttackModifier(player.getBaseAttackModifier());
	}
	
	@Override
	public String getSlot()
	{
		return this.eqslot;
	}

}
