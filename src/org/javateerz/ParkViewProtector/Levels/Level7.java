package org.javateerz.ParkViewProtector.Levels;

import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.Wall;
import org.javateerz.ParkViewProtector.Students.*;

public class Level7 extends StandardLevel implements Level
{
	public static final int MIN_STUDENTS		= 10;
	public static final int MAX_STUDENTS		= 20;
	public static final int STUDENT_MIN_HP		= 20;
	public static final int STUDENT_MAX_HP		= 50;
	public static final int STUDENT_MIN_SPEED	= 2;
	public static final int STUDENT_MAX_SPEED	= 4;
	public static final double GENDER_CHANCE	= 0.5;
	
	private Game game;
	
	public Level7(Game g)
	{
		game									= g;
	}
	
	public String getBG()
	{
		return "terrazzo_blue.png";
	}
	
	public String getMusic()
	{
		return "heavyset.ogg";
	}
	
	public ArrayList<Student> getStudents()
	{
		return getStudents(game, MIN_STUDENTS, MAX_STUDENTS, GENDER_CHANCE,
				STUDENT_MIN_HP, STUDENT_MAX_HP, STUDENT_MIN_SPEED, STUDENT_MAX_SPEED);
	}
	
	public ArrayList<Wall> getWalls() 
	{
		ArrayList<Wall> walls		= new ArrayList<Wall>();
		
		walls.add(new Wall(game, Wall.NORMAL, 0, 100, 1, 5));
		walls.add(new Wall(game, Wall.NORMAL, 200, 0, 2, 5));
		walls.add(new Wall(game, Wall.NORMAL, 500, 100, 1, 5));
		
		return walls;
	}
}
