package org.javateerz.ParkViewProtector.Bosses;

import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Attack;
import org.javateerz.ParkViewProtector.Character;
import org.javateerz.ParkViewProtector.DataStore;
import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.ParkViewProtector;
import org.javateerz.ParkViewProtector.Status;
import org.javateerz.ParkViewProtector.StatusEffect;

public abstract class Boss extends Character
{
	private static final long serialVersionUID = 1L;
	
	private static final int STATUS_RESISTANCE=3;
	
	protected String name;
	
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
	public Boss(String name,Game game, int x, int y, int hp, int maxHp, double speed)
	{
		super(game, x, y, hp, maxHp, speed);
		this.name = name;
		updateSprite();
		bar.setName("blue1");
		bar.updateSprite();
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
				if(attack.getStatus()==Status.INVULNERABLE)
				{
					setInvulFrames(attack.getStatusDuration());
					if(statusIndex("invulbig")!=-1)
					{
						effects.get(statusIndex("invulbig")).setTime(attack.getStatusDuration());
					}
					else
					{
						effects.add(new StatusEffect(game, "invulbig", this, attack.getStatusDuration()));
					}
				}
				else
				{
					setInvulFrames(attack.getHitDelay());
				}
				
				if(attack.getStatus()==Status.STUN)
				{
					double dura	= attack.getStatusDuration()/STATUS_RESISTANCE;
					setStunFrames(dura);
					if(statusIndex("stunbig")!=-1)
					{
						effects.get(statusIndex("stunbig")).setTime(dura);
					}
					else
					{
						effects.add(new StatusEffect(game, "stunbig", this, dura));
					}
				}
				
				// removes attack if not AoE
				if(!attack.isAoE())
				{
					attacks.remove(j);
				}
				
				// boss takes damage
				if(getHp()>0)
					adjustHp(-attack.getDamage());
				
				if(getHp() <= 0)
				{
					updateSprite();
				}
				
				// sets invulnerable frames
				setInvulFrames(attack.getHitDelay());
				
				return true;
			}
		}
		return false;
	}
	
	// retrieves the attack from sub class to be used
	public abstract Attack getAttack(int i);
	
	protected void updateSprite()
	{
		if(getHp() <= 0)
		{
			try
			{
				sprite = DataStore.INSTANCE.getSprite("boss/" + name + "_dead.png");
			}
			catch(Exception e)
			{
				sprite = DataStore.INSTANCE.getSprite("boss/" + name + ".png");
			}
		}
		else {
			sprite = DataStore.INSTANCE.getSprite("boss/" + name + ".png");
		}
	}
}