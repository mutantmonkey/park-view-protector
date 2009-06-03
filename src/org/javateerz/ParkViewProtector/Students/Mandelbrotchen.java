package org.javateerz.ParkViewProtector.Students;

import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.Status;
import org.javateerz.ParkViewProtector.Attack.AttackType;

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
		
		MAtkSpd				= 0;
		MAtkAoE				= true;
		MAtkDamage			= 1;
		MAtkName			= "attack";
		MAtkDuration		= 1;
		MAtkType			= AttackType.FRONT;
		MAtkStatus			= Status.NONE;
		MAtkStatusDuration	= 0;
		MAtkStillTime		= MAtkDuration+0.3;
		MAtkHits			= 1;
		MAtkHitsDelay		= MAtkDuration/MAtkHits;
		MAtkReuse			= MAtkDuration+0.6;
		MAtkEnemy			= true;
		MAtkHasDirection	= true;
		MAtkRange			= 40;
		MSight				= 200;

		FAtkSpd				= 0;
		FAtkAoE				= true;
		FAtkDamage			= 1;
		FAtkName			= "attack";
		FAtkDuration		= 1;
		FAtkType			= AttackType.FRONT;
		FAtkStatus			= Status.NONE;
		FAtkStatusDuration	= 0;
		FAtkStillTime		= FAtkDuration+0.3;
		FAtkHits			= 1;
		FAtkHitsDelay		= FAtkDuration/FAtkHits;
		FAtkReuse			= FAtkDuration+0.6;
		FAtkEnemy			= true;
		FAtkHasDirection	= true;
		FAtkRange			= 40;
		FSight				= 200;
		
		setAttack();
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
