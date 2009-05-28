package org.javateerz.ParkViewProtector;

import java.io.*;

public class VisualFX extends Movable implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	protected String name;
	int time=0;
	
	public VisualFX(Game g, String name, int time, double x, double y)
	{
		super(g, x, y, 0);
		this.name=name;
		setTime(time);
		updateSprite();
	}
	
	public VisualFX(Game g, String name, int time)
	{
		super(g, 0, 0, 0);
		this.name=name;
		setTime(time);
		updateSprite();
	}
	
	protected void updateSprite()
	{
		this.sprite		= DataStore.INSTANCE.getSprite("fx/"+name+".png");
	}
	
	public void setTime(int amount)
	{
		time=ParkViewProtector.secsToFrames(amount);
	}
	
	public boolean tick()
	{
		time--;
		if(time<=0)
			return true;
		return false;
	}
	
	private void readObject(ObjectInputStream os) throws ClassNotFoundException, IOException
	{
		os.defaultReadObject();
		
		validateState();
	}
	
	private void writeObject(ObjectOutputStream os) throws IOException
	{
		os.defaultWriteObject();
	}
}
