/**
 * Park View Protector
 *
 * @author	Javateerz
 */

import java.util.ArrayList;

public abstract class Entity
{
	protected int hp;
	protected int maxHp;
	
	protected double speed;
	
	// placement
	private int x;
	private int y;
	
	public ArrayList<Item> inventory;
	
	protected Sprite sprite;
	
	public Entity(int x, int y, int hp, int maxHp, double speed)
	{
		this.x		= x;
		this.y		= y;
		
		this.hp		= hp;
		this.maxHp	= maxHp;
		this.speed	= speed;
	}
	
	public int getHp()
	{
		return hp;
	}
	
	public void adjustHp(int amount)
	{
		hp		   -= amount;
	}
	
	public void move(int direction)
	{
		
	}
	
	public void dropItem()
	{
		
	}
	
	public void pickItem(Item item)
	{
		inventory.add(item);
	}
}