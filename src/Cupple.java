public class Cupple
{
	private Student male;
	private Student female;
	
	private int hp;
	private double speed;
	
	public Cupple(Student a,Student b)
	{
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
		hp = a.getHealth()*b.getHealth()
		speed = (a.getSpeed()+b.getSpeed)/2;
	}
}