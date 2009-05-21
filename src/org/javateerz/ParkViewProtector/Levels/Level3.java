package org.javateerz.ParkViewProtector.Levels;

import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.Mandelbrot;
import org.javateerz.ParkViewProtector.Wall;
import org.javateerz.ParkViewProtector.Bosses.Boss;
import org.javateerz.ParkViewProtector.Students.Student;

public class Level3 implements BossLevel, Level
{
	public static final int MIN_STUDENTS		= 5;
	public static final int MAX_STUDENTS		= 5;
	public static final int MAX_STUDENT_SPEED	= 1;
	public static final double GENDER_CHANCE	= 0.5;
	
	private Game game;
	
	public Level3(Game g)
	{
		game									= g;
	}
	
	public String getBG()
	{
		return "crazy.png";
	}
	
	public String getMusic()
	{
		return "bloated.ogg";
	}
	
	public ArrayList<Student> getStudents()
	{
		ArrayList<Student> students	= new ArrayList<Student>();
		
		return students;
	}
	
	public Boss getBoss()
	{
		Boss boss=new Mandelbrot(game, 0, 0);
		return boss;
	}

	public ArrayList<Wall> getWalls()
	{
		return new ArrayList<Wall>();
	}
}
