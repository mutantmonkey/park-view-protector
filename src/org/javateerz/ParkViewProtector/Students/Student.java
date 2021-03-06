/**
 * This class stores information about a Student
 *
 * @author	Jamie of the Javateerz
 * @serial
 */

package org.javateerz.ParkViewProtector.Students;

import java.io.*;
import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Attack;
import org.javateerz.ParkViewProtector.Character;
import org.javateerz.ParkViewProtector.Couple;
import org.javateerz.ParkViewProtector.DataStore;
import org.javateerz.ParkViewProtector.Direction;
import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.Item;
import org.javateerz.ParkViewProtector.ParkViewProtector;
import org.javateerz.ParkViewProtector.Sprite;
import org.javateerz.ParkViewProtector.Status;
import org.javateerz.ParkViewProtector.StatusEffect;
import org.javateerz.ParkViewProtector.Attack.AttackType;

public abstract class Student extends Character implements Serializable
{
	protected int 			MAtkSpd;
	protected boolean		MAtkAoE;
	protected int			MAtkDamage;
	protected String		MAtkName;
	protected double		MAtkDuration;
	protected AttackType	MAtkType;
	protected int			MAtkStatus;
	protected double		MAtkStatusDuration;
	protected double		MAtkStillTime;
	protected int			MAtkHits;
	protected double		MAtkHitsDelay;
	protected double		MAtkReuse;
	protected boolean		MAtkEnemy;
	protected boolean		MAtkHasDirection;
	protected int			MAtkRange;
	protected int			MSight;

	protected int 			FAtkSpd;
	protected boolean		FAtkAoE;
	protected int			FAtkDamage;
	protected String		FAtkName;
	protected double		FAtkDuration;
	protected AttackType	FAtkType;
	protected int			FAtkStatus;
	protected double		FAtkStatusDuration;
	protected double		FAtkStillTime;
	protected int			FAtkHits;
	protected double		FAtkHitsDelay;
	protected double		FAtkReuse;
	protected boolean		FAtkEnemy;
	protected boolean		FAtkHasDirection;
	protected int			FAtkRange;
	protected int			FSight;
	
	/* TEMPLATE!*/
	/*
	MAtkSpd				= 0;
	MAtkAoE				= true;
	MAtkDamage			= 1;
	MAtkName			= "attack";
	MAtkDuration		= 1;
	MAtkType			= AttackType.FRONT;
	MAtkStatus			= Status.NONE;
	MAtkStatusDuration	= 0;
	MAtkStillTime		= MAtkDuration+0.3;
	MAtkHits			= 1;
	MAtkHitsDelay		= MAtkDuration/MAtkHits;
	MAtkReuse			= MAtkDuration+0.6;
	MAtkEnemy			= true;
	MAtkHasDirection	= true;
	MAtkRange			= 40;
	MSight				= 200;

	FAtkSpd				= 0;
	FAtkAoE				= true;
	FAtkDamage			= 1;
	FAtkName			= "attack";
	FAtkDuration		= 1;
	FAtkType			= AttackType.FRONT;
	FAtkStatus			= Status.NONE;
	FAtkStatusDuration	= 0;
	FAtkStillTime		= FAtkDuration+0.3;
	FAtkHits			= 1;
	FAtkHitsDelay		= FAtkDuration/FAtkHits;
	FAtkReuse			= FAtkDuration+0.6;
	FAtkEnemy			= true;
	FAtkHasDirection	= true;
	FAtkRange			= 40;
	FSight				= 200;
		
	setAttack();
	*/
	
	public final static int NUM_STUDENTS		= 6;
	
	private String type							= "default";
	protected boolean aggro						= false;
	protected char gender;
	private int attackRange;
	private int sightRange;
	
	protected Sprite	attackN,
						attackS,
						attackE,
						attackW,
						attackX;
	
