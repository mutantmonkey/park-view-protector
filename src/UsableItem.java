public class UsableItem extends Item
{
	public UsableItem(String name,String descrip)
	{
		super(name,descrip);
		this.sprite	= DataStore.INSTANCE.getSprite("images/gItem.png");
	}
	
	public void run()
	{
		
	}
}