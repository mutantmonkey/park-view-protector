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
	
	public Item(Game g, char type, int x, int y)
	{
		super(g, x, y, 0);
		this.type = type;
		updateSprite();
	}
	
	/**
	 * 
	 * @param type
	 * @param c
	 */
	public Item(Game g, char type, Character c)
	{
		super(g, 0, 0, 0);
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
		switch(type)
		{
			case 'h':
				this.sprite	= DataStore.INSTANCE.getSprite("item_hp.png");
				break;
				
			default:
				this.sprite	= DataStore.INSTANCE.getSprite("item_tp.png");
			break;
		}
	}
}