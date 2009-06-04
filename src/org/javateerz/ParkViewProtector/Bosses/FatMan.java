package org.javateerz.ParkViewProtector.Bosses;
import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Attack;
import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.ParkViewProtector;
import org.javateerz.ParkViewProtector.Status;
import org.javateerz.ParkViewProtector.Attack.AttackType;

public class FatMan extends Boss
{
	public static final int MAX_HP	= 200;
	public static final int SPEED	= 1;
	
	private static final long serialVersionUID = 1L;
	
	public FatMan(Game game, int x, int y)
	{
		super("big",game, x, y, MAX_HP, MAX_HP, SPEED);
	}

	public FatMan(Game g,int x, int y, int hp, int maxHp, double speed)
	{
		super("big",g, x, y, maxHp, maxHp, speed);
	}
	
	public void step(Game g)
	{
		ArrayList <Attack> gameAttacks = g.getAttacks();
		int percent=(int)(Math.random()*1000);
		if(inRange(g.getPlayer(),200) && percent<=100)
		{
			if(!isStunned() && !isAttacking() && isAgain())
			{
				setDirection(getDirectionToward(g.getPlayer()));
				Attack attack;
				int attackKey=0;
				if(percent >= 30)
				{
					attackKey=0;
				}
				else if(percent >= 10)
				{
					attackKey=1;
				}
				else
				{
					attackKey=2;
				}
				
				attack = getAttack(attackKey);
				
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
		else if(!isStunned() && !isAttacking() && percent>100)
		{
			moveToward(game.getPlayer(), (int)(speed));
		}
		recover();
		
		handleAttacks();
	}
	
	public Attack getAttack(int i)
	{
		Attack attack;
		String		name="attack";
		int			damage=0,
					hits=1,
					status=0;
		double		speed=0,
					duration=0,
					reuse=duration,
					stillTime=0,
					hitDelay=duration,
					statusLength=0;
					
		AttackType type=null;
		boolean 	isStudent=false,
					AoE=false;
		/*
		 * FORMAT
		 * 		name=name;
				damage=damage;
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
				name="choco";
				damage=5;
				type=AttackType.FRONT;
				speed=15;
				duration=2;
				reuse=.8;
				stillTime=0;
				hits=hits;
				hitDelay=.3;
				status=status;
				statusLength=statusLength;
				isStudent=isStudent;
				AoE=false;
				break;
			case 1:
				name="tape";
				damage=10;
				type=AttackType.FAR_FRONT;
				speed=0;
				duration=.5;
				reuse=duration + 0.1;
				stillTime=duration + 0.02;
				hits=hits;
				hitDelay=duration/hits;
				status=status;
				statusLength=statusLength;
				isStudent=isStudent;
				AoE=true;
				break;
			case 2:
				name="rage";
				damage=1;
				type=AttackType.CENTER;
				speed=0;
				duration=1.5;
				reuse=duration + 1;
				stillTime=duration + 0.3;
				hits=10;
				hitDelay=duration/hits;
				status=Status.STUN;
				statusLength=2;
				isStudent=isStudent;
				AoE=true;
				break;
		}
		
		if(Game.cheatMode)
		{
			reuse=0;
			stillTime=2;
		}
		
		attack=new Attack(game,this.getBounds().getCenterX(), this.getBounds().getCenterY(), speed, this.getDirection(), name, isStudent, AoE, damage, duration, type, status, statusLength, stillTime, hits, hitDelay, reuse);
		return attack;
	}
}
