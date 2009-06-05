package org.javateerz.ParkViewProtector.Levels;

import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.Location;
import org.javateerz.ParkViewProtector.Wall;
import org.javateerz.ParkViewProtector.Students.*;

public class Level12 extends StandardLevel implements Level
{
	public static final int MIN_STUDENTS		= 42;
	public static final int MAX_STUDENTS		= 42;
	public static final int STUDENT_MIN_HP		= 30;
	public static final int STUDENT_MAX_HP		= 40;
	public static final int STUDENT_MIN_SPEED	= 2;
	public static final int STUDENT_MAX_SPEED	= 3;
	public static final double GENDER_CHANCE	= 0.5;
	
	private Game game;
	
	public Level12(Game g)
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
		return new Location(20, 30);
	}
	
	public ArrayList<Student> getStudents()
	{
		return getStudents(game, MIN_STUDENTS, MAX_STUDENTS, GENDER_CHANCE,
				STUDENT_MIN_HP, STUDENT_MAX_HP, STUDENT_MIN_SPEED, STUDENT_MAX_SPEED);
	}
	
	public ArrayList<Wall> getWalls() 
	{
		ArrayList<Wall> walls		= new ArrayList<Wall>();
		
		walls.add(new Wall(game, Wall.NORMAL, 0, 200, 5, 4));
		
		return walls;
	}
}
