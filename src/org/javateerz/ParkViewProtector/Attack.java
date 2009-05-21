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
	public enum AttackType { FRONT, MID_FRONT, FAR_FRONT, BACK, CENTER, CLOSE, MID, FAR };
	
	public int	PLACEMENT_CLOSE		= 20,
				PLACEMENT_MID_CLOSE	= 30,
				PLACEMENT_MID		= 50,
				PLACEMENT_FAR		= 100;
	
	private String 		name;
	private AttackType	type;
	private int 		damage,
						tp, 
						duration, 
						time = 0, 
						status, 
						statusDuration, 
						stillTime, 
						hits, 
						hitDelay, 
						reuse;
	private boolean 	isEnemy, 
						AoE;
	
	private static final long serialVersionUID = 4L;
	
	/**
	 * Create a new Attack
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param speed The speed the attack travels
	 * @param direction Direction of the attack
	 * @param name Name of the attack
	 * @param isEnemy If true, the attack effects students
	 * @param AoE If true, the attack will not disappear upon hitting a target
	 * @param damage The damage the attack will deal
	 * @param tp The amount of TP the attack consumes
	 * @param duration The duration the attack stays on screen
	 * @param type The placement of the attack
	 * @param statusEffect The status effect the attack induces
	 * @param statusDuration The length of the status effect
	 * @param stillTime The duration the attack makes the user stand still
	 * @param hits The number of hits the attack deals
	 * @param hitDelay The time before the target can be hit again after being hit
	 * @param reuse The time the user can perform another attack
	 */
	public Attack(	Game game,
					double x,
					double y,
					double speed,
					int direction,
					String name,
					boolean isEnemy,
					boolean AoE,
					int damage,
					int tp,
					int duration,
					AttackType type,
					int statusEffect,
					int statusDuration,
					int stillTime,
					int hits,
					int hitDelay,
					int reuse)
	{
		super(game, x, y, speed);
		this.type			= type;
		this.name			= name;
		this.damage			= damage;
		this.tp				= tp;
		this.duration		= duration;
		this.direction		= direction;
		this.stillTime		= stillTime;
		this.status			= statusEffect;
		this.statusDuration	= statusDuration;
		this.AoE			= AoE;
		this.hits			= hits;
		this.hitDelay		= hitDelay;
		this.reuse			= reuse;
		this.isEnemy		= isEnemy;
		switchXY();
	}
	
	/**
	 * Create a new Attack that does not require TP
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param speed The speed the attack travels
	 * @param direction Direction of the attack
	 * @param name Name of the attack
	 * @param isEnemy If true, the attack effects students
	 * @param AoE If true, the attack will not disappear upon hitting a target
	 * @param damage The damage the attack will deal
	 * @param duration The duration the attack stays on screen
	 * @param type The placement of the attack
	 * @param statusEffect The status effect the attack induces
	 * @param statusDuration The length of the status effect
	 * @param stillTime The duration the attack makes the user stand still
	 * @param hits The number of hits the attack deals
	 * @param hitDelay The time before the target can be hit again after being hit
	 * @param reuse The time the user can perform another attack
	 */
	public Attack(	Game game,
			double x,
			double y,
			double speed,
			int direction,
			String name,
			boolean isEnemy,
			boolean AoE,
			int damage,
			int duration,
			AttackType type,
			int statusEffect,
			int statusDuration,
			int stillTime,
			int hits,
			int hitDelay,
			int reuse)
		{
			super(game, x, y, speed);
			this.type			= type;
			this.name			= name;
			this.damage			= damage;
			this.duration		= duration;
			this.direction		= direction;
			this.stillTime		= stillTime;
			this.status			= statusEffect;
			this.statusDuration	= statusDuration;
			this.AoE			= AoE;
			this.hits			= hits;
			this.hitDelay		= hitDelay;
			this.reuse			= reuse;
			this.isEnemy		= isEnemy;
			switchXY();
		}
	
	/**
	 * @return The name of the attack
	 */
	public String getName()
	{	return name;}
	
	/**
	 * @return The damage the attack deals
	 */
	public int getDamage()
	{	return damage;}
	
	/**
	 * @return The duration the attack stays on screen
	 */
	public int getDuration()
	{	return duration;}

	/**
	 * @return The duration of the status effect
	 */
	public int getStatusDuration()
	{	return statusDuration;}

	/**
	 * @return The number of hits the attack deals
	 */
	public int getHits()
	{	return hits;}

	/**
	 * @return The duration that the user cannot perform an action
	 */
	public int getStillTime()
	{	return stillTime;}

	/**
	 * @return The TP attack consumes
	 */
	public int getTp()
	{	return tp;}
	
	/**
	 * @return The status effect the attack induces
	 */
	public int getStatus()
	{	return status;}
	
	/**
	 * @return The duration before the user can perform another attack
	 */
	public int getReuse()
	{	return reuse;}
	
	/**
	 * @return If true, the attack will not disappear upon hitting a target
	 */
	public boolean isAoE()
	{	return AoE;}
	
	/**
	 * @return The duration before the target can be hit again
	 */
	public int getHitDelay()
	{	return hitDelay;}
	
	/**
	 * @return If true, only students will take damage
	 */
	public boolean isEnemy()
	{	return isEnemy;}
	
	/**
	 * Moves the attack and increases time by 1.
	 * 
	 * @param dist Distance to move
	 */
	public void move(int dist)
	{
		super.move(dist);
		time++;
	}

	/**
	 * Sets the direction and centers the attack
	 */
	public void switchXY()
	{
		// set the graphic
		updateSprite();
		
		// centers the attack
		x = (x) - (int) this.getBounds().getWidth()/4;
		y = (y) - (int) this.getBounds().getHeight()/4;
		
		// the attack will be slightly in front of the character
		if(type == AttackType.FRONT)
		{
			if(direction == Direction.EAST)
				x += (int) Math.round(PLACEMENT_CLOSE);
			else if(direction == Direction.WEST)
				x -= (int) Math.round(PLACEMENT_CLOSE);
			else if(direction==Direction.SOUTH)
				y += (int) Math.round(PLACEMENT_CLOSE);
			else
				y -= (int) Math.round(PLACEMENT_CLOSE);
		}
		
		// the attack will be in front of the character
		else if(type == AttackType.MID_FRONT)
		{
			if(direction == Direction.EAST)
				x += (int) Math.round(PLACEMENT_MID);
			else if(direction == Direction.WEST)
				x -= (int) Math.round(PLACEMENT_MID);
			else if(direction == Direction.SOUTH)
				y += (int) Math.round(PLACEMENT_MID);
			else
				y -= (int) Math.round(PLACEMENT_MID);
		}
		
		// the attack will be far in front of the character
		else if(type == AttackType.FAR_FRONT)
		{
			if(direction == Direction.EAST)
				x += (int) Math.round(PLACEMENT_FAR);
			else if(direction == Direction.WEST)
				x -= (int) Math.round(PLACEMENT_FAR);
			else if(direction == Direction.SOUTH)
				y += (int) Math.round(PLACEMENT_FAR);
			else
				y -= (int) Math.round(PLACEMENT_FAR);
		}
		
		// the attack will appear behind the character
		else if(type == AttackType.BACK)
		{
			if(direction == Direction.EAST)
				x -= (int) Math.round(PLACEMENT_MID_CLOSE);
			else if(direction == Direction.WEST)
				x += (int) Math.round(PLACEMENT_MID_CLOSE);
			else if(direction == Direction.SOUTH)
				y -= (int) Math.round(PLACEMENT_MID_CLOSE);
			else
				y += (int) Math.round(PLACEMENT_MID_CLOSE);
		}
		
		// the attack appears on the center of the character
		else if(type == AttackType.CENTER)
		{
			//Impletment never
		}
	}

	/**
	 * @return If the attack has completed
	 */
	public boolean over()
	{
		if(time > duration)
			return true;
		return false;
	}
	
	/**
	 * Sets the attack's graphic
	 */
	protected void updateSprite()
	{
		// search for the attack graphic in the direction it is facing
		try
		{
			if(direction == Direction.NORTH)
			{
				this.sprite = DataStore.INSTANCE.getSprite("attack/" + name + "_n.png");
			}
			else if(direction == Direction.SOUTH)
			{
				this.sprite = DataStore.INSTANCE.getSprite("attack/" + name + "_s.png");
			}
			else if(direction == Direction.WEST)
			{
				this.sprite = DataStore.INSTANCE.getSprite("attack/" + name + "_w.png");
			}
			else
			{
				this.sprite = DataStore.INSTANCE.getSprite("attack/" + name + "_e.png");
			}
		}
		catch(Exception e)
		{
			// search for the attack that does not have direction specific graphics
			try
			{
				this.sprite = DataStore.INSTANCE.getSprite("attack/" + name + ".png");
			}
			// set place holder graphic if there is no available graphic
			catch(Exception e1)
			{
				if(direction == Direction.NORTH)
				{
					this.sprite = DataStore.INSTANCE.getSprite("attack/attack_n.png");
				}
				else if(direction == Direction.SOUTH)
				{
					this.sprite = DataStore.INSTANCE.getSprite("attack/attack_s.png");
				}
				else if(direction == Direction.WEST)
				{
					this.sprite = DataStore.INSTANCE.getSprite("attack/attack_w.png");
				}
				else
				{
					this.sprite = DataStore.INSTANCE.getSprite("attack/attack_e.png");
				}
			}
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
