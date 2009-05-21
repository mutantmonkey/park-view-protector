package org.javateerz.ParkViewProtector.Levels;

import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Wall;
import org.javateerz.ParkViewProtector.Students.Student;

public interface Level
{
	public String getBG();
	public String getMusic();
	public ArrayList<Student> getStudents();
	public ArrayList<Wall> getWalls();
}
