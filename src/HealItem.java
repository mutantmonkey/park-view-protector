public class HealItem extends UsableItem
{
	public HealItem(String name,String descrip)
	{
		super(name,descrip);
		this.sprite	= DataStore.INSTANCE.getSprite("images/gItem.png");
	}
	
	public void run(Character c)
	{
		if(contains("HP+="))
		{
			int increase = Integer.parseInt((descrip.substring(idxOf("HP+=") + 4)));
			c.adjustHp(increase*-1);
		}
	}
	
	public boolean contains(String check)
	{
		for(int i = 0;i < descrip.length()-check.length();i++)
		{
			if(descrip.substring(i,i+check.length()).equals(check))
			{
				return true;
			}
		}
		return false;
	}
	
	public int idxOf(String check)
	{
		for(int i = 0;i < descrip.length()-check.length();i++)
		{
			if(descrip.substring(i,i+check.length()).equals(check))
			{
				return i;
			}
		}
		return -1;
	}
}