package org.javateerz.ParkViewProtector.Students;

import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.Status;
import org.javateerz.ParkViewProtector.Attack.AttackType;

public class Mandelbrotchen extends Student
{
	protected int 			MAtkSpd				= 0;
	protected boolean		MAtkAoE				= true;
	protected int			MAtkDamage			= 1;
	protected double		MAtkDuration		= 1;
	protected AttackType	MAtkType			= AttackType.FRONT;
	protected int			MAtkStatus			= Status.NONE;
	protected double		MAtkStatusDuration	= 0;
	protected double		MAtkStillTime		= 1;
	protected int			MAtkHits			= 1;
	protected double		MAtkHitsDelay		= 1;
	protected double		MAtkReuse			= 1;
	protected boolean		MAtkEnemy			= true;
	protected boolean		MAtkHasDirection	= false;
	protected int			MAtkRange			= 50;

	protected int 			FAtkSpd				= 0;
	protected boolean		FAtkAoE				= true;
	protected int			FAtkDamage			= 1;
	protected double		FAtkDuration		= 1;
	protected AttackType	FAtkType			= AttackType.FRONT;
	protected int			FAtkStatus			= Status.NONE;
	protected double		FAtkStatusDuration	= 0;
	protected double		FAtkStillTime		= 1;
	protected int			FAtkHits			= 1;
	protected double		FAtkHitsDelay		= 1;
	protected double		FAtkReuse			= 1;
	protected boolean		FAtkEnemy			= true;
	protected boolean		FAtkHasDirection	= false;
	protected int			FAtkRange			= 50;
	
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
