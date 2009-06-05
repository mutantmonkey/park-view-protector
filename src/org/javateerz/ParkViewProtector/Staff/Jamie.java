/**
 * Park View Protractor
 * 
 * @author Jason of Javateerz
 *
 *
 */

package org.javateerz.ParkViewProtector.Staff;

import org.javateerz.ParkViewProtector.Attack;
import org.javateerz.ParkViewProtector.DataStore;
import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.Status;
import org.javateerz.ParkViewProtector.Attack.AttackType;

public class Jamie extends Staff
{
	public static final String CHAR_NAME		= "James Schwinabart";
	
	private static final double SPEED= 5.0;
	private static final int MAX_HP = 100;
	private static final int MAX_TP = 60;
	
	private int lawnTick		= 0;
	private final int LAWN_MAX	= 20;
	private static final long serialVersionUID = 1L;
	
	public Jamie(Game g, int x, int y)
	{
		super(CHAR_NAME, g, x, y, MAX_HP, MAX_HP, SPEED, MAX_TP, MAX_TP);
		updateSprite();
	}
	
	public Jamie(Game g, int x, int y, int hp, int tp)
	{
		super(CHAR_NAME, g, x, y, hp, MAX_HP, SPEED, tp, MAX_TP);
		updateSprite();
	}

	protected void updateSprite()
	{
		sprite = DataStore.INSTANCE.getSprite("staff/jamie.png");
	}
	
	public Attack getAttack(int i)
	{
		Attack attack;
		String		name="attack";
		int			damage=0,
					tp=0,
					speed=0,
					hits=1,
					status=0;
		double		duration=0,
					stillTime=0,
					reuse=duration,
					hitDelay=duration,
					statusLength=0;
		AttackType type=null;
		boolean 	isEnemy=true,
					AoE=false;
		/*
		 * FORMAT
		 * 		name=name;
				damage=damage;
				tp=tp;
				type=type;
				speed=speed;
				duration=duration;
				reuse=reuse;
				stillTime=stillTime;
				hits=hits;
				hitDelay=duration/hits;
				status=status;
				statusLength=statusLength;
				isEnemy=isEnemy;
				AoE=AoE;
		 */
		switch(i)
		{
			case 0:
				name="lawnmower";
				damage=3;
				if(getTp()>40)
				{
					if(lawnTick>=LAWN_MAX)
					{
						tp=5;
						lawnTick=0;
					}
					else
					{
						lawnTick++;
					}
				}
				else
				{
					tp=99999;
				}
				type=AttackType.FRONT;
				speed=0;
				duration=0;
				reuse=0;
				stillTime=0;
				hits=1;
				hitDelay=0.1;
				status=Status.NONE;
				statusLength=0;
				isEnemy=isEnemy;
				AoE=true;
				break;
			case 1:
				name="javateerz";
				damage=-60;
				type=AttackType.CENTER;
				tp=250;
				speed=0;
				duration=1;
				reuse=5;
				stillTime=stillTime;
				hits=1;
				hitDelay=duration/hits;
				status=Status.INVULNERABLE;
				statusLength=20;
				isEnemy=false;
				AoE=true;
				break;
			case 2:
				name="clarinet";
				damage=8;
				tp=10;
				type=AttackType.CENTER;
				speed=10;
				duration=.6;
				reuse=duration + 0.3;
				stillTime=duration;
				hits=1;
				hitDelay=duration/hits;
				status=Status.STUN;
				statusLength=.3;
				isEnemy=isEnemy;
				AoE=true;
				break;
		}
		
		if(Game.cheatMode)
		{
			tp=0;
			reuse=0;
			stillTime=2;
		}
		
		attack=new Attack(game, this.getBounds().getCenterX(), this.getBounds().getCenterY(), speed, this.getDirection(), name, isEnemy, AoE, damage, tp, duration, type, status, statusLength, stillTime, hits, hitDelay, reuse);
		return attack;
	}
}