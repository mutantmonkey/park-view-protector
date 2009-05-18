package org.javateerz.ParkViewProtector;

import java.util.ArrayList;

import org.javateerz.EasyGL.GLRect;
import org.newdawn.slick.Color;

public abstract class Boss extends Character
{
	private static final long serialVersionUID = 1L;
	
	private static final int STATUS_RESISTANCE=3;
	
	/**
	 * Create a new Boss
	 * 
	 * @param game
	 * @param x
	 * @param y
	 * @param hp
	 * @param maxHp
	 * @param speed
	 */
	public Boss(Game game, int x, int y, int hp, int maxHp, double speed)
	{
		super(game, x, y, hp, maxHp, speed);
		updateSprite();
	}
	
	/**
	 * Actions the boss takes
	 * 
	 * @param game
	 */
	// FIXME: Actually make this do something.
	public void step(Game game)
	{
		moveToward(game.getPlayer(), (int)(speed));
		recover();
	}
	
	/**
	 * The boss attacks with a specified attack
	 * 
	 * @param key
	 */
	public void attack(int key)
	{
		ArrayList<Attack> attacks = game.getAttacks();
		Attack attack;
		
		// gets the attack from sub class bosses
		attack = getAttack(key);
		
		// set attack placement
		attack.switchXY();
		attacks.add(attack);
		
		// searches for attack noise and plays it
		try
		{
			ParkViewProtector.playSound(attack.getName()+".wav");
		}
		catch(Exception e)
		{
			System.out.println("The attack has no sound.");
		}
		
		// set attack frames
		setAttackFrames(attack.getStillTime());
		
		// set delay before able to use another attack
		setAgainFrames(attack.getReuse());
	}
	
	/**
	 * Handles an attack on the boss
	 * 
	 * @return If the boss has been hit
	 */
	public boolean handleAttacks()
	{
		Attack attack;
		ArrayList<Attack> attacks = game.getAttacks();

		// search for attacks that are hitting the boss
		for(int j = 0; j < attacks.size(); j++)
		{
			attack = attacks.get(j);
			
			// if the boss is able to get hit, it will be hit
			if(attack.getBounds().intersects(getBounds()) && isVulnerable() &&
					attack.isEnemy())
			{
				// visual effect
				game.hitFX((int) (getBounds().getCenterX()),
						(int) (getBounds().getCenterY()));
				
				// sets stunned frames
				if(attack.getStatus() == Status.STUN && !isStunned())
				{
					setStunFrames((int) (attack.getStatusDuration()/STATUS_RESISTANCE));
				}
				
				// removes attack if not AoE
				if(!attack.isAoE())
				{
					attacks.remove(j);
				}
				
				// boss takes damage
				if(getHp()>0)
					adjustHp(-attack.getDamage());
				
				// sets invulnerable frames
				setInvulFrames(attack.getHitDelay());
				
				return true;
			}
		}
		return false;
	}
	
	// display the HP of the boss
	public void showHp()
	{
		GLRect rect	= new GLRect((int) x, (int) y, (int) getBounds().getWidth(),
				(int) getBounds().getHeight());
		rect.setColor(new Color(ParkViewProtector.COLOR_BG_1.getRed(),
				ParkViewProtector.COLOR_BG_1.getGreen(),
				ParkViewProtector.COLOR_BG_1.getBlue(), 5));
		rect.draw();

		Bar chargeBar = new Bar(ParkViewProtector.STATS_BAR_HP,
				(int) (getBounds().getWidth()), (double)getHp()/getMaxHp());
		chargeBar.draw((int) x,(int) y);
	}
	
	// retrieves the attack from sub class to be used
	public abstract Attack getAttack(int i);

	// FIXME: this belongs in the FatMan class
	protected void updateSprite()
	{
		sprite = DataStore.INSTANCE.getSprite("big_boss.png");
	}
}