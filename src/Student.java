/**
 * Park View Protector
 *
 * @author	Javateerz
 */

public class Student extends Entity
{
	char gender;
	
	/**
	 * Create a new student
	 * 
	 * @param health	HP of student
	 * @param spd		Speed of student
	 * @param gend		Gender of student
	 */
	public Student(int health, double spd, char gend)
	{
		super(health, spd);
		
		gender		= gend;
	}
}