/*
 * Behold...
 * 
 * ...an Item...
 * 
 * ...@author:	Javateerz...
 */

public abstract class Item
{
	private String name;
	
	private String descrip;
	
	private Sprite sprite; //graphic usage coming soon
	
	public Item(String name,String descrip)
	{
		this.name = name;
		this.descrip = descrip;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDes()
	{
		return descrip;
	}
	
	public abstract void run();
}