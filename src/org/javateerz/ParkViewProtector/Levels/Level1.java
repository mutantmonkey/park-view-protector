package org.javateerz.ParkViewProtector.Levels;

import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Wall;

public class Level1 implements Level
{
	public ArrayList<Wall> getWalls() 
	{
		ArrayList<Wall> walls			= new ArrayList<Wall>();
		
		walls.add(new Wall(Wall.NORMAL,100,100,4,4));
		walls.add(new Wall(Wall.NARROW_V, 600, 100, 1,4));
		
		return walls;
	}
}
