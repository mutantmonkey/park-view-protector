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

public class SpecialCharacter extends Staff
{
	public static final String CHAR_NAME		= "Michael Schwinabart";
	
	private static final double SPEED= 5.0;
	private static final int MAX_HP = 50;
	private static final int MAX_TP = 270;
	private static final long serialVersionUID = 4L;
	
	public SpecialCharacter(Game g, int x, int y)
	{
		super(CHAR_NAME, g, x, y, MAX_HP, MAX_HP, SPEED, MAX_TP, MAX_TP);
		updateSprite();
	}
	
	public SpecialCharacter(Game g, int x, int y, int hp, int tp)
	{
		super(CHAR_NAME, g, x, y, hp, MAX_HP, SPEED, tp, MAX_TP);
		updateSprite();
	}

	protected void updateSprite()
	{
		sprite = DataStore.INSTANCE.getSprite("staff/michael.png");
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
				name="honk";
				damage=7;
				tp=10;
				type=AttackType.FRONT;
				speed=0;
				duration=0.6;
				reuse=.8;
				stillTime=.7;
				hits=1;
				hitDelay=duration/hits;
				status=Status.STUN;
				statusLength=.8;
				isEnemy=isEnemy;
				AoE=true;
				break;
			case 1:
				name="cake";
				damage=-60;
				type=AttackType.CENTER;
				tp=250;
				speed=0;
				duration=2;
				reuse=0;
				stillTime=0;
				hits=1;
				hitDelay=duration/hits;
				status=Status.INVULNERABLE;
				statusLength=7;
				isEnemy=false;
				AoE=true;
				break;
			case 2:
				name="screech";
				damage=1;
				tp=25;
				type=AttackType.CENTER;
				speed=0;
				duration=0.5;
				reuse=1;
				stillTime=.8;
				hits=1;
				hitDelay=duration/hits;
				status=Status.STUN;
				statusLength=2;
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
		
		attack=new Attack(game, this.getBounds().getCenterX(),
				this.getBounds().getCenterY(), speed, this.getDirection(),
				name, isEnemy, AoE, damage, tp, duration,
				type, status, statusLength, stillTime,
				hits, hitDelay, reuse);
		return attack;
	}
}