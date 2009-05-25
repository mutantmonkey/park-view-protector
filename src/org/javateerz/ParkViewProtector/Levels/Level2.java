package org.javateerz.ParkViewProtector.Levels;

import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.Wall;
import org.javateerz.ParkViewProtector.Students.Student;

public class Level2 extends StandardLevel implements Level
{
	public static final int MIN_STUDENTS		= 20;
	public static final int MAX_STUDENTS		= 30;
	public static final int MIN_STUDENT_SPEED	= 1;
	public static final int MAX_STUDENT_SPEED	= 2;
	public static final double GENDER_CHANCE	= 0.5;
	
	private Game game;
	
	public Level2(Game g)
	{
		game									= g;
	}
	
	public String getBG()
	{
		return "terrazzo_green.png";
	}
	
	public String getMusic()
	{
		return "heavyset.ogg";
	}
	
	public ArrayList<Student> getStudents()
	{
		return getStudents(game, MIN_STUDENTS, MAX_STUDENTS, GENDER_CHANCE,
				MIN_STUDENT_SPEED, MAX_STUDENT_SPEED);
	}
	
	public ArrayList<Wall> getWalls() 
	{
		ArrayList<Wall> walls		= new ArrayList<Wall>();
		
		walls.add(new Wall(game, Wall.NORMAL, 100, 100, 1, 5));
		walls.add(new Wall(game, Wall.NORMAL, 600, 100, 1, 5));
		
		return walls;
	}
}
