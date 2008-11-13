/**
 * Class to handle everything associated with Student, Staff, and Cupple
 *
 * @author	Jamie of the Javateerz
 */

import java.awt.Graphics;
import java.util.ArrayList;

public abstract class Character
{
	protected int hp;
	protected int maxHp;
	protected int damage;
	
	protected double speed;
	
	// placement
	protected int x;
	protected int y;
	
	public ArrayList<Item> inventory;
	
	protected Sprite sprite;
	
	public Character(int x, int y, int hp, int maxHp, double speed, int damage)
	{
		this.x		= x;
		this.y		= y;
		
		this.hp		= hp;
		this.maxHp	= maxHp;
		this.damage	= damage;
		
		this.speed	= speed;
		
		this.sprite	= DataStore.INSTANCE.getSprite("images/placeholder.gif");
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
	 * Returns the speed of the character
	 * 
	 * @return Speed
	 */
	public double getSpeed()
	{
		return speed;
	}
	
	/**
	 * Changes the character's speed
	 * 
	 * @param change New speed
	 */
	public void changeSpeed(double change)
	{
		speed = change;
	}
	
	/**
	 * Moves the student a distance (which is multiplied by speed)
	 * 
	 * @param distX X component
	 * @param distY Y component
	 */
	public void move(int distX, int distY)
	{
		x		   += distX * speed;
		y		   += distY * speed;
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
	
	/**
	 * Called by main game loop, draws the character's sprite on the screen
	 */
	public void draw(Graphics g)
	{
		sprite.draw(g, x, y);
	}
}