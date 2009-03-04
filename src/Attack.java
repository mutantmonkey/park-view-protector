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
	private int damage, duration, time=0, status, statusDuration, stillTime, hits, hitDelay, reuse;
	//Target: True=Student, FLASE=Staff
	private boolean isStudent, AoE;
	private int type;
	
	private static final long serialVersionUID = 1L;
	
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
			String name, boolean isStudent, boolean AoE, int damage, int duration,
			int type, int statusEffect, int statusDuration, int stillTime,
			int hits, int hitDelay, int reuse)
	{
		super(x,y,speed);
		this.type=type;
		this.name=name;
		this.damage=damage;
		this.duration=duration;
		this.direction=direction;
		this.stillTime=stillTime;
		this.status=statusEffect;
		this.statusDuration=statusDuration;
		this.AoE=AoE;
		this.hits=hits;
		this.hitDelay=hitDelay;
		this.reuse=reuse;
		switchXY();
	}
	
	protected void updateSprite()
	{
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
	
	public int getDamage()
	{
		return damage;
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
}