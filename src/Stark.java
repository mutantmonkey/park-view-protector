/**
 * Park View Protractor
 * 
 * @author Jason of Javateerz
 *
 * The jawesomeest charater in the hole game
 *
 */

public class Stark extends Staff
{
	public Stark(int x, int y, int hp, int maxHp, double speed, int tp, int maxTp, int damage)
	{
		super(x, y, hp, maxHp, speed, tp, maxTp, damage);
	}
	
	public void attack()
	{
		//Attack atk=new Attack(x, y, direct, damage, 0, 0);
		//atk.dealDamage();
	}
	public void skill(int ski)
	{
		switch(ski)
		{
			//The skills will be implemented here.
			case 0:		//Recycle
				//Attack atk=new Attack();
				break;
			case 1:
				break;
			case 2:
				break;
		}
	}
}