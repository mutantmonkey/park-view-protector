public class Cupple extends Character
{
	private Student male;
	private Student female;
	
	private int x;
	private int y;
	
	public Cupple(int x,int y,int hp,int maxHp,double speed)
	{
		super(hp,maxHp,speed);
		this.x = x;
		this.y = y;
	}
	
	public Cupple(Student a,Student b)
	{
		super();
		if(a.getGender() == 'm')
		{
			male = new Student(x,y,a.getHp(),a.getMaxHp(),a.getSpeed(),'m');
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