import java.awt.Graphics;

/*
 * Behold...
 * 
 * ...an Item...
 * 
 * ...@author:	Javateerz...
 */

public abstract class Item
{
	protected String name;
	
	protected String descrip;
	
	protected Sprite sprite; //graphic usage coming soon
	
	protected int x;
	protected int y;
	
	public Item(String name,String descrip)
	{
		this.name = name;
		this.descrip = descrip;
		this.sprite	= DataStore.INSTANCE.getSprite("images/gItem.png");
	}
	
	public String getName()//gets name
	{
		return name;
	}
	
	public String getDes()//gets description
	{
		return descrip;
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
	
	public abstract void run(Character c);//runs it...?
}