package org.javateerz.ParkViewProtector.Students;

import org.javateerz.ParkViewProtector.Game;

public class GangsterStudent extends Student
{
	public final static double AGGRO		= 0.80;
	
	private static final long serialVersionUID	= 1L;
	
	/**
	 * Create a new gangster student
	 * 
	 * @param game
	 * @param hp
	 * @param maxHp
	 * @param speed
	 * @param gender
	 */
	public GangsterStudent(Game game, int x, int y, int maxHp, double speed, char gender)
	{
		super(game, x, y, maxHp, speed, gender, "gangster");
		
		if(Math.random() < AGGRO)
			setAggro(true);
	}
}