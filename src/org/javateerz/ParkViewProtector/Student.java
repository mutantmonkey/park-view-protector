/**
 * This class stores information about a Student
 *
 * @author	Jamie of the Javateerz
 * @serial
 */

package org.javateerz.ParkViewProtector;

import java.io.*;
import java.util.ArrayList;

import org.javateerz.EasyGL.GLRect;
import org.newdawn.slick.Color;

public class Student extends Character implements Serializable
{
	public final static int NUM_STUDENTS		= 6;
	
	public final static int GANG				= 0;
	public final static double GANG_AGGRO		= 0.80;
	public final static int GOTH				= 1;
	public final static double GOTH_AGGRO		= 0.30;
	public final static int BAND				= 2;
	public final static double BAND_AGGRO		= 0.10;
	public final static int SCI					= 3;
	public final static double SCI_AGGRO		= 0.40;
	public final static int ROCK				= 4;
	public final static double ROCK_AGGRO		= 0.60;
	public final static int SPOR				= 5;
	public final static double SPOR_AGGRO		= 0.90;
	public final static int MANDELBROT			= 9999;
	public final static double MANDELBROT_AGGRO	= 1.0;
	
	private String type							= "default";
	private boolean aggro						= false;
	private char gender;
	private static final int ATTACK_RANGE		= 50;
	private static final int SIGHT_RANGE		= 200;
	
	private static final long serialVersionUID	= 2L;
	
	/**
	 * Create a new student
	 * 
	 * @param game
	 * @param hp
	 * @param maxHp
	 * @param speed
	 * @param gender
	 */
	public Student(Game game, int x, int y, int maxHp, double speed, char gender,
			int type)
	{
		super(game, x, y, maxHp, maxHp, speed);
		
		// sets the gender
		this.gender = gender;
		
		// generate random HP
		setHp((int) (Math.random() * getMaxHp() + 1));
		
		// sets the type of student
		switch(type)
		{
			case Student.GANG:
				this.type = "gangster";
				if(Math.random() < GANG_AGGRO)
					aggro = true;
				break;
				
			case Student.GOTH:
				this.type = "goth";
				if(Math.random() < GOTH_AGGRO)
					aggro = true;
				break;
				
			case Student.BAND:
				this.type = "band";
				if(Math.random() < BAND_AGGRO)
					aggro = true;
				break;

			case Student.SCI:
				this.type = "science";
				if(Math.random() < SCI_AGGRO)
					aggro = true;
				break;
				
			case Student.ROCK:
				this.type = "rocker";
				if(Math.random() < ROCK_AGGRO)
					aggro = true;
				break;

			case Student.SPOR:
				this.type = "sport";
				if(Math.random() < SPOR_AGGRO)
					aggro = true;
				break;
				
			case Student.MANDELBROT:
				this.type = "mandelbrot";
				if(Math.random() < MANDELBROT_AGGRO)
					aggro = true;
				break;
				
			default:
				this.type = "default";
			break;
		}
		
		// give the student some items
		int random = (int)(Math.random()*4);
		// there is a 1/2 chance of a health item, 1/4 chance of a teacher item, and 1/4 chance of no item
		switch(random)
		{
			case 0:
			case 1:
				pickItem(new Item(game, 'h', 0, 0));
				break;
				
			case 2:
				pickItem(new Item(game, 't', 0, 0));
				break;
				
			default:
				break;
		}
		
		// sets graphic
		updateSprite();
	}
	
	public void step(Game game)
	{
		// attempt to couple with other students
		if(getHp() > 0)
		{
			attemptCoupling();
		}
		
		// move toward and attack player if aggro and in range, otherwise, move random
		if(!isStunned() && !isAttacking())
		{
			// if student wants to attack the player
			if(aggro)
			{
				// if in attack range, attack
				if(inRange(game.getPlayer(), ATTACK_RANGE))
				{
					setDirection(getDirectionToward(game.getPlayer()));
					attack();
				}
				
				// if in sight range, move toward
				else if(inRange(game.getPlayer(), SIGHT_RANGE))
				{
					moveToward(game.getPlayer(), ATTACK_RANGE);
				}
				
				else
				{
					moveRandom();
				}
			}
			else
			{
				moveRandom();
			}
		}
		
		// recover from status effects
		recover();
		
		// recover hp
		if(!isStunned())
			regain();
		
		// receive attacks
		handleAttacks();
	}

	/**
	 * Updates the sprite
	 */
	protected void updateSprite()
	{
		try
		{
			sprite = DataStore.INSTANCE.getSprite("student/" + type + "_" + gender + ".png");
		}
		catch(Exception e)
		{
			sprite = DataStore.INSTANCE.getSprite("student/default_" + gender + ".png");
		}
	}
	
	/**
	 * @return The type of the student
	 */
	public String getType()
	{
		return type;
	}
	
	/**
	 * @return The gender of the student
	 */
	public char getGender()
	{
		return gender;
	}
	
	/**
	 * Sets the aggro of the student
	 * 
	 * @param agg
	 */
	public void setAggro(boolean agg)
	{
		aggro = agg;
	}
	
	/**
	 * Attempt to couple with another student
	 * 
	 * @return If the couple was successful
	 */
	public boolean attemptCoupling()
	{
		ArrayList<Student> students = game.getStudents();
		ArrayList<Couple> couples = game.getCouples();
		int charge, student1, student2;
		Student student;
		
		int i						= students.indexOf(this);
		
		// prevent coupling if we intersect a wall
		if(!canMove(getBounds()))
		{
			return false;
		}
		
		for(int j = 0; j < students.size(); j++)
		{
			student				= students.get(j);
			
			// don't do anything if it's us
			if(i == j)
			{
				continue;
			}
			
			// no same-sex couples (for now at least)
			if(getGender() == student.getGender())
			{
				continue;
			}

			// did we hit another student with a charge?
			if(getNewBounds(Game.MOVE_SPEED).intersects(students.get(j).getBounds())
					&& student.getHp() > 0)
			{
				charge				= getHp() +
										student.getHp();
				
				if(Math.random() * Game.COUPLE_CHANCE_MULTIPLIER < charge)
				{
					couples.add(new Couple(game, this, student));
					
					student1			= i;
					student2			= j;
					
					if(student2 > student1)
					{
						student2--;
					}
					
					try
					{
						students.remove(student1);
						students.remove(student2);
					}
					catch(Exception e)
					{
						game.driver.error("Something went wrong when deleting someone :O", false);
					}
					
					return true;
				}
			}
		}
		
		return false;
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
					attack.isEnemy())
			{
				game.hitFX((int)(getBounds().getCenterX()),
						(int)(getBounds().getCenterY()));
				
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
				
				if(getHp()>-10)
					adjustHp(-attack.getDamage()/2);
				
				if(getHp()<0)
				{
					setAggro(false);
					if(getHp() <= 0 && bin.items.size() > 0)
						{
							while(bin.items.size() > 0)
							{
								game.getItems().add(bin.items.get(0));
								bin.dropItem(bin.items.get(0));
							}
						}
				}
				//System.out.println("Student took "+ attack.getDamage()/2 + ", now has "+ getCharge());
				setInvulFrames(attack.getHitDelay());
				
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
		
		name=getType() + "_" + gender;
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