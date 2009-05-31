package org.javateerz.ParkViewProtector.Levels;

import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.Location;
import org.javateerz.ParkViewProtector.ParkViewProtector;
import org.javateerz.ParkViewProtector.Wall;
import org.javateerz.ParkViewProtector.Students.*;

public class Level6 implements Level
{
	public static final int MIN_STUDENTS		= 10;
	public static final int MAX_STUDENTS		= 20;
	public static final int MIN_STUDENT_SPEED	= 4;
	public static final int MAX_STUDENT_SPEED	= 5;
	public static final double GENDER_CHANCE	= 0.5;
	
	private Game game;
	
	public Level6(Game g)
	{
		game									= g;
	}
	
	public String getBG()
	{
		return "terrazzo_green.png";
	}
	
	public String getMusic()
	{
		return "twice.ogg";
	}
	
	public Location getStartLocation()
	{
		return new Location(100, 100);
	}
	
	public ArrayList<Student> getStudents()
	{
		ArrayList<Student> students	= new ArrayList<Student>();
		
		// create a random number of students using MIN_STUDENTS and MAX_STUDENTS; multiply
		// it by 2 and divide to ensure that an even number is created to ensure proper
		// coupling
		int numStudents				= (int) (Math.random() * (MAX_STUDENTS - MIN_STUDENTS + 1)) + MIN_STUDENTS;
		numStudents					= Math.round(numStudents * 2 / 2);
		
		Student student				= null;
		
		int x, y, maxHp;
		double speed;
		char gender;
		
		for(int i = 0; i < numStudents; i++)
		{
			x						= (int) (Math.random() * ParkViewProtector.WIDTH);
			y						= (int) (Math.random() * ParkViewProtector.HEIGHT);
			speed					= Math.random() * (MAX_STUDENT_SPEED -
					MIN_STUDENT_SPEED) + MIN_STUDENT_SPEED;
			gender					= (Math.random() <= GENDER_CHANCE) ? 'm' : 'f';
			maxHp					= (int)(Math.random()*30);
			
			student					= new SportStudent(game, x, y, maxHp, speed, gender);
			
			// make sure that the student is not spawned on top of a wall)
			while(!student.canMove(student.getBounds()))
			{
				x					= (int) (Math.random() * ParkViewProtector.WIDTH) + 1;
				y					= (int) (Math.random() * ParkViewProtector.HEIGHT) + 1;
				
				student.moveTo(x, y);
			}
			
			student.setAggro(true);
			
			students.add(student);
		}
		
		return students;
	}
	
	public ArrayList<Wall> getWalls()
	{
		return new ArrayList<Wall>();
	}
}
