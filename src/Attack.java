import java.util.ArrayList;

/**
 * Park View Protector
 *
 * @author	Javateerz
 * 
 * Creates an attack area
 */

public class Attack
{
	private int x;
	private int y;
	private int direct;
	private int damage;
	//Target: 0=Student, 1=Staff
	private int target;
	/*
	 * Shape of the attack?
	 * 0=Front
	 * 1=Surrounding
	 * ect.
	 */
	private int type;
	
	public Attack(int x, int y, int direct, int damage, int target, int type)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
		this.damage=damage;
		this.target=target;
		this.type=type;
	}
	
	//I don't know why it's erroring
	public ArrayList detect()
	{
		ArrayList targets;
		switch(type)
		{
			//type of attacks to impletmented here.
			case 0:
				//if(targ instanceof Student)
				//targets.add(targ)
				break;
		}
		return targets;
	}
	
	public void dealDamage()
	{
		ArrayList targets=detect();
		for(int i; i<targets.size(); i++)
		{
			targets.get(i).adjustHP(damage);
		}
	}
}
