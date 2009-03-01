/**
 * Class to handle everything associated with Student, Staff, and Cupple
 *
 * @author	Jamie of the Javateerz
 */

import java.util.ArrayList;

public abstract class Character extends Movable
{
	// stats
	protected int hp;
	protected int maxHp;
	protected int damage;
	protected int hitDelay;
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Stores the inventory for the character
	 */
	public ArrayList<Item> inventory;
	
	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param hp
	 * @param maxHp
	 * @param speed
	 * @param damage
	 */
	public Character(int x, int y, int hp, int maxHp, double speed, int damage)
	{
		super(x, y, speed);
		
		this.hp		= hp;
		this.maxHp	= maxHp;
		this.damage	= damage;
	}
	
	/**
	 * Returns the amount of HP that this character has
	 * 
	 * @return Amount of HP
	 */
	public int getHp()
	{
		return hp;
	}
	
	public void decrementHitDelay(int amount)
	{
		if(hitDelay>0)
		{
			hitDelay-=amount;
		}
	}
	
	public void setHitDelay(int amount)
	{
		hitDelay=amount;
	}
	
	public boolean isHittable()
	{
		if(hitDelay<=0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Decreases the HP by the specified amount
	 * 
	 * @param amount Amount of HP to subtract
	 * @return New amount of HP
	 */
	public int adjustHp(int amount)
	{
		hp		   -= amount;
		
		return hp;
	}
	
	/**
	 * Returns the maximum amount of HP the character can have
	 * 
	 * @return Amount of max HP
	 */
	public int getMaxHp()
	{
		return maxHp;
	}
	
	/**
	 * Does an attack
	 */
	public abstract void attack();
	
	/**
	 * Removes an item from the character's inventory
	 * 
	 * @param i Index of item in the ArrayList
	 */
	public void dropItem(int i)
	{
		inventory.remove(i);
	}
	
	/**
	 * Adds an item to the character's inventory
	 * 
	 * @param item Item to add
	 */
	public void pickItem(Item item)
	{
		inventory.add(item);
	}
	
	protected void validateState()
	{
		super.validateState();
		
		if(hp > maxHp)
		{
			throw new IllegalArgumentException("HP cannot exceed max HP");
		}
	}
}