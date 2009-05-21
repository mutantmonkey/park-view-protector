/**
 * This class stores information about a couple ("cupple")
 *
 * @author	Jamie of the Javateerz
 * @serial
 */

package org.javateerz.ParkViewProtector;

import java.io.*;
import java.util.ArrayList;

import org.javateerz.EasyGL.GLRect;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

public class Couple extends Character
{
	private static final long serialVersionUID = 3L;
	
	/**
	 * Number to multiply to the combined speed of both students by
	 */
	public static final double SPEED_MULTIPLIER	= 0.2;
	
	/**
	 * The chance that a couple will become a wall couple
	 */
	public static final double WALL_CHANCE		= 0.6;
	
	private boolean aggro	= false;
	
	/**
	 * Maximum distance from a wall in pixels that couples will
	 * "stick" to
	 */
	public static final int WALL_DISTANCE		= 10;
	
	private Student male;
	private Student female;
	
	private boolean likesWall		= false;
	private int wallId				= 0;
	
	public Couple(Game g, Student a, Student b)
	{
		super(g, a.x, a.y, a.hp + b.hp, a.maxHp + b.maxHp,
				(a.speed+b.speed) * SPEED_MULTIPLIER);
		
		if(a.getGender() == 'm')
		{
			male = a;
			female = b;
		}
		else
		{
			male = b;
			female = a;
		}
		
		if(Math.random()>.5)
			aggro=true;
		
		// determine whether or not this should be a wall couple
		likesWall					= (Math.random() < WALL_CHANCE) ? true : false;
		
		// temp graphic
		updateSprite();
	}
	
	protected void updateSprite()
	{
		try
		{
			sprite	= DataStore.INSTANCE.getSprite("couples/" + female.getType() + "_"
					+ male.getType() + ".png");
		}
		catch(Exception e)
		{
			
		}
	}

	public void step(Game game)
	{
		if(likesWall && game.getWalls().size()>0 && !(aggro && inRange(game.getPlayer(), 50)))
		{
			// find nearest wall
			if(wallId == 0)
			{
				wallId		= findNearestWall();
			}
			
			// move toward the wall
			if(canMove(this.getBounds()))
			{
				Wall wall	= game.getWall(wallId);
				moveToward(wall);
			}
			else {
				
			}
		}
		else if(!isStunned() && !isAttacking() && aggro)
		{
			if(inRange(game.getPlayer(), 50))
			{
				setDirection(getDirectionToward(game.getPlayer()));
				attack();
			}
			else if(inRange(game.getPlayer(),200))
			{
				moveToward(game.getPlayer(),10);
			}
			else
			{
				moveRandom();
			}
		}
		else
			moveRandom();
		
		// decrement the hit delay
		recover();
		
		handleAttacks();
	}

	public Student getMale()
	{
		return male;
	}
	
	public Student getFemale()
	{
		return female;
	}
	
	public void moveToward(Wall wall)
	{
		int distX				= 0,
			distY				= 0;
		
		// increase the bounding box for testing
		Rectangle testBounds	= this.getBounds();
		testBounds.grow(WALL_DISTANCE, WALL_DISTANCE);
		
		// make sure we actually need to move
		if(testBounds.intersects(wall.getBounds()))
		{
			return;
		}
		
		// compute X distance
		if(wall.getBounds().getX() < x)
		{
			distX				   -= Game.MOVE_SPEED;
		}
		else {
			// wall lies to the right
			distX				   += Game.MOVE_SPEED;
		}
		
		// compute Y distance
		if(wall.getBounds().getY() <= y)
		{
			// wall lies above
			distY				   -= Game.MOVE_SPEED;
		}
		else {
			// wall lies below
			distY				   += Game.MOVE_SPEED;
		}
		
		move(distX, distY);
	}
	
	/**
	 * Handle any attacks on a couple
	 * 
	 * @param currCouple
	 * @return Whether or not the attack hit the couple
	 */
	public boolean handleAttacks()
	{
		Attack attack;
		Student male, female;
		
		ArrayList<Couple> couples=game.getCouples();
		ArrayList<Attack> attacks=game.getAttacks();
		ArrayList<Student> students=game.getStudents();
		
		int i				= couples.indexOf(this);
		
		// hit by an attack?
		for(int j = 0; j < attacks.size(); j++)
		{
			attack		= attacks.get(j);
			
			if(attack.getBounds().intersects(getBounds()) &&
					isVulnerable() && attack.isEnemy())
			{
				game.hitFX((int)(getBounds().getCenterX()),
						(int)(getBounds().getCenterY()));
				adjustHp(-attack.getDamage());
				setInvulFrames(attack.getHitDelay());
				
				if(attack.getStatus()==Status.INVULNERABLE)
				{
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
				
				// FIXME: If multiple attacks hit something or what?
				if(getHp() <=0)
				{
					int 	x=(int) getBounds().getX(),
							y=(int) getBounds().getY();
					
					male		= getMale();
					male.moveTo(x,y);
					while(!male.canMove(male.getBounds()))
					{
						x					= x + 1;
						y					= y + 1;

						male.moveTo(x,y);
					}
					male.setInvulFrames(attack.getHitDelay());
					male.setHp(-10);
					
					x=(int) getBounds().getX();
					y=(int) getBounds().getY();
					
					female		= getFemale();
					female.moveTo(x + Game.DECOUPLE_SPACING, y);
					while(!female.canMove(female.getBounds()))
					{
						x					= x + Game.DECOUPLE_SPACING + 1;
						y					= y + 1;

						female.moveTo(x,y);
					}
					female.setInvulFrames(attack.getHitDelay());
					female.setHp(-10);
					
					// create students before removing couple
					students.add(male);
					students.add(female);
					
					couples.remove(i);
				}
				
				return true;
			}
		}
		
		return false;
	}

	public void attack()
	{
		ArrayList<Attack> attacks=game.getAttacks();
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
		boolean 	isEnemy=false,
					AoE=false;
		
		name="honk";
		damage=1;
		tp=10;
		type=Type.FRONT;
		speed=0;
		duration=30;
		reuse=100;
		stillTime=30;
		hits=1;
		hitDelay=duration/hits;
		status=status;
		statusLength=statusLength;
		isEnemy=isEnemy;
		AoE=true;
		
		attack=new Attack(game,this.getBounds().getCenterX(), this.getBounds().getCenterY(), speed, this.getDirection(), name, isEnemy, AoE, damage, tp, duration, type, status, statusLength, stillTime, hits, hitDelay, reuse);
		if(inRange(game.getPlayer(),50) && isAgain())
		{
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