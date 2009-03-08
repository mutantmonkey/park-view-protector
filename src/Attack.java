/**
 * Park View Protector
 *
 * @author	Javateerz
 * 
 * Creates an attack area
 */

import java.util.ArrayList;
import java.awt.Rectangle;
import java.io.*;

public class Attack extends Movable
{
	private double speed;
	private String name;
	private int damage, tp, duration, time=0, status, statusDuration, stillTime, hits, hitDelay, reuse;
	//Target: True=Student, FLASE=Staff
	private boolean isStudent, AoE;
	private int type;
	
	private static final long serialVersionUID = 3L;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param speed
	 * @param direction
	 * @param name: String to find the filename
	 * @param isStudent: boolean to check if the attack damages the student of player
	 * @param damage: int of damage to deal to character
	 * @param duration: Duration of attack
	 * @param type: Where the attack appears in relation of the character
	 * @param statusEffect: What status effect is caused by this attack
	 * @param statusEffectLength: Length of the status effect
	 * @param stillTime: Time the character stands still
	 */
	public Attack(int x, int y, double speed, int direction,
			String name, boolean isStudent, boolean AoE, int damage, int tp, int duration,
			int type, int statusEffect, int statusDuration, int stillTime,
			int hits, int hitDelay, int reuse)
	{
		super(x,y,speed);
		this.type=type;
		this.name=name;
		this.damage=damage;
		this.tp=tp;
		this.duration=duration;
		this.direction=direction;
		this.stillTime=stillTime;
		this.status=statusEffect;
		this.statusDuration=statusDuration;
		this.AoE=AoE;
		this.hits=hits;
		this.hitDelay=hitDelay;
		this.reuse=reuse;
		this.isStudent=isStudent;
		switchXY();
	}
	
	protected void updateSprite()
	{
		try
		{
			if(direction==Direction.NORTH)
			{
				this.sprite		= DataStore.INSTANCE.getSprite("attack/"+name+"_n.png");
			}
			else if(direction==Direction.SOUTH)
			{
				this.sprite		= DataStore.INSTANCE.getSprite("attack/"+name+"_s.png");
			}
			else if(direction==Direction.WEST)
			{
				this.sprite		= DataStore.INSTANCE.getSprite("attack/"+name+"_w.png");
			}
			else
			{
				this.sprite		= DataStore.INSTANCE.getSprite("attack/"+name+"_e.png");
			}
		}
		catch(Exception e)
		{
			try
			{
				this.sprite		= DataStore.INSTANCE.getSprite("attack/"+name+".png");
			}
			catch(Exception e1)
			{
				if(direction==Direction.NORTH)
				{
					this.sprite		= DataStore.INSTANCE.getSprite("attack/attack"+"_n.png");
				}
				else if(direction==Direction.SOUTH)
				{
					this.sprite		= DataStore.INSTANCE.getSprite("attack/attack"+"_s.png");
				}
				else if(direction==Direction.WEST)
				{
					this.sprite		= DataStore.INSTANCE.getSprite("attack/attack"+"_w.png");
				}
				else
				{
					this.sprite		= DataStore.INSTANCE.getSprite("attack/attack"+"_e.png");
				}
			}
		}
	}

	public String getName()
	{
		return name;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public int getTp()
	{
		return tp;
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	public int getTime()
	{
		return time;
	}
	
	public int getStatus()
	{
		return status;
	}
	
	public int getStatusDuration()
	{
		return statusDuration;
	}
	
	public int getStillTime()
	{
		return stillTime;
	}
	
	public int getReuse()
	{
		return reuse;
	}
	
	public boolean isAoE()
	{
		return AoE;
	}
	
	public int getHitDelay()
	{
		return hitDelay;
	}
	
	public void move(int dist)
	{
		super.move(dist);
		time++;
	}
	
	public boolean over()
	{
		if(time>duration)
			return true;
		return false;
	}
	
	public void switchXY()
	{
		updateSprite();
		
		x = (x+10)-(int) this.getBounds().getWidth()/4;
		y = (y+16)-(int) this.getBounds().getHeight()/4;
		
		if(type==Type.FRONT)
		{
			if(direction==Direction.EAST)
				x+=(int) Math.round(30);
			else if(direction==Direction.WEST)
				x-=(int) Math.round(30);
			else if(direction==Direction.SOUTH)
				y+=(int) Math.round(30);
			else
				y-=(int) Math.round(30);
		}
		else if(type==Type.BACK)
		{
			if(direction==Direction.EAST)
				x-=(int) Math.round(30);
			else if(direction==Direction.WEST)
				x+=(int) Math.round(30);
			else if(direction==Direction.SOUTH)
				y-=(int) Math.round(30);
			else
				y+=(int) Math.round(30);
		}
		else if(type==Type.CENTER)
		{
			//Impletment never
		}
	}
	
	private void readObject(ObjectInputStream os) throws ClassNotFoundException, IOException
	{
		os.defaultReadObject();
		
		validateState();
	}
	
	private void writeObject(ObjectOutputStream os) throws IOException
	{
		os.defaultWriteObject();
	}

	public boolean isStudent()
	{
		return isStudent;
	}
}

class Type
{
	public static final int FRONT=0;
	public static final int BACK=1;
	public static final int CENTER=2;	
}

class Status
{
	public static final int STUN=1;
	public static final int POISON=2;
	public static final int IMMUNE=3;
}