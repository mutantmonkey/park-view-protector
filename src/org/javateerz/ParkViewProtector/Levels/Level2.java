package org.javateerz.ParkViewProtector.Levels;

import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.ParkViewProtector;
import org.javateerz.ParkViewProtector.Wall;
import org.javateerz.ParkViewProtector.Bosses.Boss;
import org.javateerz.ParkViewProtector.Bosses.FatMan;
import org.javateerz.ParkViewProtector.Students.Student;

public class Level2 implements BossLevel, Level
{
	public static final int MIN_STUDENTS		= 5;
	public static final int MAX_STUDENTS		= 5;
	public static final int MAX_STUDENT_SPEED	= 1;
	public static final double GENDER_CHANCE	= 0.5;
	
	private Game game;
	
	public Level2(Game g)
	{
		game									= g;
	}
	
	public String getBG()
	{
		return "terrazzo_blue.png";
	}
	
	public String getMusic()
	{
		return "bloated.ogg";
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
		
		int x, y, type, maxHp;
		double speed;
		char gender;
		
		for(int i = 0; i < numStudents; i++)
		{
			x						= (int) (Math.random() * ParkViewProtector.WIDTH);
			y						= (int) (Math.random() * ParkViewProtector.HEIGHT);
			speed					= Math.random() * MAX_STUDENT_SPEED + 1;
			gender					= (Math.random() <= GENDER_CHANCE) ? 'm' : 'f';
			type					= (int)(Math.random()*Student.NUM_STUDENTS);
			maxHp					= (int)(Math.random()*30);
			
			student					= new Student(game, x, y, maxHp, speed, gender, type);
			
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
	
	public Boss getBoss()
	{
		Boss boss=new FatMan(game, 400, 400);
		return boss;
	}

	public ArrayList<Wall> getWalls()
	{
		return new ArrayList<Wall>();
	}

}
