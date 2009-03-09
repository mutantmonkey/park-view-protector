import java.io.Serializable;
import java.util.ArrayList;

public class ItemBin implements Serializable
{
	private static final long serialVersionUID	= 1L;
	
	public ArrayList<Item> items;
	public int[] inv;
	public Character holder;
	
	public ItemBin(Character c)
	{
		holder = c;
		inv = new int[2];
	}
	
	public void add(Item item)
	{
		if(item.getType() == 'h')
		{
			inv[0]++;
		}
		else if(item.getType() == 't')
		{
			inv[1]++;
		}
		item.setUser(holder);
		items.add(item);
	}
	
	public void useItem(char type)
	{
		for(int i = 0;i < items.size();i++)
		{
			if(items.get(i).getType() == type)
			{
				useItem(items.get(i).getType());
			}
		}
	}
	
	public void useItem(Item item)
	{
		if(item.getType() == 'h')
		{
			if(inv[0] > 0)
			{
				inv[0]--;
				item.run();
			}
		}
		else if(item.getType() == 't')
		{
			if(inv[1] > 0)
			{
				inv[1]--;
				item.run();
			}
		}
		items.remove(item);
	}
	
	public void dropItem(Item item)
	{
		if(item.getType() == 'h')
		{
			if(inv[0] > 0)
			{
				inv[0]--;
			}
		}
		else if(item.getType() == 't')
		{
			if(inv[1] > 0)
			{
				inv[1]--;
			}
		}
		items.remove(item);
		item.resetUser();
	}
	
	public void dropInv()
	{
		for(int i = 0;i < items.size();i++)
		{
			dropItem(items.get(i));
		}
	}
}
