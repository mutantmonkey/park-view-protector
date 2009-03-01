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
	private static final long serialVersionUID = 1L;
	
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
		
		updateSprite();
	}
	
	protected void updateSprite()
	{
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
		int damage=0, type=0, speed=0, duration=0, status=0, statusLength=0, stillTime=0, hits=1, hitDelay=duration, reuse=duration;
		boolean isStudent=false, AoE=false;
		switch(i)
		{
			case 0:
				name="physball";
				type=Type.FRONT;
				damage=5;
				speed=5;
				duration=40;
				reuse=30;
				break;
			case 1:
				name="meterstick";
				type=Type.FRONT;
				damage=10;
				speed=0;
				duration=30;
				stillTime=duration;
				reuse=duration;
				AoE=true;
				hitDelay=duration;
				break;
			case 2:
				name="goodnight";
				type=Type.CENTER;
				damage=3;
				speed=0;
				duration=50;
				stillTime=duration;
				reuse=duration;
				status=Status.STUN;
				statusLength=100;
				AoE=true;
				hitDelay=duration/3;
				break;
		}
		attack=new Attack(x, y, speed, this.getDirection(), name, isStudent, AoE, damage, duration, type, status, statusLength, stillTime, hits, hitDelay, reuse);
		return attack;
	}
}