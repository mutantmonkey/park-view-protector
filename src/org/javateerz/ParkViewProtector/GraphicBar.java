package org.javateerz.ParkViewProtector;

public class GraphicBar
{
	private String name;
	private transient Sprite m1, m2, m3;
	private transient Sprite b1, b2, b3;
	private int width;
	private int maxWidth;
	
	
	public GraphicBar(Game g, String name, int width, int maxWidth)
	{
		this.name		= name;
		this.width		= width;
		this.maxWidth	= maxWidth;
		updateSprite();
	}
	
	public GraphicBar(Game g, String name, int maxWidth, double percent)
	{
		this.name		= name;
		this.width		= (int) (percent * maxWidth);
		this.maxWidth	= maxWidth;
		updateSprite();
	}
	
	/**
	 * Adjust the percentage filled
	 * 
	 * @param width
	 */
	public void setFilled(double percent)
	{
		this.width				= (int) (percent * maxWidth);
	}
	
	public void setWidth(int amount)
	{
		this.maxWidth			= amount;
	}
	
	public void setName(String name)
	{
		this.name				= name;
		updateSprite();
	}
	
	public void updateSprite()
	{
		this.m1		= DataStore.INSTANCE.getSprite("bar/" + name + "_1.png");
		this.m2		= DataStore.INSTANCE.getSprite("bar/" + name + "_2.png");
		this.m3		= DataStore.INSTANCE.getSprite("bar/" + name + "_3.png");
		this.b1		= DataStore.INSTANCE.getSprite("bar/" + name + "_b1.png");
		this.b2		= DataStore.INSTANCE.getSprite("bar/" + name + "_b2.png");
		this.b3		= DataStore.INSTANCE.getSprite("bar/" + name + "_b3.png");
	}
	
	/**
	 * Draw the bar at the given position
	 * 
	 * @param g Graphics context
	 * @param x X-position
	 * @param y Y-position
	 */
	// FIXME: Lags when there's too many things on the screen
	public void draw(int x, int y)
	{
		// background
		b1.draw(x,y);
		for(int i=0; i<maxWidth; i++)
		{
			b2.draw(x+b1.getWidth()+i,y);
		}
		b3.draw(x+maxWidth+b1.getWidth(),y);
		
		// main bar
		m1.draw(x,y);
		for(int i=0; i<width; i++)
		{
			m2.draw(x+m1.getWidth()+i,y);
		}
		m3.draw(x+width+b1.getWidth(),y);
	}
}
