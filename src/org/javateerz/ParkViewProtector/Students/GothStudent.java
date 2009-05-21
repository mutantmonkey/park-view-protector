package org.javateerz.ParkViewProtector.Students;

import org.javateerz.ParkViewProtector.Game;

public class GothStudent extends Student
{
	public final static double AGGRO		= 0.30;
	
	private static final long serialVersionUID	= 1L;
	
	/**
	 * Create a new goth student
	 * 
	 * @param game
	 * @param hp
	 * @param maxHp
	 * @param speed
	 * @param gender
	 */
	public GothStudent(Game game, int x, int y, int maxHp, double speed, char gender)
	{
		super(game, x, y, maxHp, speed, gender, "goth");
		
		if(Math.random() < AGGRO)
			setAggro(true);
	}
}
