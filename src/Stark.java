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
	public static final double SPEED= 1.0;
	
	/**
	 * Create a new Stark
	 * 
	 * @param x X Location
	 * @param y Y Location
	 * @param hp HP
	 * @param maxHp Max HP
	 * @param tp Teacher Points
	 * @param maxTp Max Teacher Points
	 * @param damage Damage that will be dealt
	 */
	public Stark(int x, int y, int hp, int maxHp, int tp, int maxTp, int damage)
	{
		super(x, y, hp, maxHp, SPEED, tp, maxTp, damage);
		
		sprite = DataStore.INSTANCE.getSprite("images/stark.png");
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
				break;
			case 1:
				break;
			case 2:
				break;
		}
	}
}