	private static final long serialVersionUID	= 3L;
	
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
			String type)
	{
		super(game, x, y, maxHp, maxHp, speed);
		
		this.gender	= gender;
		this.type	= type;
		
		// generate random HP
		setHp((int) (Math.random() * getMaxHp() + 1));
		
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
		bar.setName("red1");
		bar.updateSprite();
	}
	
	public void setAttack()
	{
		if(gender == 'm')
		{
			attackRange	= MAtkRange;
			sightRange	= MSight;
		}
		else
		{
			attackRange = FAtkRange;
			sightRange	= FSight;
		}
		
		try
		{
			attackN = DataStore.INSTANCE.getSprite("attack/"+type+"_"+gender+"_n.png");
			attackS = DataStore.INSTANCE.getSprite("attack/"+type+"_"+gender+"_s.png");
			attackE = DataStore.INSTANCE.getSprite("attack/"+type+"_"+gender+"_e.png");
			attackW = DataStore.INSTANCE.getSprite("attack/"+type+"_"+gender+"_w.png");
		}
		catch(Exception e)
		{
			try
			{
				attackX = DataStore.INSTANCE.getSprite("attack/"+type+"_"+gender+".png");
			}
			catch(Exception e1)
			{
				try
				{
					attackX = DataStore.INSTANCE.getSprite("attack/"+type+".png");
				}
				catch(Exception e2)
				{
					try
					{
						attackN = DataStore.INSTANCE.getSprite("attack/"+type+"_n.png");
						attackS = DataStore.INSTANCE.getSprite("attack/"+type+"_s.png");
						attackW = DataStore.INSTANCE.getSprite("attack/"+type+"_w.png");
						attackE = DataStore.INSTANCE.getSprite("attack/"+type+"_e.png");
					}
					catch(Exception e3)
					{
						attackN = DataStore.INSTANCE.getSprite("attack/attack_n.png");
						attackS = DataStore.INSTANCE.getSprite("attack/attack_s.png");
						attackW = DataStore.INSTANCE.getSprite("attack/attack_w.png");
						attackE = DataStore.INSTANCE.getSprite("attack/attack_e.png");
						attackX = DataStore.INSTANCE.getSprite("attack/attack_x.png");
					}
				}
			}
		}
	}
	
	public void step()
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
				if(inRange(game.getPlayer(), attackRange))
				{
					setDirection(getDirectionToward(game.getPlayer()));
					attack();
				}
				
				// if in sight range, move toward
				else if(inRange(game.getPlayer(), sightRange))
				{
					moveToward(game.getPlayer(), attackRange);
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
						//game.getDriver().error("Something went wrong when deleting someone :O", false);
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
	
	/**
	 * Perform a default attack
	 */
	public void attack()
	{
		/*
		 * FIXME: Load attack images and sounds in the background and store them in the
		 * character so that they do not have to be loaded each time (it will be slow)
		 */
		
		try
		{
			ArrayList<Attack> attacks=game.getAttacks();Sprite tempSprite=attackX;
			Attack attack;
			if(gender == 'm')
			{
				if(MAtkHasDirection)
				{
					switch(this.getDirection())
					{
						case Direction.NORTH:
							tempSprite=attackN;
							break;
						case Direction.SOUTH:
							tempSprite=attackS;
							break;
						case Direction.WEST:
							tempSprite=attackW;
							break;
						case Direction.EAST:
							tempSprite=attackE;
							break;
					}
				}
				
				attack		= new Attack(
						game,
						this.getBounds().getCenterX(),
						this.getBounds().getCenterY(),
						MAtkSpd,
						this.getDirection(),
						MAtkName,
						tempSprite,
						false,
						MAtkAoE,
						MAtkDamage,
						MAtkDuration,
						MAtkType,
						MAtkStatus,
						MAtkStatusDuration,
						MAtkStillTime,
						MAtkHits,
						MAtkHitsDelay,
						MAtkReuse);
				
				if(inRange(game.getPlayer(), MAtkRange) && isAgain())
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
			
			else
			{
				if(FAtkHasDirection)
				{
					switch(this.getDirection())
					{
						case Direction.NORTH:
							tempSprite=attackN;
							break;
						case Direction.SOUTH:
							tempSprite=attackS;
							break;
						case Direction.WEST:
							tempSprite=attackW;
							break;
						case Direction.EAST:
							tempSprite=attackE;
							break;
					}
				}
				
				attack		= new Attack(
						game,
						this.getBounds().getCenterX(),
						this.getBounds().getCenterY(),
						FAtkSpd,
						this.getDirection(),
						FAtkName,
						tempSprite,
						false,
						FAtkAoE,
						FAtkDamage,
						FAtkDuration,
						FAtkType,
						FAtkStatus,
						FAtkStatusDuration,
						FAtkStillTime,
						FAtkHits,
						FAtkHitsDelay,
						FAtkReuse);
				
				if(inRange(game.getPlayer(), FAtkRange) && isAgain())
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
		}
		catch(Exception e5)
		{
			System.out.println("A student attack failed for some odd reason. " +
					"Please fix!!!");
		}
	}
	
	/**
	 * Perform an attack
	 * 
	 * @param name Name of attack
	 */
	/*protected void attack(String name)
	{
		int	speed		= 0,
			damage		= 1,
			tp			= 10,
			duration	= 30,
			stillTime	= 30,
			reuse		= 100;
		
		attack(name, speed, damage, tp, duration, stillTime, reuse);
	}*/
	
	/**
	 * Perform an attack
	 * 
	 * @param name Name of attack
	 * @param speed The speed the attack travels
	 * @param damage The damage the attack will deal
	 * @param tp The amount of TP the attack consumes
	 * @param duration The duration the attack stays on screen
	 * @param stillTime The duration the attack makes the user stand still
	 * @param reuse The time the user can perform another attack
	 */
	/*protected void attack(String name, int speed, int damage, int tp, int duration,
			int stillTime, int reuse)
	{
		int hits			= 1;
		
		attack(name, MAtkSpd, MAtkDamage, tp, AttackType.FRONT, duration, stillTime, hits,
				duration / hits, reuse, 0, 0, true);
	}*/
	
	/**
	 * Perform an attack
	 * 
	 * @param name Name of attack
	 * @param speed The speed the attack travels
	 * @param damage The damage the attack will deal
	 * @param tp The amount of TP the attack consumes
	 * @param type The placement of the attack
	 * @param duration The duration the attack stays on screen
	 * @param stillTime The duration the attack makes the user stand still
	 * @param hits The number of hits the attack deals
	 * @param hitDelay The time before the target can be hit again after being hit
	 * @param reuse The time the user can perform another attack
	 * @param status The status effect the attack induces
	 * @param statusDuration The length of the status effect
	 * @param AoE If true, the attack will not disappear upon hitting a target
	 */
	protected void attack(String name, int speed, int damage, int tp, AttackType type,
			int duration, int stillTime, int hits, int hitDelay, int reuse, int status,
			int statusLength, boolean AoE)
	{
		/*
		 * FIXME: Load attack images and sounds in the background and store them in the
		 * character so that they do not have to be loaded each time (it will be slow)
		 */
		/*ArrayList<Attack> attacks=game.getAttacks();
		
		Attack attack		= new Attack(game, this.getBounds().getCenterX(),
				this.getBounds().getCenterY(),
				speed,
				this.getDirection(),
				name,
				false,
				AoE,
				damage,
				tp,
				duration,
				type,
				status,
				statusLength,
				stillTime,
				hits,
				hitDelay,
				reuse);
		
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
		}*/
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
	
	public final static int GANG				= 0;
	public final static int GOTH				= 1;
	public final static int BAND				= 2;
	public final static int SCI					= 3;
	public final static int ROCK				= 4;
	public final static int SPOR				= 5;
	
	public static Student create(Game game, int x, int y, int maxHp, double speed,
			char gender, int type)
	{
		Student student;
		
		switch(type)
		{
			case BAND:
				student							= new BandStudent(game, x, y, maxHp,
						speed, gender);
				break;
		
			case GANG:
				student							= new GangsterStudent(game, x, y, maxHp,
						speed, gender);
				break;
				
			case GOTH:
				student							= new GothStudent(game, x, y, maxHp,
						speed, gender);
				break;
				
			case ROCK:
				student							= new RockerStudent(game, x, y, maxHp,
						speed, gender);
				break;
				
			case SCI:
				student							= new ScientistStudent(game, x, y, maxHp,
						speed, gender);
				break;
				
			default:
			case SPOR:
				student							= new SportStudent(game, x, y, maxHp,
						speed, gender);
				break;
		}
		
		return student;
	}
}