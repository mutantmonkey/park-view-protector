package org.javateerz.ParkViewProtector;

public class StatusEffect extends VisualFX
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Character c;

	public StatusEffect(Game g, String name, Character c, double time)
	{
		super(g, name, time);
		this.c=c;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void updateSprite()
	{
		sprite = DataStore.INSTANCE.getSprite("fx/"+name+".png");
	}
	
	public void draw()
	{
		sprite.draw(c.getBounds().getCenterX()-sprite.getWidth()/2,
				c.getBounds().getCenterY()-sprite.getHeight()/2);
	}
}
