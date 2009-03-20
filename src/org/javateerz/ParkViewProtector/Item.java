/**
 * Behold...
 * 
 * ...an Item...
 * 
 * @author:	Javateerz...
 */

package org.javateerz.ParkViewProtector;

public class Item extends Movable
{
	private char type; //'h' is for health restore, 't' is for teacher point restore
	private Character user; //if it open on the field, the user is null
	
	private final int HP_AMT = -50; //data store
	private final int TP_AMT = 10; //reference pointer pointers
									//see advanced Starkiometry
	
	private static final long serialVersionUID = 1L;
	
	public Item(char type,int x,int y)
	{
		super(x,y,0);
		this.type = type;
		updateSprite();
	}
	
	/**
	 * 
	 * @param type
	 * @param c
	 */
	public Item(char type,Character c)
	{
		super(0,0,0);
		this.type = type;
		user = c;
		updateSprite();
	}
	
	public char getType()//gets name
	{
		return type;
	}
	
	public Character getUser()
	{
		return user;
	}
	
	public void setUser(Character c)
	{
		user = c;
	}
	
	public void resetUser()
	{
		x = user.x;
		y = user.y;
		user = null;
	}
	
	public void run()
	{
		if(type == 'h')
		{
			user.adjustHp(HP_AMT);
		}
		else if(type == 't')
		{
			if(user instanceof Staff)
			{
				Staff s = (Staff)user;
				s.adjustTp(TP_AMT);
			}
		}
	}
	
	public String toString()
	{
		return "" + type + " x: " + x + " y: " + y;
	}
	
	public void updateSprite()
	{
		if(type == 'h')
		{
			this.sprite	= DataStore.INSTANCE.getSprite("gHpPot.png");
		}
		else
		{
			this.sprite	= DataStore.INSTANCE.getSprite("gTpPot.png");
		}
	}
}