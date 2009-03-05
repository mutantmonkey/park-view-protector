/**
 * This class stores information about a couple ("cupple")
 *
 * @author	Jamie of the Javateerz
 * @serial
 */

import java.io.*;

public class Cupple extends Character implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Student male;
	private Student female;
	
	private int x;
	private int y;
	
	public Cupple(Student a,Student b)
	{
		super(a.x,a.y,a.hp+b.hp,a.maxHp + b.maxHp,(a.speed+b.speed)/2,a.damage);
		if(a.getGender() == 'm')
		{
			male = a;
			female = b;
		}
		else
		{
			male = b;
			female = a;
		}
		
		// temp graphic
		updateSprite();
	}
	
	protected void updateSprite()
	{
		sprite	= DataStore.INSTANCE.getSprite("images/cupple.png");
	}
	
	public Student getMale()
	{
		return male;
	}
	
	public Student getFemale()
	{
		return female;
	}
	
	public void step(Game game)
	{
		// random movement
		game.moveRandom(this);
		
		// decrement the hit delay
		decrementHitDelay(1);
		
		game.handleAttacks(this);
	}

	public void attack()
	{
		// TODO: insert code here
	}
	
	private void readObject(ObjectInputStream os) throws ClassNotFoundException, IOException
	{
		os.defaultReadObject();
		
		validateState();
	}
	
	private void writeObject(ObjectOutputStream os) throws IOException
	{
		os.defaultWriteObject();
	}
}