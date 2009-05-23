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
		if(inRange(g.getPlayer(),120) && percent<=100)
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
					speed=0,
					duration=0,
					reuse=duration,
					stillTime=0,
					hits=1,
					hitDelay=duration,
					status=0,
					statusLength=0;
		AttackType type=null;
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
			case 0:
				name="choco";
				damage=3;
				tp=0;
				type=AttackType.FRONT;
				speed=20;
				duration=300;
				reuse=30;
				stillTime=30;
				hits=hits;
				hitDelay=0;
				status=status;
				statusLength=statusLength;
				isStudent=isStudent;
				AoE=AoE;
				break;
			case 1:
				name="tape";
				damage=2;
				tp=0;
				type=AttackType.FAR_FRONT;
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
				name="rage";
				damage=1;
				tp=0;
				type=AttackType.CENTER;
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
		
		if(Game.cheatMode)
		{
			tp=0;
			reuse=0;
			stillTime=2;
		}
		
		attack=new Attack(game,this.getBounds().getCenterX(), this.getBounds().getCenterY(), speed, this.getDirection(), name, isStudent, AoE, damage, tp, duration, type, status, statusLength, stillTime, hits, hitDelay, reuse);
		return attack;
	}
}