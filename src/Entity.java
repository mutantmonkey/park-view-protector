/**
 * Park View Protector
 *
 * @author	Javateerz
 */

import java.util.ArrayList;

public class Entity
{
	public int hp;
	public double speed;
	
	public ArrayList<Item> inventory;
	
	public Entity(int health, double spd)
	{
		hp		= health;
		speed	= spd;
	}
}