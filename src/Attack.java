import java.util.ArrayList;
import java.awt.Rectangle;

/**
 * Park View Protector
 *
 * @author	Javateerz
 * 
 * Creates an attack area
 */

public class Attack
{
	private int x;
	private int y;
	private int xSize;
	private int ySize;	
	private int direct;
	private int damage;
	//Target: True=Student, FLASE=Staff
	private boolean isStudent;
	/*
	 * Shape of the attack?
	 * 0=Front
	 * 1=Surrounding
	 * ect.
	 */
	private int type;
	
	public Attack(int x, int y, int direct, int damage, boolean isStudent, int type)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
		this.damage=damage;
		this.isStudent=isStudent;
		this.type=type;
	}
	
	public ArrayList detect()
	{
		ArrayList targets;
		if(isStudent)
		{
			switch(type)
			{
				//type of attacks to impletmented here.
				case 0:
					x-=10;
					y-=10;
					xSize=40;
					ySize=40;
					break;
			}
		}
		else
		{
			switch(type)
			{
				case 0:
					break;
			}
		}
		return targets;
	}
	
	public void dealDamage()
	{
		ArrayList targets=detect();
		for(int i; i<targets.size(); i++)
		{
			targets.get(i).adjustHP(damage);
		}
	}
	public Rectangle getBounds()
	{
		Rectangle rect=new Rectangle(x, y, xSize, ySize);
		return rect;
	}
}
