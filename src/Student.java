/**
 * This class stores information about a Student
 *
 * @author	Jamie of the Javateerz
 * @serial
 */

import java.io.*;

public class Student extends Character implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String type	= "default";
	
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
	public Student(int x, int y, int hp, int maxHp, double spd, int dmg, char gender)
	{
		super(x, y, hp, maxHp, spd, dmg);
		
		this.gender		= gender;
		
		// generate a random charge
		this.charge		= (int) (Math.random() * 10 + 1);
		
		// FIXME: this is just for testing; determining type should probably be handled in
		// the driver
		if(Math.random() < 0.4)
		{
			type = "goth";
		}
		else {
			type = "gangster";
		}
		
		updateSprite();
	}
	
	/**
	 * Updates the sprite
	 */
	private void updateSprite()
	{
		sprite			= DataStore.INSTANCE.getSprite("images/student/" + type + "_" + gender + ".png");
	}
	
	/**
	 * Returns the gender of the student
	 * 
	 * @return
	 */
	public char getGender()
	{
		return gender;
	}
	
	/**
	 * Returns the charge of the student
	 * 
	 * @return
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
	
	/**
	 * Does an attack
	 */
	public void attack()
	{
		
	}
	
	public void changeGraphic()
	{
		this.sprite		= DataStore.INSTANCE.getSprite("images/placeholder.png");
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