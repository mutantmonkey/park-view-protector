/**
 * Park View Protector
 *
 * @author	Javateerz
 */

public class Student extends Entity
{
	private char gender;
	
	private boolean infected;
	
	/**
	 * Create a new student
	 * 
	 * @param health	HP of student
	 * @param maxHealth	Max HP of student
	 * @param spd		Speed of student
	 * @param gend		Gender of student
	 */
	public Student(int health, int maxHealth, double spd, char gend)
	{
		super(health, maxHealth, spd);
		
		gender		= gend;
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
	public boolean getInfected()
	{
		return infected;
	}
	
	/**
	 * Gives the student the "infection"
	 */
	public void infect()
	{
		infected			= true;
	}
	
	/**
	 * Cures the infection of the student the "infection"
	 */
	public void cure()
	{
		infected			= false;
	}
}