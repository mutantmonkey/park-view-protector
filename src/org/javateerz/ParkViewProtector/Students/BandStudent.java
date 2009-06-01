package org.javateerz.ParkViewProtector.Students;

import org.javateerz.ParkViewProtector.DataStore;
import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.Sprite;
import org.javateerz.ParkViewProtector.Status;
import org.javateerz.ParkViewProtector.Attack.AttackType;

public class BandStudent extends Student
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
