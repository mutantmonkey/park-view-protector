package org.javateerz.ParkViewProtector;

import java.util.ArrayList;

public class Mandelbrot extends Boss
{
	public static final int MIN_BROTCHEN	= 3;
	public static final int MAX_BROTCHEN	= 5;
	public static final int BROT_HP			= 3;
	public static final double BROT_SPEED	= 1.0;
	
	public static final int MAX_HP			= 200;
	public static final int SPEED			= 1;
	
	private static final long serialVersionUID = 1L;
	
	public Mandelbrot(Game game, int x, int y)
	{
		super(game, x, y, MAX_HP, MAX_HP, SPEED);
	}

	public Mandelbrot(Game g, int x, int y, int hp, int maxHp, double speed)
	{
		super(g, x, y, hp, maxHp, speed);
	}
	
	protected void updateSprite()
	{
		sprite = DataStore.INSTANCE.getSprite("mandelbrot.png");
	}
	
	public void step(Game g)
	{
		ArrayList <Attack> gameAttacks = g.getAttacks();
		int percent=(int)(Math.random()*1000);
		if(inRange(g.getPlayer(),120) && percent<=100)
		{
			if(!isStunned() && !isAttacking() && isAgain())
			{
				setDirection(getDirectionToward(g.getPlayer()));
				Attack attack;

				if(percent >= 30)
				{
					// Mandelbrotchen
					
					int numBrotchen	= (int) (Math.random() * (MAX_BROTCHEN - MAX_BROTCHEN)) +
						MIN_BROTCHEN;
					int brotX, brotY;
					Student brot;
					
					for(int i = 0; i < numBrotchen; i++)
					{
						brotX		= (int) (Math.random() * ParkViewProtector.WIDTH);
						brotY		= (int) (Math.random() * ParkViewProtector.HEIGHT);
						
						brot		= new Student(game, brotX, brotY, BROT_HP,
								BROT_SPEED, 'm', Student.MANDELBROT);
						
						game.addStudent(brot);
					}
				}
				else {
					attack = getAttack(0);
					
					setAttackFrames(attack.getStillTime());
					
					attack.switchXY();
					gameAttacks.add(attack);
					
					try
					{
						ParkViewProtector.playSound(attack.getName()+".wav");
					}
					catch(Exception e)
					{
						System.out.println("The attack has no sound.");
					}
					
					setAgainFrames(attack.getReuse());
				}
			}
		}
		else if(!isStunned() && !isAttacking() && percent>100)
		{
			moveToward(game.getPlayer(),(int)(speed));
		}
		recover();
		
		handleAttacks();
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
		boolean 	isStudent=false,
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
			default:
				name="rage";
				damage=1;
				tp=0;
				type=Type.CENTER;
				speed=0;
				duration=100;
				reuse=200;
				stillTime=duration;
				hits=10;
				hitDelay=duration/hits;
				status=Status.STUN;
				statusLength=50;
				isStudent=isStudent;
				AoE=true;
				break;
		}
		attack=new Attack(game,this.getBounds().getCenterX(), this.getBounds().getCenterY(), speed, this.getDirection(), name, isStudent, AoE, damage, tp, duration, type, status, statusLength, stillTime, hits, hitDelay, reuse);
		return attack;
	}
}
