public class Cupple extends Character
{
	private Student male;
	private Student female;
	
	private int x;
	private int y;
	
	public Cupple(Student a,Student b)
	{
		super(a.x,a.y,a.hp+b.hp,a.maxHp,(a.speed+b.speed)/4,a.damage);
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
		this.sprite	= DataStore.INSTANCE.getSprite("images/cupple.png");
	}
	
	public Student getMale()
	{
		return male;
	}
	
	public Student getFemale()
	{
		return female;
	}

	public void attack()
	{
		// TODO: insert code here
	}
}