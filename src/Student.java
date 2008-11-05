/**
 * Park View Protector
 *
 * @author	Javateerz
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Student extends Entity
{
	char gender;
	
	/**
	 * Create a new student
	 * 
	 * @param health	HP of student
	 * @param gend		Gender of student
	 */
	public Student(int health, char gend)
	{
		super(health);
		
		gender		= gend;
	}
}