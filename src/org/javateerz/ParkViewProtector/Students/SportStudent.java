package org.javateerz.ParkViewProtector.Students;

import org.javateerz.ParkViewProtector.Game;

public class SportStudent extends Student
{
	public final static double AGGRO		= 0.90;
	
	private static final long serialVersionUID	= 1L;
	
	/**
	 * Create a new sport student
	 * 
	 * @param game
	 * @param hp
	 * @param maxHp
	 * @param speed
	 * @param gender
	 */
	public SportStudent(Game game, int x, int y, int maxHp, double speed, char gender)
	{
		super(game, x, y, maxHp, speed, gender, "sport");
		
		if(Math.random() < AGGRO)
			setAggro(true);
	}
}
