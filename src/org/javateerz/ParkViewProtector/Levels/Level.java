package org.javateerz.ParkViewProtector.Levels;

import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Student;
import org.javateerz.ParkViewProtector.Wall;

public interface Level
{
	public ArrayList<Student> getStudents();
	public ArrayList<Wall> getWalls();
}
