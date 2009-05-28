/**
 * Park View Protector
 * 
 * @author Jason of Javateerz
 *
 * The player class.
 *
 */

package org.javateerz.ParkViewProtector.Staff;

import java.io.*;
import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Attack;
import org.javateerz.ParkViewProtector.Character;
import org.javateerz.ParkViewProtector.DataStore;
import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.ParkViewProtector;
import org.javateerz.ParkViewProtector.Status;
import org.javateerz.ParkViewProtector.StatusEffect;

public abstract class Staff extends Character
{
	private static final long serialVersionUID = 3L;

	private static final int TP_REGEN = 1;
	
	private String name;
	private boolean invulHit=false;
	private boolean stunHit=false;
	
	private int tp;
	private int maxTp;
	private int tpRegenRate=0;
	private static final int TP_REGEN_RATE=5;
	
	public abstract Attack getAttack(int i);
	
	/*public Staff()
	{
		super(0, 0, 50, 50, 1.0, 1);
		tp = 12;
		maxTp = 12;
		
		sprite = DataStore.INSTANCE.getSprite("placeholder.png");
	}*/
	
	/**
	 * 
	 * @param name
	 * @param g Instance of Game
	 * @param x
	 * @param y
	 * @param hp
	 * @param maxHp
	 * @param speed
	 * @param tp
	 * @param maxTp
	 */
	public Staff(String name, Game g, int x, int y, int hp, int maxHp, double speed,
			int tp, int maxTp)
	{
		super(g, x, y, hp, maxHp, speed);
		this.name = name;
		this.tp=tp;
		this.maxTp=maxTp;
		bar.setName("green1");
		bar.updateSprite();
	}
	
	public void step(Game game)
	{
		recover();
		tpRegen();
		handleAttack();
	}
	
	public void setInvulHit(boolean hit)
	{
		invulHit=hit;
	}
	
	public boolean getInvulHit()
	{
		return invulHit;
	}
	
	public void setStunHit(boolean hit)
	{
		stunHit=hit;
	}
	
	public boolean getStunHit()
	{
		return stunHit;
	}

	protected void updateSprite()
	{
		sprite = DataStore.INSTANCE.getSprite("placeholder.png");
	}
	
	//The character uses an item class uses an item
	public void useItem(int item)
	{
		/*if(inventory.get(item) instanceof KeyItem)
		{
			inventory.get(item).run();
		}*/
	}
	
	//Return the current amount of TP that character has
	public int getTp()
	{
		return tp;
	}
	
	//Return the max TP of the character
	public int getMaxTp()
	{
		return maxTp;
	}
	
	//Changes the current amount of TP
	public void adjustTp(int amount)
	{
		tp+=amount;
		if(tp > maxTp)
		{
			tp = maxTp;
		}
	}
	
	//Changes the max TP
	public void adjustMaxTp(int amount)
	{
		maxTp+=amount;
	}
	
	//Sets the current amount of TP
	public void setTp(int amount)
	{
		tp=amount;
	}
	
	//Sets the max TP
	public void setMaxTp(int amount)
	{
		maxTp=amount;
	}
	
	public String getName()
	{
		return name;
	}
	
	protected void validateState()
	{
		super.validateState();
		
		if(tp > maxTp)
		{
			throw new IllegalArgumentException("TP cannot exceed max TP");
		}
	}

	/**
	 * Regenerate the player's TP
	 */
	public void tpRegen()
	{
		tpRegenRate+=1;
		if(tpRegenRate>=TP_REGEN_RATE / ParkViewProtector.getRenderDeltas())
		{
			if(game.getPlayer().getTp()<game.getPlayer().getMaxTp())
				game.getPlayer().adjustTp(TP_REGEN);
			tpRegenRate=0;
		}
	}
	
	/**
	 * Handle attacks
	 * 
	 * @return Whether or not the attack hit the player
	 */
	public boolean handleAttack()
	{
		Attack attack;
		
		ArrayList<Attack> attacks=game.getAttacks();
		
		for(int j = 0; j < attacks.size(); j++)
		{
			attack		= attacks.get(j);
			
			if(attack.getBounds().intersects(getBounds()) && isVulnerable() && !attack.isEnemy())
			{
				game.hitFX((int)(getBounds().getCenterX()),
						(int)(getBounds().getCenterY()));
				adjustHp(-attack.getDamage());
				if(getHp()>getMaxHp())
					setHp(getMaxHp());
				
				if(attack.getStatus()==Status.INVULNERABLE)
				{
					setInvulHit(true);
					setInvulFrames(attack.getStatusDuration());
					if(statusIndex("invul")!=-1)
					{
						effects.get(statusIndex("invul")).setTime(attack.getStatusDuration());
					}
					else
					{
						effects.add(new StatusEffect(game, "invul", this, attack.getStatusDuration()));
					}
				}
				else
				{
					setInvulFrames(attack.getHitDelay());
				}
				
				if(attack.getStatus()==Status.STUN)
				{
					setStunHit(true);
					setStunFrames(attack.getStatusDuration());
					if(statusIndex("stun")!=-1)
					{
						effects.get(statusIndex("stun")).setTime(attack.getStatusDuration());
					}
					else
					{
						effects.add(new StatusEffect(game, "stun", this, attack.getStatusDuration()));
					}
				}

				if(!attack.isAoE())
				{
					attacks.remove(j);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Handles players attacks
	 */
	public void attack(int key)
	{
		ArrayList<Attack> attacks=game.getAttacks();
		Attack attack;
		attack			= getAttack(key);
		if(getTp()>=attack.getTp() && isAgain() && !isStunned())
		{
			setAttackFrames(attack.getStillTime());
			adjustTp(-attack.getTp());
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
	}
	
	private void readObject(ObjectInputStream os) throws ClassNotFoundException, IOException
	{
		os.defaultReadObject();
		
		validateState();
	}
	
	private void writeObject(ObjectOutputStream os) throws IOException
	{
		os.defaultWriteObject();
	}
}

class Stats
{
	public static final int STARK_HP=100;
	public static final int STARK_TP=100;
	public static final int SPECIAL_HP=50;
	public static final int SPECIAL_TP=300;
}
