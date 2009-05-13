package org.javateerz.ParkViewProtector.Levels;

import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.ParkViewProtector;
import org.javateerz.ParkViewProtector.Student;
import org.javateerz.ParkViewProtector.Wall;

public class Level1 implements Level
{
	public static final int MIN_STUDENTS		= 20;
	public static final int MAX_STUDENTS		= 30;
	public static final int MAX_STUDENT_SPEED	= 1;
	public static final double GENDER_CHANCE	= 0.5;
	
	private Game game;
	
	public Level1(Game g)
	{
		game									= g;
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
		
		int x, y, type;
		double speed;
		char gender;
		
		for(int i = 0; i < numStudents; i++)
		{
			x						= (int) (Math.random() * ParkViewProtector.WIDTH);
			y						= (int) (Math.random() * ParkViewProtector.HEIGHT);
			speed					= Math.random() * MAX_STUDENT_SPEED + 1;
			gender					= (Math.random() <= GENDER_CHANCE) ? 'm' : 'f';
			type					= (int)(Math.random()*3);
			
			student					= new Student(x, y, 5, 5, speed, gender, type);
			
			// make sure that the student is not spawned on top of a wall)
			while(!game.canMove(student.getBounds(), student))
			{
				x					= (int) (Math.random() * ParkViewProtector.WIDTH) + 1;
				y					= (int) (Math.random() * ParkViewProtector.HEIGHT) + 1;
				
				student.moveTo(x, y);
			}
			
			students.add(student);
		}
		
		return students;
	}
	
	public ArrayList<Wall> getWalls() 
	{
		ArrayList<Wall> walls		= new ArrayList<Wall>();
		
		walls.add(new Wall(Wall.NORMAL, 100, 100, 4, 4));
		walls.add(new Wall(Wall.NARROW_V, 600, 100, 1, 4));
		
		return walls;
	}
}
