package org.javateerz.ParkViewProtector.Levels;

import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.Wall;
import org.javateerz.ParkViewProtector.Students.*;

public class Level11 extends StandardLevel implements Level
{
	public static final int MIN_STUDENTS		= 30;
	public static final int MAX_STUDENTS		= 32;
	public static final int MIN_STUDENT_SPEED	= 1;
	public static final int MAX_STUDENT_SPEED	= 5;
	public static final double GENDER_CHANCE	= 0.5;
	
	private Game game;
	
	public Level11(Game g)
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
		
		walls.add(new Wall(game, Wall.NORMAL, 100, 100, 4, 2));
		walls.add(new Wall(game, Wall.NORMAL, 200, 200, 1, 4));
		walls.add(new Wall(game, Wall.NORMAL, 500, 100, 1, 3));
		
		return walls;
	}
}
