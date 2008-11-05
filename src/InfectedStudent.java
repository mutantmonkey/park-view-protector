/**
 * Park View Protector
 *
 * @author	Javateerz
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class InfectedStudent extends Student
{
	/**
	 * Create a new infected student
	 * 
	 * @param health	HP of student
	 * @param gend		Gender of student
	 */
	public InfectedStudent(int health, char gend)
	{
		super(health, gend);
	}
}