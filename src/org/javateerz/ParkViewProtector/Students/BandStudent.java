package org.javateerz.ParkViewProtector.Students;

import org.javateerz.ParkViewProtector.DataStore;
import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.Sprite;
import org.javateerz.ParkViewProtector.Status;
import org.javateerz.ParkViewProtector.Attack.AttackType;

public class BandStudent extends Student
{
	
	
	public final static double AGGRO		= 0.10;
	
	private static final long serialVersionUID	= 1L;
	
	/**
	 * Create a new band student
	 * 
	 * @param game
	 * @param hp
	 * @param maxHp
	 * @param speed
	 * @param gender
	 */
	public BandStudent(Game game, int x, int y, int maxHp, double speed, char gender)
	{
		super(game, x, y, maxHp, speed, gender, "band");
		
		if(Math.random() < AGGRO)
			setAggro(true);
		
		MAtkSpd				= 0;
		MAtkAoE				= true;
		MAtkDamage			= 1;
		MAtkName			= "band_m";
		MAtkDuration		= 2;
		MAtkType			= AttackType.FRONT;
		MAtkStatus			= Status.NONE;
		MAtkStatusDuration	= 0;
		MAtkStillTime		= MAtkDuration+.1;
		MAtkHits			= 3;
		MAtkHitsDelay		= MAtkDuration/MAtkHits;
		MAtkReuse			= MAtkDuration+1;
		MAtkEnemy			= true;
		MAtkHasDirection	= false;
		MAtkRange			= 50;

		FAtkSpd				= 0;
		FAtkAoE				= true;
		FAtkDamage			= 1;
		FAtkName			= "band_f";
		FAtkDuration		= 1;
		FAtkType			= AttackType.FRONT;
		FAtkStatus			= Status.NONE;
		FAtkStatusDuration	= 0;
		FAtkStillTime		= 1;
		FAtkHits			= 1;
		FAtkHitsDelay		= 1;
		FAtkReuse			= 1;
		FAtkEnemy			= true;
		FAtkHasDirection	= false;
		FAtkRange			= 50;
		
		setAttack();
	}
	
	/*public void attack()
	{
		if(gender == 'm')
		{
			attack("band_m", 0, 1, 10, AttackType.FRONT, 120, 300, 3, 10, 100, Status.STUN,
					20, true);
		}
		else {
			attack("band_f");
		}
	}*/
}
