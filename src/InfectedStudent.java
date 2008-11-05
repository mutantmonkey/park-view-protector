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
	 * @param health	HP of infected student
	 * @param spd		Speed of infected student
	 * @param gend		Gender of infected student
	 */
	public InfectedStudent(int health, double spd, char gend)
	{
		super(health, spd, gend);
	}
}