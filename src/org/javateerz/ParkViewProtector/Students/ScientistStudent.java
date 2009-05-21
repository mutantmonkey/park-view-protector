package org.javateerz.ParkViewProtector.Students;

import org.javateerz.ParkViewProtector.Game;

public class ScientistStudent extends Student
{
	public final static double AGGRO		= 0.40;
	
	private static final long serialVersionUID	= 1L;
	
	/**
	 * Create a new scientist student
	 * 
	 * @param game
	 * @param hp
	 * @param maxHp
	 * @param speed
	 * @param gender
	 */
	public ScientistStudent(Game game, int x, int y, int maxHp, double speed, char gender)
	{
		super(game, x, y, maxHp, speed, gender, "science");
		
		if(Math.random() < AGGRO)
			setAggro(true);
	}
}
