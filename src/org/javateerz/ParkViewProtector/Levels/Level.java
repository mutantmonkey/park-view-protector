package org.javateerz.ParkViewProtector.Levels;

import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Boss;
import org.javateerz.ParkViewProtector.Student;
import org.javateerz.ParkViewProtector.Wall;

public interface Level
{
	public String getMusic();
	public ArrayList<Student> getStudents();
	public ArrayList<Wall> getWalls();
	public Boss getBoss();
}
