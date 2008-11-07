/**
 * Park View Protector
 *
 * @author	Javateerz
 */

import java.util.ArrayList;

public class Entity
{
	public int hp;
	public int maxHp;
	
	public double speed;
	
	public ArrayList<Item> inventory;
	
	public Entity(int h, int maxH, double spd)
	{
		hp		= h;
		maxHp	= maxH;
		speed	= spd;
	}
	
	public void move()
	{
		
	}
	
	public void turn()
	{
		
	}
}