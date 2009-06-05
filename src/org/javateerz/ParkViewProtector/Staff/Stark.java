/**
 * Park View Protractor
 * 
 * @author Jason of Javateerz
 *
 * The jawesomeest charater in the hole game
 *
 */

package org.javateerz.ParkViewProtector.Staff;

import org.javateerz.ParkViewProtector.Attack;
import org.javateerz.ParkViewProtector.DataStore;
import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.Status;
import org.javateerz.ParkViewProtector.Attack.AttackType;

public class Stark extends Staff
{
	public static final String CHAR_NAME		= "Mr. David Stark";
	
	private static final double SPEED= 2.0;
	private static final int MAX_HP=150;
	private static final int MAX_TP=100;
	private static final long serialVersionUID = 4L;
	
	public Stark(Game g, int x, int y)
	{
		super(CHAR_NAME, g, x, y, MAX_HP, MAX_HP, SPEED, MAX_TP, MAX_TP);
		updateSprite();
	}
	
	public Stark(Game g, int x, int y, int hp, int tp)
	{
		super(CHAR_NAME, g, x, y, hp, MAX_HP, SPEED, tp, MAX_TP);
		updateSprite();
	}
	
	protected void updateSprite()
	{
		sprite = DataStore.INSTANCE.getSprite("staff/stark.png");
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
		AttackType type = null;
		boolean 	isEnemy=true,
					AoE=false;
		/*
		 * FORMAT
		 * 		name			= name;
				damage			= damage;
				tp				= tp;
				type			= type;
				speed			= speed;
				duration		= duration;
				reuse			= reuse;
				stillTime		= stillTime;
				hits			= hits;
				hitDelay		= duration/hits;
				status			= status;
				statusLength	= statusLength;
				isEnemy			= isEnemy;
				AoE				= AoE;
		 */
		switch(i)
		{
			case 0:
				name="physball";
				damage=10;
				tp=12;
				type=AttackType.FRONT;
				speed=10;
				duration=1;
				reuse=duration;
				stillTime=stillTime;
				hits=hits;
				hitDelay=duration/hits;
				status=status;
				statusLength=statusLength;
				isEnemy=isEnemy;
				AoE=AoE;
				break;
			case 1:
				name="meterstick";
				damage=15;
				tp=17;
				type=AttackType.FRONT;
				speed=0;
				duration=0.3;
				reuse=duration + 0.08;
				stillTime=duration + .02;
				hits=hits;
				hitDelay=duration/hits;
				status=status;
				statusLength=statusLength;
				isEnemy=isEnemy;
				AoE=true;
				break;
			case 2:
				name="goodnight";
				damage=3;
				tp=20;
				type=AttackType.CENTER;
				speed=0;
				duration=1.5;
				reuse=duration + 0.3;
				stillTime=duration + 0.1;
				hits=3;
				hitDelay=duration/hits;
				status=Status.STUN;
				statusLength=5;
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
		
		attack=new Attack(game,this.getBounds().getCenterX(), this.getBounds().getCenterY(), speed, this.getDirection(), name, isEnemy, AoE, damage, tp, duration, type, status, statusLength, stillTime, hits, hitDelay, reuse);
		return attack;
	}
}