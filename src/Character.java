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
	protected int hitDelay;
	
	private static final long serialVersionUID = 4L;
	
	/**
	 * Keeps track of items
	 * 
	 * Memory inquired
	 */
	public ItemBin bin;
	
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
	public Character(int x, int y, int hp, int maxHp, double speed)
	{
		super(x, y, speed);
		
		this.hp		= hp;
		this.maxHp	= maxHp;
		bin = new ItemBin(this);
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
	
	public void setHp(int amount)
	{
		hp=amount;
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
	
	public int getHitDelay()
	{
		return hitDelay;
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
	 * Drops the entire inventory
	 */
	
	public void dropInv()
	{
		bin.dropInv();
		//code to add the items to the field
	}
	
	/**
	 * Adds an item to the character's inventory
	 * 
	 * @param item Item to add
	 */
	public void pickItem(Item item)
	{
		bin.add(item);

		//code to remove from field...
	}
	
	/**
	 * Runs an item on the character used on, then removes it from the inventory
	 * 
	 * @param type Char
	 */
	public void useItem(char type)
	{
		bin.useItem(type);
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