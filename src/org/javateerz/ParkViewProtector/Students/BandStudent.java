package org.javateerz.ParkViewProtector.Students;

import org.javateerz.ParkViewProtector.Game;
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
	}
	
	public void attack()
	{
		if(gender == 'm')
		{
			attack("band_m", 0, 1, 10, AttackType.FRONT, 120, 300, 3, 10, 100, Status.STUN,
					20, true);
		}
		else {
			attack("band_f");
		}
	}
}
