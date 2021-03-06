package org.javateerz.ParkViewProtector.Levels;

import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.Location;
import org.javateerz.ParkViewProtector.Wall;
import org.javateerz.ParkViewProtector.Bosses.Boss;
import org.javateerz.ParkViewProtector.Bosses.Mandelbrot;
import org.javateerz.ParkViewProtector.Students.Student;

public class Level15 implements BossLevel, Level
{
	private Game game;
	private Boss boss;
	
	public Level15(Game g)
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
	
	public Location getStartLocation()
	{
		return new Location(100, 100);
	}
	
	public ArrayList<Student> getStudents()
	{
		ArrayList<Student> students	= new ArrayList<Student>();
		
		return students;
	}
	
	public Boss getBoss()
	{
		boss						= new Mandelbrot(game, 0, 0);
		
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
