import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * Park View Protractor
 * 
 * @author Jason of Javateerz
 *
 * The jawesomeest charater in the hole game
 *
 */

public class Stark extends Staff implements Serializable
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
		super(x, y, hp, maxHp, SPEED, damage, tp, maxTp);
		
		sprite = DataStore.INSTANCE.getSprite("images/staff/stark.png");
	}
	
	public void attack()
	{
		
	}
	
	public void skill(int i)
	{
		
	}
	
	public Attack getAttack(int i)
	{
		Attack attack;
		String name="attack";
		int damage=0, type=0, speed=0, duration=0, status=0;
		switch(i)
		{
			case 0:
				name="physball";
				type=0;
				damage=5;
				speed=5;
				duration=40;
				break;
			case 1:
				name="meterstick";
				type=0;
				damage=10;
				speed=0;
				duration=30;
				break;
			case 2:
				name="goodnight";
				type=2;
				damage=3;
				speed=0;
				duration=50;
				status=1;
				break;
		}
		attack=new Attack(x,y,speed, name, this.getDirection(), damage, duration, true, type, status);
		return attack;
	}
}