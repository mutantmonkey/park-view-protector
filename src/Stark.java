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
	public String getAtkName(int name)
	{
		if(name==0)
			return "physball";
		else if(name==1)
			return "meterstick";
		else if(name==2)
			return "goodnight";
		return "attack";
	}
	
	public int getAtkType(int i)
	{
		if(i==0)
			return 0;
		else if(i==1)
			return 0;
		else if(i==2)
			return 2;
		return 0;
	}
	
	public int getAtkSpee(int i)
	{
		if(i==0)
			return 5;
		else if(i==1)
			return 0;
		else if(i==2)
			return 0;
		return 0;
	}
	
	public int getAtkDama(int i)
	{
		if(i==0)
			return 1;
		else if(i==1)
			return 3;
		else if(i==2)
			return 1;
		return 0;
	}
	
	public int getDuration(int i)
	{
		if(i==0)
			return 40;
		else if(i==1)
			return 10;
		else if(i==2)
			return 50;
		return 0;
	}
}