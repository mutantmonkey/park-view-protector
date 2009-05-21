package org.javateerz.ParkViewProtector.Students;

import org.javateerz.ParkViewProtector.Game;

public class RockerStudent extends Student
{
	public final static double AGGRO		= 0.60;
	
	private static final long serialVersionUID	= 1L;
	
	/**
	 * Create a new rocker student
	 * 
	 * @param game
	 * @param hp
	 * @param maxHp
	 * @param speed
	 * @param gender
	 */
	public RockerStudent(Game game, int x, int y, int maxHp, double speed, char gender)
	{
		super(game, x, y, maxHp, speed, gender, "rocker");
		
		if(Math.random() < AGGRO)
			setAggro(true);
	}
}
