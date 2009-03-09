/**
 * Park View Protractor
 * 
 * @author Jason of Javateerz
 *
 * The jawesomeest charater in the hole game
 *
 */

public class SpecialCharacter extends Staff
{
	private static final double SPEED= 5.0;
	private static final int MAX_HP = 50;
	private static final int MAX_TP = 300;
	private static final long serialVersionUID = 4L;
	
	public SpecialCharacter(int x, int y, int hp, int tp)
	{
		super(x, y, hp, MAX_HP, SPEED, tp, MAX_TP);
		updateSprite();
	}

	protected void updateSprite()
	{
		sprite = DataStore.INSTANCE.getSprite("staff/michael.png");
	}
	
	public Attack getAttack(int i)
	{
		Attack attack;
		String		name="attack";
		int			damage=0,
					tp=0,
					type=0,
					speed=0,
					duration=0,
					reuse=duration,
					stillTime=0,
					hits=1,
					hitDelay=duration,
					status=0,
					statusLength=0;
		boolean 	isStudent=true,
					AoE=false;
		/*
		 * FORMAT
		 * 		name=name;
				damage=damage;
				tp=tp;
				type=type;
				speed=speed;
				duration=duration;
				reuse=reuse;
				stillTime=stillTime;
				hits=hits;
				hitDelay=duration/hits;
				status=status;
				statusLength=statusLength;
				isStudent=isStudent;
				AoE=AoE;
		 */
		switch(i)
		{
			case 0:
				name="honk";
				damage=20;
				tp=10;
				type=Type.FRONT;
				speed=0;
				duration=20;
				reuse=40;
				stillTime=10;
				hits=1;
				hitDelay=duration/hits;
				status=Status.STUN;
				statusLength=40;
				isStudent=isStudent;
				AoE=true;
				break;
			case 1:
				name="cake"; //test purposes
				damage=-3;
				type=Type.CENTER;
				tp=250;
				speed=0;
				duration=40;
				reuse=10;
				stillTime=stillTime;
				hits=20;
				hitDelay=duration/hits;
				status=Status.IMMUNE;
				statusLength=100;
				isStudent=false;
				AoE=true;
				break;
			case 2:
				name="squel";
				damage=1;
				tp=25;
				type=Type.CENTER;
				speed=0;
				duration=1;
				reuse=50;
				stillTime=20;
				hits=1;
				hitDelay=duration/hits;
				status=Status.STUN;
				statusLength=1000;
				isStudent=isStudent;
				AoE=true;
				break;
		}
		attack=new Attack(x, y, speed, this.getDirection(), name, isStudent, AoE, damage, tp, duration, type, status, statusLength, stillTime, hits, hitDelay, reuse);
		return attack;
	}
}