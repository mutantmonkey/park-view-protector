package org.javateerz.ParkViewProtector.Students;

import org.javateerz.ParkViewProtector.Game;

public class Mandelbrotchen extends Student
{
	private static final long serialVersionUID	= 1L;
	
	/**
	 * Create a new Mandelbrotchen
	 * 
	 * @param game
	 * @param hp
	 * @param maxHp
	 * @param speed
	 */
	public Mandelbrotchen(Game game, int x, int y, int maxHp, double speed)
	{
		super(game, x, y, maxHp, speed, 'm', "mandelbrot");
		
		setAggro(true);
	}
	
	public boolean handleAttacks()
	{
		boolean ret				= super.handleAttacks();
		
		// WARNING: I haven't checked, but this may cause one loop in Game to be off by
		// one on the student count. But since the loops run so quickly, I don't think
		// that this will create a noticeable problem.
		if(hp <= 0)
		{
			game.getStudents().remove(game.getStudents().indexOf(this));
		}
			
		return ret;
	}
}
