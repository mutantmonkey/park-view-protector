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

public class Frau extends Staff
{
	public static final String CHAR_NAME		= "Frau Thorsen";
	
	private static final double SPEED= 2.0;
	private static final int MAX_HP = 80;
	private static final int MAX_TP = 100;
	private static final long serialVersionUID = 1L;
	
	public Frau(Game g, int x, int y)
	{
		super(CHAR_NAME, g, x, y, MAX_HP, MAX_HP, SPEED, MAX_TP, MAX_TP);
		updateSprite();
	}
	
	public Frau(Game g, int x, int y, int hp, int tp)
	{
		super(CHAR_NAME, g, x, y, hp, MAX_HP, SPEED, tp, MAX_TP);
		updateSprite();
	}

	protected void updateSprite()
	{
		sprite = DataStore.INSTANCE.getSprite("staff/frau.png");
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
				name="german_club";
				damage=15;
				tp=10;
				type=AttackType.FRONT;
				speed=0;
				duration=0.7;
				reuse=duration + 0.2;
				stillTime=duration;
				hits=hits;
				hitDelay=duration/hits;
				status=status;
				statusLength=statusLength;
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
				damage=1;
				tp=25;
				type=AttackType.CENTER;
				speed=0;
				duration=1;
				reuse=50;
				stillTime=1;
				hits=1;
				hitDelay=duration/hits;
				status=Status.STUN;
				statusLength=10;
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