import java.util.ArrayList;
import java.awt.Rectangle;

/**
 * Park View Protector
 *
 * @author	Javateerz
 * 
 * Creates an attack area
 */

public class Attack extends Movable
{
	private int x;
	private int y;
	private double speed;
	private String name;
	private int xSize;
	private int ySize;	
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
	
	public Attack(int x, int y, double speed, String name, int direct, int damage, boolean isStudent, int type)
	{
		super(x, y, speed);
		this.name=name;
		this.direction=direct;
		this.damage=damage;
		this.isStudent=isStudent;
		this.type=type;
	}
	
	/*public ArrayList detect()
	{
		ArrayList targets;
		switch(type)
		{
			case Type.FRONT:
				break;
		}
		return targets;
	}*/
	
	public void switchXY()
	{
		int temp;
		if(direction==Direction.NORTH)
		{
			this.sprite		= DataStore.INSTANCE.getSprite("images/"+name+"_n.png");
		}
		else if(direction==Direction.SOUTH)
		{
			this.sprite		= DataStore.INSTANCE.getSprite("images/"+name+"_s.png");
		}
		else if(direction==Direction.WEST)
		{
			this.sprite		= DataStore.INSTANCE.getSprite("images/"+name+"_w.png");
		}
		else /*Implied else if for EAST*/
		{
			this.sprite		= DataStore.INSTANCE.getSprite("images/"+name+"_e.png");
		}
	}
}

class Type
{
	public static final int FRONT=0;
}