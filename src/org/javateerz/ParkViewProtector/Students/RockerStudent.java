package org.javateerz.ParkViewProtector.Students;

import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.Status;
import org.javateerz.ParkViewProtector.Attack.AttackType;

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
}
