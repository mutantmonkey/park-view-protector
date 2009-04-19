/**
 * This class stores information about a Student
 *
 * @author	Jamie of the Javateerz
 * @serial
 */

package org.javateerz.ParkViewProtector;

import java.io.*;

import org.javateerz.EasyGL.GLRect;
import org.newdawn.slick.Color;

public class Student extends Character implements Serializable
{
	private static final long serialVersionUID = 2L;
	
	private String stype	= "default";
	
	private char gender;
	private int charge;
	
	/**
	 * Create a new student
	 * 
	 * @param hp		HP of student
	 * @param maxHp		Max HP of student
	 * @param spd		Speed of student
	 * @param gender	Gender of student
	 */
	public Student(int x, int y, int hp, int maxHp, double spd, char gender, int type)
	{
		super(x, y, hp, maxHp, spd);
		
		this.gender		= gender;
		
		// generate a random charge
		this.charge		= (int) (Math.random() * 10 + 1);
		
		// FIXME: this is just for testing; determining type should probably be handled in
		// the driver
		switch(type)
		{
			case Arch.GANG:
				stype = "gangster";
				break;
			case Arch.GOTH:
				stype = "goth";
				break;
			case Arch.BAND:
				stype = "band";
				break;
			default:
				stype = "default";
			break;
		}
		
		updateSprite();
	}
	
	/**
	 * Updates the sprite
	 */
	protected void updateSprite()
	{
		sprite			= DataStore.INSTANCE.getSprite("student/" + stype + "_" + gender + ".png");
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
		// random movement
		game.moveRandom(this);
		
		// decrement the hit delay
		decrementHitDelay(1);
		
		game.recharge(this);
		
		// attempt coupling
		if(charge > 0)
		{
			game.attemptCoupling(this);
		}
		

		// did we hit another student with a charge?
		/*if(currStudent.getBounds().intersects(students.get(j).getBounds())
				&& students.get(j).getCharge() > 0)
		{
				charge				= currStudent.getCharge() +
									students.get(j).getCharge()+1;
			
			if(Math.random() * COUPLE_CHANCE_MULTIPLIER < charge)
			{
				couples.add(new Cupple(currStudent, students.get(j)));
				
				
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
		
		game.handleAttacks(this);
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

class Arch
{
	public final static int GANG=0;
	public final static int GOTH=1;
	public final static int BAND=2;
}