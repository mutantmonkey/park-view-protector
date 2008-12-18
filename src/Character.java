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
	 * Random movement
	 * 
	 * @param speed Speed to move at
	 * @param changeMoves Number of moves to change the direction after
	 */
	public void moveRandom(int speed, int changeMoves)
	{
		// change direction if the move count exceeds the number of moves to change after
		if(moveCount <= 0 || moveCount > changeMoves)
		{
			// choose a new direction
			setDirection((int) (Math.random() * 3));
					
			resetMoveCount();
		}
		
		move(speed);
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
}