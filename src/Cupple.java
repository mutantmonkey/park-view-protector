public class Cupple extends Character
{
	private Student male;
	private Student female;
	
	private int x;
	private int y;
	
	public Cupple(int x,int y,int hp,int maxHp,double speed)
	{
		super(x,y,hp,maxHp,speed);
	}
	
	public void makeCupple(Student a,Student b)
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
		hp = a.getHp()*b.getHp();
		speed = (a.getSpeed()+b.getSpeed)/2;
	}
	
	public Student getMale()
	{
		return male;
	}
	
	public Student getFemale()
	{
		return female;
	}
}