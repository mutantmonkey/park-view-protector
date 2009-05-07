/**
 * Park View Protractor
 * 
 * @author Jason of Javateerz
 *
 * The jawesomeest charater in the hole game
 *
 */

package org.javateerz.ParkViewProtector;

public class Stark extends Staff
{
	public static final String CHAR_NAME		= "Mr. David Stark";
	
	private static final double SPEED= 2.0;
	private static final int MAX_HP=100;
	private static final int MAX_TP=100;
	private static final long serialVersionUID = 4L;
	
	public Stark(int x, int y)
	{
		super(CHAR_NAME, x, y, MAX_HP, MAX_HP, SPEED, MAX_TP, MAX_TP);
		updateSprite();
	}
	
	public Stark(int x, int y, int hp, int tp)
	{
		super(CHAR_NAME, x, y, hp, MAX_HP, SPEED, tp, MAX_TP);
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
					type=0,
					speed=0,
					duration=0,
					reuse=duration,
					stillTime=0,
					hits=1,
					hitDelay=duration,
					status=0,
					statusLength=0;
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
				name="physball";
				damage=5;
				tp=10;
				type=Type.FRONT;
				speed=5;
				duration=40;
				reuse=duration;
				stillTime=stillTime;
				hits=hits;
				hitDelay=duration/hits;
				status=status;
				statusLength=statusLength;
				isStudent=isStudent;
				AoE=AoE;
				break;
			case 1:
				name="meterstick";
				damage=10;
				tp=10;
				type=Type.FRONT;
				speed=0;
				duration=20;
				reuse=duration;
				stillTime=duration;
				hits=hits;
				hitDelay=duration/hits;
				status=status;
				statusLength=statusLength;
				isStudent=isStudent;
				AoE=true;
				break;
			case 2:
				name="goodnight";
				damage=3;
				tp=30;
				type=Type.CENTER;
				speed=0;
				duration=50;
				reuse=duration;
				stillTime=duration;
				hits=hits;
				hitDelay=duration/hits;
				status=Status.STUN;
				statusLength=100;
				isStudent=isStudent;
				AoE=true;
				break;
		}
		attack=new Attack(this.getBounds().getX(), this.getBounds().getY(), speed, this.getDirection(), name, isStudent, AoE, damage, tp, duration, type, status, statusLength, stillTime, hits, hitDelay, reuse);
		return attack;
	}
}