package org.javateerz.ParkViewProtector;
import java.util.ArrayList;

public class FatMan extends Boss
{
	String myMove;
	
	private static final long serialVersionUID = 1L;
	
	public FatMan(Game g,int x,int y)
	{
		super(g,x,y,100,100,1.0);
		updateSprite();
	}
	
	public FatMan(Game g,int x, int y, int hp, int maxHp, double speed)
	{
		super(g,x,y,hp,maxHp,speed);
		updateSprite();
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
			case 0:
				name="choco";
				damage=5;
				tp=0;
				type=Type.FRONT;
				speed=40;
				duration=300;
				reuse=10;
				stillTime=10;
				hits=hits;
				hitDelay=0;
				status=status;
				statusLength=statusLength;
				isStudent=isStudent;
				AoE=AoE;
				break;
			case 1:
				name="tape";
				damage=10;
				tp=0;
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
				duration=0;
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
		attack=new Attack(game,this.getBounds().getX(), this.getBounds().getY(), speed, this.getDirection(), name, isStudent, AoE, damage, tp, duration, type, status, statusLength, stillTime, hits, hitDelay, reuse);
		return attack;
	}
	
	public void step()
	{
		ArrayList <Attack> gameAttacks = game.getAttacks();
		int rand = (int)(Math.random()*1000);
		if(rand > 100)
		{
			if(!isStunned() && !isAttacking())
			{
				moveToward(game.getPlayer(),(int)(speed));
			}
		}
		else
		{
			if(!isAttacking())
			{
			Attack bossAttack;
			int attackKey=0;
			if(rand >= 30)
			{
				attackKey=0;
			}
			else if(rand >= 10)
			{
				attackKey=1;
			}
			else
			{
				attackKey=2;
			}
			
			bossAttack = getAttack(attackKey);
			
			setAttackFrames(bossAttack.getStillTime());
			
			bossAttack.switchXY();
			gameAttacks.add(bossAttack);
			
			
			try
			{
				ParkViewProtector.playSound(bossAttack.getName()+".wav");
			}
			catch(Exception e)
			{
				System.out.println("The attack has no sound.");
			}
			
			// set delay
			//attackDelay			= bossAttack.getReuse();
			setAgainFrames(bossAttack.getReuse());
			}
		}
		recover();
	}
}
