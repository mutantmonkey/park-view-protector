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
}
