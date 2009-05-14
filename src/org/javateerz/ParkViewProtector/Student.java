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
	public final static int GANG=0;
	public final static int GOTH=1;
	public final static int BAND=2;
	
	private static final long serialVersionUID = 2L;
	
	private String type	= "default";
	
	private char gender;
	private int charge;
	private int chargeRegenRate=0;
	private final int CHARGE_REGEN_RATE=10;
	
	/**
	 * Create a new student
	 * 
	 * @param g			Instance of Game
	 * @param hp		HP of student
	 * @param maxHp		Max HP of student
	 * @param spd		Speed of student
	 * @param gender	Gender of student
	 */
	public Student(Game g, int x, int y, int hp, int maxHp, double spd, char gender,
			int type)
	{
		super(g, x, y, hp, maxHp, spd);
		
		this.gender		= gender;
		
		// generate a random charge
		this.charge		= (int) (Math.random() * 10 + 1);
		
		// FIXME: this is just for testing; determining type should probably be handled in
		// the driver
		switch(type)
		{
			case Student.GANG:
				this.type = "gangster";
				break;
			case Student.GOTH:
				this.type = "goth";
				break;
			case Student.BAND:
				this.type = "band";
				break;
			default:
				this.type = "default";
			break;
		}
		
		// give the student some items
		int random = (int)(Math.random()*4);
		//there is a 1/2 chance of a health item, 1/4 chance of a teacher item, and 1/4 chance of no item
		switch(random)
		{
			case 0:
			case 1:
				pickItem(new Item(g, 'h', 0, 0));
				break;
				
			case 2:
				pickItem(new Item(g, 't', 0, 0));
				break;
				
			default:
				break;
		}
		
		updateSprite();
	}
	
	/**
	 * Updates the sprite
	 */
	protected void updateSprite()
	{
		sprite			= DataStore.INSTANCE.getSprite("student/" + type + "_" + gender + ".png");
	}
	
	/**
	 * @return The type of the student (used for the sprite)
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
	 * @return The charge of the student
	 */
	public int getCharge()
	{
		return charge;
	}
	
	/**
	 * Increases the charge of the student by the specified amount
	 * 
	 * @param amt Amount to add to charge
	 */
	public void adjustCharge(int amt)
	{
		charge			   += amt;
	}
	
	public void setCharge(int amt)
	{
		charge=amt;
	}
	
	public void step(Game game)
	{
		if(charge > 0)
		{
			attemptCoupling();
		}
		
		// random movement
		moveRandom(this);
		
		// decrement the hit delay
		recover();
		
		recharge();
		
		// attempt coupling
		

		// did we hit another student with a charge?
		/*if(currStudent.getBounds().intersects(students.get(j).getBounds())
				&& students.get(j).getCharge() > 0)
		{
				charge				= currStudent.getCharge() +
									students.get(j).getCharge()+1;
			
			if(Math.random() * COUPLE_CHANCE_MULTIPLIER < charge)
			{
				couples.add(new Couple(currStudent, students.get(j)));
				
				
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
					System.out.println("Something went wrong when deleting someone :O");
				}
				break;
			}
		}*/
		
		handleAttacks();
	}
	
	/**
	 * Recharge students
	 */
	public void recharge()
	{
		chargeRegenRate+=1;
		if(chargeRegenRate>=CHARGE_REGEN_RATE)
		{
			chargeRegenRate=0;
			if(getCharge()<100)
				adjustCharge(1);
		}
	}
	
	/**
	 * Attempt to couple with another student
	 * 
	 * @param currStudent
	 * @return Whether or not the coupling succeeded
	 */
	public boolean attemptCoupling()
	{
		int charge, student1, student2;
		Student testStudent;
		ArrayList<Student> students=game.getStudents();
		ArrayList<Couple> couples=game.getCouples();
		
		int i						= students.indexOf(this);
		
		// prevent coupling if we intersect a wall
		if(!canMove(getBounds()))
		{
			return false;
		}
		
		for(int j = 0; j < students.size(); j++)
		{
			testStudent				= students.get(j);
			
			// don't do anything if it's us
			if(i == j)
			{
				continue;
			}
			
			// no same-sex couples (for now at least)
			if(getGender() == testStudent.getGender())
			{
				continue;
			}

			// did we hit another student with a charge?
			if(getNewBounds(game.MOVE_SPEED).intersects(students.get(j).getBounds())
					&& testStudent.getCharge() > 0)
			{
				charge				= getCharge() +
										testStudent.getCharge();
				
				if(Math.random() * game.COUPLE_CHANCE_MULTIPLIER < charge)
				{
					couples.add(new Couple(game, this, testStudent));
					
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
		Attack currAttack;
		ArrayList<Attack> attacks=game.getAttacks();
		
		// hit by an attack?
		for(int j = 0; j < attacks.size(); j++)
		{
			currAttack		= attacks.get(j);
			
			//The student takes damage in this if loop
			if(currAttack.getBounds().intersects(getBounds()) &&
					/*getCharge() > 0 && */isVulnerable() &&
					currAttack.isStudent())
			{
				game.hitFX((int)(getBounds().getCenterX()-getBounds().getWidth()/4),
						(int)(getBounds().getCenterY()-getBounds().getHeight()/4));
				
				if(currAttack.getStatus()==Status.STUN)
				{
					setStunFrames(currAttack.getStatusDuration());
				}
				
				if(!currAttack.isAoE())
				{
					attacks.remove(j);
				}
				
				// FIXME: should be variable depending on strength
				if(getCharge()>-10)
					adjustCharge(-currAttack.getDamage()/3);
				//System.out.println("Student took "+ currAttack.getDamage()/3 + ", now has "+ getCharge());
				setInvulFrames(currAttack.getHitDelay());
				
				return true;
			}
		}
		
		return false;
	}
	
	public void attack()
	{
		
	}
	
	public void showCharge()
	{
		GLRect rect				= new GLRect((int) x, (int) y, (int) getBounds().getWidth(),
				(int) getBounds().getHeight());
		rect.setColor(new Color(ParkViewProtector.COLOR_BG_1.getRed(),
				ParkViewProtector.COLOR_BG_1.getGreen(),
				ParkViewProtector.COLOR_BG_1.getBlue(), 5));
		rect.draw();

		Bar chargeBar = new Bar(ParkViewProtector.STATS_BAR_HP,(int)(getBounds().getWidth()), (double)charge/100);
		chargeBar.draw((int)x,(int)y);
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