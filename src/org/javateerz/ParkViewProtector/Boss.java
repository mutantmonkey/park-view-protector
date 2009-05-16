package org.javateerz.ParkViewProtector;

import java.util.ArrayList;

import org.javateerz.EasyGL.GLRect;
import org.newdawn.slick.Color;

public abstract class Boss extends Character
{
	private static final long serialVersionUID = 1L;
	
	public Boss(Game g, int x, int y, int hp, int maxHp, double speed)
	{
		super(g,x,y,hp,maxHp,speed);
		updateSprite();
	}
	

	/**
	 * Tells the boss to make a decision of what to do.
	 * 
	 * @param boss - the boss to give the move to
	 */
	public void step(Game g)
	{
		moveToward(g.getPlayer(),(int)(speed));
		recover();
	}
	
	protected void updateSprite()
	{
		sprite = DataStore.INSTANCE.getSprite("big_boss.png");
	}
	
	/**
	 * Handles players attacks
	 */
	public void attack(int key)
	{
		ArrayList<Attack> attacks=game.getAttacks();
		Attack attack;
		attack			= getAttack(key);
		setAttackFrames(attack.getStillTime());
		attack.switchXY();
		attacks.add(attack);
			
		try
		{
			ParkViewProtector.playSound(attack.getName()+".wav");
		}
		catch(Exception e)
		{
			System.out.println("The attack has no sound.");
		}
		
		// set delay
		setAgainFrames(attack.getReuse());
	}
	
	/**
	 * Handle any attacks on a student
	 * 
	 * @param obj
	 * @return Whether or not the attack hit the student
	 */
	public boolean handleAttacks()
	{
		Attack attack;
		ArrayList<Attack> attacks=game.getAttacks();
		
		// hit by an attack?
		for(int j = 0; j < attacks.size(); j++)
		{
			attack		= attacks.get(j);
			
			//The student takes damage in this if loop
			if(attack.getBounds().intersects(getBounds()) && isVulnerable() &&
					attack.isStudent())
			{
				game.hitFX((int)(getBounds().getCenterX()),
						(int)(getBounds().getCenterY()));
				
				if(attack.getStatus()==Status.STUN && !isStunned())
				{
					setStunFrames((int)(attack.getStatusDuration()/3));
				}
				
				if(!attack.isAoE())
				{
					attacks.remove(j);
				}
				
				// FIXME: should be variable depending on strength
				if(getHp()>0)
					adjustHp(-attack.getDamage()/2);
				
				//System.out.println("Student took "+ attack.getDamage()/2 + ", now has "+ getCharge());
				setInvulFrames(attack.getHitDelay());
				
				return true;
			}
		}
		
		return false;
	}

	public void showCharge()
	{
		GLRect rect				= new GLRect((int) x, (int) y, (int) getBounds().getWidth(),
				(int) getBounds().getHeight());
		rect.setColor(new Color(ParkViewProtector.COLOR_BG_1.getRed(),
				ParkViewProtector.COLOR_BG_1.getGreen(),
				ParkViewProtector.COLOR_BG_1.getBlue(), 5));
		rect.draw();

		Bar chargeBar = new Bar(ParkViewProtector.STATS_BAR_HP,(int)(getBounds().getWidth()), (double)getHp()/getMaxHp());
		chargeBar.draw((int)x,(int)y);
	}
	
	public abstract Attack getAttack(int i);
}