package org.javateerz.ParkViewProtector;

import java.io.Serializable;
import java.util.ArrayList;

import org.javateerz.EasyGL.GLString;

public class ItemBin implements Serializable
{
	private static final long serialVersionUID	= 1L;
	
	public ArrayList<Item> items = new ArrayList<Item>();
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
		System.out.println("Item - get!");
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
		if(items.size() > 0)
		{
			dropItem(items.get(0));
		}
	}
	
	public void draw(int x,int y)
	{
		GLString stats					= new GLString("HP: " + inv[0] + " TP: " + inv[1],x,y);
		stats.setColor(ParkViewProtector.STATS_BAR_FG);
		stats.draw();
	}
}
