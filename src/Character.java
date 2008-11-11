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
	
	protected double speed;
	
	// placement
	private int x;
	private int y;
	
	public ArrayList<Item> inventory;
	
	protected Sprite sprite;
	
	public Character(int x, int y, int hp, int maxHp, double speed)
	{
		this.x		= x;
		this.y		= y;
		
		this.hp		= hp;
		this.maxHp	= maxHp;
		this.speed	= speed;
		
		//this.sprite	= DataStore.INSTANCE.getSprite("images/placeholder.gif");
	}
	
	public int getHp()
	{
		return hp;
	}
	
	public void adjustHp(int amount)
	{
		hp		   -= amount;
	}
	
	public void move(int distX, int distY)
	{
		x		   += distX;
		y		   += distY;
	}
	
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