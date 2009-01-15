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
	private double speed;
	private String name;
	private int damage, duration, time=0;
	//Target: True=Student, FLASE=Staff
	private boolean isStudent;
	/*
	 * Shape of the attack?
	 * 0=Front
	 * 1=Surrounding
	 * ect.
	 */
	private int type;
	
	public Attack(int x, int y, double speed, String name, int direct, int damage, int duration,
			boolean isStudent, int type)
	{
		super(x+20, y+32, speed);
		this.type=type;
		this.name=name;
		this.direction=direct;
		this.damage=damage;
		this.isStudent=isStudent;
		this.duration=duration;
		switchXY();
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	public int getTime()
	{
		return time;
	}
	
	public void tick()
	{
		time++;
	}
	
	public void switchXY()
	{
		if(direction==Direction.NORTH)
		{
			this.sprite		= DataStore.INSTANCE.getSprite("images/"+name+"_n.png");
			System.out.println(getDirection());
			//move(-this.getBounds().width/4,-this.getBounds().height/4);
			x+=(int) Math.round(-getBounds().width/4 * speed);
			y+=(int) Math.round(-getBounds().height/4 * speed);
		}
		else if(direction==Direction.SOUTH)
		{
			this.sprite		= DataStore.INSTANCE.getSprite("images/"+name+"_s.png");
			System.out.println(getDirection());
			//move(-this.getBounds().width/4,-this.getBounds().height/4);
			x+=(int) Math.round(-getBounds().width/4 * speed);
			y+=(int) Math.round(-getBounds().height/4 * speed);
		}
		else if(direction==Direction.WEST)
		{
			this.sprite		= DataStore.INSTANCE.getSprite("images/"+name+"_w.png");
			System.out.println(getDirection());
			//move(-this.getBounds().width/4,-this.getBounds().height/4);
			x+=(int) Math.round(-getBounds().width/4 * speed);
			y+=(int) Math.round(-getBounds().height/4 * speed);
		}
		else /*Implied else if for EAST*/
		{
			this.sprite		= DataStore.INSTANCE.getSprite("images/"+name+"_e.png");
			System.out.println(getDirection());
			//move(-this.getBounds().width/4,-this.getBounds().height/4);
			x+=(int) Math.round(-getBounds().width/4 * speed);
			y+=(int) Math.round(-getBounds().height/4 * speed);
		}
		
		if(type==Type.FRONT)
		{
			System.out.println(getDirection());
			if(direction==Direction.EAST)
				x+=(int) Math.round(15 * speed);
			else if(direction==Direction.WEST)
				x-=(int) Math.round(15 * speed);
			else if(direction==Direction.SOUTH)
				y+=(int) Math.round(15*speed);
			else
				y-=(int) Math.round(15*speed);
		}
	}
}

class Type
{
	public static final int FRONT=0;
}