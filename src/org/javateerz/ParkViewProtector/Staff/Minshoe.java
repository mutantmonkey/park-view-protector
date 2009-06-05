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

public class Minshoe extends Staff
{
	public static final String CHAR_NAME		= "Dr. 'Minshoe'";
	
	private static final double SPEED= 1.7;
	private static final int MAX_TP = 140;
	private static final int MAX_HP = 111;
	private static final long serialVersionUID = 3L;
	
	public Minshoe(Game g, int x, int y)
	{
		super(CHAR_NAME, g, x, y, MAX_HP, MAX_HP, SPEED, MAX_TP, MAX_TP);
		updateSprite();
	}
	
	public Minshoe(Game g, int x, int y, int hp, int tp)
	{
		super(CHAR_NAME, g, x, y, hp, MAX_HP, SPEED, tp, MAX_TP);
		updateSprite();
	}

	protected void updateSprite()
	{
		sprite = DataStore.INSTANCE.getSprite("staff/minshoe.png");
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
		boolean 	isStudent=true,
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
				isStudent=isStudent;
				AoE=AoE;
		 */
		switch(i)
		{
			case 0:
				name="tardy";
				damage=10;
				tp=20;
				type=AttackType.FAR_FRONT;
				speed=0;
				duration=.5;
				reuse=duration;
				stillTime=.5;
				hits=1;
				hitDelay=duration;
				status=status;
				statusLength=statusLength;
				isStudent=true;
				AoE=AoE;
				break;
			case 1:
				name="detention";
				damage=5;
				tp=70;
				type=AttackType.CENTER;
				speed=0;
				duration=4;
				reuse=4.5;
				stillTime=3;
				hits=10;
				hitDelay=duration/hits;
				status=status;
				statusLength=statusLength;
				isStudent=isStudent;
				AoE=AoE;
				break;
			case 2:
				name="announcement";
				damage=0;
				tp=50;
				type=AttackType.CENTER;
				speed=0;
				duration=10;
				reuse=15;
				stillTime=3;
				hits=10;
				hitDelay=duration/hits;
				status=Status.STUN;
				statusLength=10;
				isStudent=isStudent;
				AoE=AoE;
				break;
		}
		attack=new Attack(game, x, y, speed, this.getDirection(), name, isStudent, AoE, damage, tp, duration, type, status, statusLength, stillTime, hits, hitDelay, reuse);
		return attack;
	}
}