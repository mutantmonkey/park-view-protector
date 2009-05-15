package org.javateerz.ParkViewProtector;

import java.util.ArrayList;

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
	public void step()
	{
		moveToward(game.getPlayer(),(int)(speed));
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
	
	public abstract Attack getAttack(int i);
}