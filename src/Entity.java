/**
 * Park View Protector
 *
 * @author	Javateerz
 */

import java.awt.*;
import java.util.ArrayList;

public abstract class Entity
{
	public int hp;
	public int maxHp;
	
	private int x;
	private int y;
	
	public double speed;
	
	public ArrayList<Item> inventory;
	
	public Entity(int x, int y, int hp, int maxHp, double speed)
	{
		this.x		= x;
		this.y		= y;
		
		this.hp		= hp;
		this.maxHp	= maxHp;
		this.speed	= speed;
	}
	
	public void move()
	{
		
	}
	
	public void dropItem()
	{
		
	}
	
	public void pickItem()
	{
		
	}
}