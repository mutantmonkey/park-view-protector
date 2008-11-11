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
	 * @param hp		HP of student
	 * @param maxHp		Max HP of student
	 * @param spd		Speed of student
	 * @param gender	Gender of student
	 */
	public Student(int x, int y, int hp, int maxHp, double spd, char gender)
	{
		super(x, y, hp, maxHp, spd);
		
		this.gender		= gender;
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
	}
	
	/**
	 * Cures the infection of the student the "infection"
	 */
	public void cure()
	{
		infected			= false;
	}
}