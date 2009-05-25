package org.javateerz.ParkViewProtector.Levels;

import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.ParkViewProtector;
import org.javateerz.ParkViewProtector.Students.Student;

public abstract class StandardLevel
{
	public ArrayList<Student> getStudents(Game game, int minStudents, int maxStudents,
			double genderChance, int minSpeed, int maxSpeed)
	{
		ArrayList<Student> students	= new ArrayList<Student>();
		
		// create a random number of students using minStudents and maxStudents; multiply
		// it by 2 and divide to ensure that an even number is created to ensure proper
		// coupling
		int numStudents				= (int) (Math.random() * (maxStudents - minStudents + 1)) + minStudents;
		numStudents					= Math.round(numStudents * 2 / 2);
		
		Student student				= null;
		
		int x, y, type, maxHp;
		double speed;
		char gender;
		
		for(int i = 0; i < numStudents; i++)
		{
			x						= (int) (Math.random() * ParkViewProtector.WIDTH);
			y						= (int) (Math.random() * ParkViewProtector.HEIGHT);
			speed					= Math.random() * (maxSpeed - minSpeed) + minSpeed;
			gender					= (Math.random() <= genderChance) ? 'm' : 'f';
			type					= (int)(Math.random()*Student.NUM_STUDENTS);
			maxHp					= (int)(Math.random()*30);
			
			student					= Student.create(game, x, y, maxHp, speed, gender, type);
			
			// make sure that the student is not spawned on top of a wall)
			while(!student.canMove(student.getBounds()))
			{
				x					= (int) (Math.random() * ParkViewProtector.WIDTH) + 1;
				y					= (int) (Math.random() * ParkViewProtector.HEIGHT) + 1;
				
				student.moveTo(x, y);
			}
			
			students.add(student);
		}
		
		return students;
	}
}
