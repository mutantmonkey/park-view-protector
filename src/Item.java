import java.awt.Graphics;

/**
 * Behold...
 * 
 * ...an Item...
 * 
 * @author:	Javateerz...
 */

public class Item
{
	private char type; //'h' is for health restore, 't' is for teacher point restore
	private Character user; //if it open on the field, the user is null
	private Staff staff; // if it belongs to a staff member
	
	protected Sprite sprite; //graphic usage coming soon
	
	private int x;
	private int y;
	
	private final int HP_AMT = -50; //data store
	private final int TP_AMT = 10; //reference pointer pointers
									//see advanced Starkiometry
	
	public Item(char type)
	{
		this.type = type;
		this.sprite	= DataStore.INSTANCE.getSprite("images/gItem.png");
	}
	
	/**
	 * 
	 * @param type
	 * @param c
	 */
	public Item(char type,Character c)
	{
		this.type = type;
		user = c;
		if(c instanceof Staff)
		{
			staff = (Staff)c;
		}
		this.sprite	= DataStore.INSTANCE.getSprite("images/gItem.png");
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
			if(staff != null)
			{
				staff.adjustTp(TP_AMT);
			}
		}
	}
	
	public void draw(Graphics g)//draws it...?
	{
		sprite.draw(g, x, y);
	}
	
	public void place(int x, int y)//places it...?
	{
		this.x = x;
		this.y = y;
	}
}