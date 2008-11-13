/**
 * Park View Protector
 *
 * @author	Javateerz
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
	
	public int getHp()
	{
		return hp;
	}
	
	/**
	 * 
	 * 
	 * @param amount
	 * @return
	 */
	public int adjustHp(int amount)
	{
		hp		   -= amount;
		
		return hp;
	}
	
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
	
	public void dropItem(int i)
	{
		inventory.remove(i);
	}
	
	public void pickItem(Item item)
	{
		inventory.add(item);
	}
	
	/**
	 * Called by graphics API, draw the entity
	 */
	public void draw(Graphics g)
	{
		sprite.draw(g, x, y);
	}
}