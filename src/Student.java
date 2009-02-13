/**
 * This class stores information about a Student
 *
 * @author	Jamie of the Javateerz
 * @serial
 */

import java.io.*;

public class Student extends Character implements Serializable
{
	private static final long serialVersionUID = 0;
	
	private String type	= "default";
	
	private char gender;
	private boolean infected;
	
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
		
		updateSprite();
	}
	
	/**
	 * Updates the sprite
	 */
	private void updateSprite()
	{
		String dir		= infected ? "student_infected" : "student";
		
		sprite			= DataStore.INSTANCE.getSprite("images/" + dir + "/" + type + ".png");
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
	 * Returns whether or not the student is "infected"
	 * 
	 * @return
	 */
	public boolean isInfected()
	{
		return infected;
	}
	
	/**
	 * Gives the student the "infection"
	 */
	public void infect()
	{
		infected			= true;
		
		updateSprite();
	}
	
	/**
	 * Cures the infection of the student the "infection"
	 */
	public void cure()
	{
		infected			= false;
		
		updateSprite();
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