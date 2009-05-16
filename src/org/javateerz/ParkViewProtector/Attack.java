/**
 * Park View Protector
 *
 * @author	Javateerz
 * 
 * Creates an attack object
 */

package org.javateerz.ParkViewProtector;

import java.io.*;

public class Attack extends Movable
{
	private double 		speed;
	private String 		name;
	private int 		damage,
						tp, 
						duration, 
						time=0, 
						status, 
						statusDuration, 
						stillTime, 
						hits, 
						hitDelay, 
						reuse;
	// Target: True=Student, False=Staff
	private boolean 	isStudent, 
						AoE;
	private int 		type;
	
	private static final long serialVersionUID = 4L;
	
	/**
	 * 
	 * @param x	: The x coordinate
	 * @param y: The y coordinate
	 * @param speed: The speed the attack travels
	 * @param direction: Direction of the attack
	 * @param name: Name of the attack
	 * @param isStudent: If true, the attack effects students
	 * @param AoE: If true, the attack will not disappear upon hitting a target
	 * @param damage: The damage the attack will deal
	 * @param tp: The amount of TP the attack consumes
	 * @param duration: The duration the attack stays on screen
	 * @param type: The placement of the attack
	 * @param statusEffect: The status effect the attack induces
	 * @param statusDuration: The length of the status effect
	 * @param stillTime: The duration the attack makes the user stand still
	 * @param hits: The number of hits the attack deals
	 * @param hitDelay: The time before the target can be hit again after being hit
	 * @param reuse: The time the user can perform another attack
	 */
	public Attack(	Game g,
					double x,
					double y,
					double speed,
					int direction,
					String name,
					boolean isStudent,
					boolean AoE,
					int damage,
					int tp,
					int duration,
					int type,
					int statusEffect,
					int statusDuration,
					int stillTime,
					int hits,
					int hitDelay,
					int reuse)
	{
		super(g, x, y, speed);
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
	
	public Attack(	Game g,
			double x,
			double y,
			double speed,
			int direction,
			String name,
			boolean isStudent,
			boolean AoE,
			int damage,
			int duration,
			int type,
			int statusEffect,
			int statusDuration,
			int stillTime,
			int hits,
			int hitDelay,
			int reuse)
		{
			super(g, x, y, speed);
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
			this.isStudent=isStudent;
			switchXY();
		}
	
	/**
	 * Sets the attack's graphic
	 */
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

	/**
	 * @return The name of the attack
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return The damage the attack deals
	 */
	public int getDamage()
	{
		return damage;
	}
	
	/**
	 * @return The duration the attack stays on screen
	 */
	public int getDuration()
	{
		return duration;
	}

	/**
	 * @return The duration of the status effect
	 */
	public int getStatusDuration()
	{
		return statusDuration;
	}

	/**
	 * @return The duration that the user cannot perform an action
	 */
	public int getStillTime()
	{
		return stillTime;
	}

	/**
	 * @return The TP attack consumes
	 */
	public int getTp()
	{
		return tp;
	}
	
	/**
	 * @return The status effect the attack induces
	 */
	public int getStatus()
	{
		return status;
	}
	
	/**
	 * @return The duration before the user can perform another attack
	 */
	public int getReuse()
	{
		return reuse;
	}
	
	/**
	 * @return If true, the attack will not disappear upon hitting a target
	 */
	public boolean isAoE()
	{
		return AoE;
	}
	
	/**
	 * @return The duration before the target will get hit again by the same attack
	 */
	public int getHitDelay()
	{
		return hitDelay;
	}
	
	/**
	 * @return If true, only students will take damage
	 */
	public boolean isStudent()
	{
		return isStudent;
	}
	
	/**
	 * Moves the attack and increases time by 1.
	 */
	public void move(int dist)
	{
		super.move(dist);
		time++;
	}
	
	/**
	 * @return If the attack has completed
	 */
	public boolean over()
	{
		if(time>duration)
			return true;
		return false;
	}
	
	/**
	 * Sets the direction of the attack and center it on the character
	 */
	public void switchXY()
	{
		updateSprite();
		
		x = (x)-(int) this.getBounds().getWidth()/4;
		y = (y)-(int) this.getBounds().getHeight()/4;
		
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

/**
 * Attack type placement variables
 */
class Type
{
	public static final int FRONT=0;
	public static final int BACK=1;
	public static final int CENTER=2;	
}