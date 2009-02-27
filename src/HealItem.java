public class HealItem extends UsableItem
{
	private int hpInc;
	private int tpInc;
	
	public HealItem(String name,String descrip,int hp,int tp)
	{
		super(name,descrip);
		this.sprite	= DataStore.INSTANCE.getSprite("images/gItem.png");
		hpInc = hp;
		tpInc = tp;
	}
	
	public void run(Character c)
	{
		c.adjustHp(hpInc);
		if(c instanceof Staff)
		{
			c.adjustHp(tpInc);
		}
	}
}