package org.javateerz.ParkViewProtector.Levels;

import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.ParkViewProtector;
import org.javateerz.ParkViewProtector.Wall;
import org.javateerz.ParkViewProtector.Students.*;

public class Level14 implements Level
{
	public static final int MIN_STUDENTS		= 20;
	public static final int MAX_STUDENTS		= 30;
	public static final int STUDENT_MIN_HP		= 50;
	public static final int STUDENT_MAX_HP		= 70;
	public static final int STUDENT_MIN_SPEED	= 2;
	public static final int STUDENT_MAX_SPEED	= 3;
	public static final double GENDER_CHANCE	= 0.5;
	
	private Game game;
	
	public Level14(Game g)
	{
		game									= g;
	}
	
	public String getBG()
	{
		return "terrazzo_blue.png";
	}
	
	public String getMusic()
	{
		return "hornet.ogg";
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
		
		int x, y, hp;
		double speed;
		char gender;
		
		for(int i = 0; i < numStudents; i++)
		{
			x						= (int) (Math.random() * ParkViewProtector.WIDTH);
			y						= (int) (Math.random() * ParkViewProtector.HEIGHT);
			speed					= Math.random() * (STUDENT_MAX_SPEED -
					STUDENT_MIN_SPEED) + STUDENT_MIN_SPEED;
			gender					= (Math.random() <= GENDER_CHANCE) ? 'm' : 'f';
			hp						= (int) (Math.random() * (STUDENT_MAX_HP -
					STUDENT_MIN_HP) + STUDENT_MIN_HP);
			
			// FIXME: Should we make this the drama student level? Or should we have
			// chorus students?
			student					= new ScientistStudent(game, x, y, hp, speed, gender);
			
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
		ArrayList<Wall> walls		= new ArrayList<Wall>();
		
		walls.add(new Wall(game, Wall.NORMAL, 0, 100, 3, 1));
		walls.add(new Wall(game, Wall.NORMAL, 0, 300, 8, 3));
		
		return walls;
	}
}
