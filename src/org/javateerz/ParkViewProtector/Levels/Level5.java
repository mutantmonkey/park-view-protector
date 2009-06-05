package org.javateerz.ParkViewProtector.Levels;

import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.Location;
import org.javateerz.ParkViewProtector.Wall;
import org.javateerz.ParkViewProtector.Bosses.Boss;
import org.javateerz.ParkViewProtector.Bosses.FatMan;
import org.javateerz.ParkViewProtector.Students.Student;

public class Level5 extends StandardLevel implements BossLevel, Level
{
	public static final int MIN_STUDENTS		= 5;
	public static final int MAX_STUDENTS		= 5;
	public static final int STUDENT_MIN_HP		= 10;
	public static final int STUDENT_MAX_HP		= 30;
	public static final int STUDENT_MIN_SPEED	= 1;
	public static final int STUDENT_MAX_SPEED	= 2;
	public static final double GENDER_CHANCE	= 0.5;
	
	private Game game;
	private Boss boss;
	
	public Level5(Game g)
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
	
	public Location getStartLocation()
	{
		return new Location(100, 100);
	}
	
	public ArrayList<Student> getStudents()
	{
		return getStudents(game, MIN_STUDENTS, MAX_STUDENTS, GENDER_CHANCE,
				STUDENT_MIN_HP, STUDENT_MAX_HP, STUDENT_MIN_SPEED, STUDENT_MAX_SPEED);
	}
	
	public Boss getBoss()
	{
		boss=new FatMan(game, 400, 400);
		return boss;
	}

	public ArrayList<Wall> getWalls()
	{
		return new ArrayList<Wall>();
	}
	
	public boolean levelComplete()
	{
		return boss.getHp() <= 0;
	}
}
