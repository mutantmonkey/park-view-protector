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
import org.javateerz.ParkViewProtector.Type;

public class SpecialCharacter extends Staff
{
	public static final String CHAR_NAME		= "Michael Schwinabart";
	
	private static final double SPEED= 5.0;
	private static final int MAX_HP = 50;
	private static final int MAX_TP = 300;
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
					type=0,
					speed=0,
					duration=0,
					reuse=duration,
					stillTime=0,
					hits=1,
					hitDelay=duration,
					status=0,
					statusLength=0;
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
				type=Type.FRONT;
				speed=0;
				duration=70;
				reuse=40;
				stillTime=20;
				hits=1;
				hitDelay=duration/hits;
				status=Status.STUN;
				statusLength=50;
				isEnemy=isEnemy;
				AoE=true;
				break;
			case 1:
				name="cake";
				damage=-60;
				type=Type.CENTER;
				tp=250;
				speed=0;
				duration=40;
				reuse=10;
				stillTime=stillTime;
				hits=1;
				hitDelay=duration/hits;
				status=Status.INVULNERABLE;
				statusLength=200;
				isEnemy=false;
				AoE=true;
				break;
			case 2:
				name="screech";
				damage=1;
				tp=25;
				type=Type.CENTER;
				speed=0;
				duration=1;
				reuse=50;
				stillTime=40;
				hits=1;
				hitDelay=duration/hits;
				status=Status.STUN;
				statusLength=300;
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