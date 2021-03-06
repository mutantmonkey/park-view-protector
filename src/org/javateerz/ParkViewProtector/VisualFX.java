package org.javateerz.ParkViewProtector;

import java.io.*;

public class VisualFX extends Movable implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected double time=0;
	
	public VisualFX(Game g, String name, double time, double x, double y)
	{
		super(g, x, y, 0);
		this.name=name;
		this.time=time;
		updateSprite();
	}
	
	public VisualFX(Game g, String name, double time)
	{
		super(g, 0, 0, 0);
		this.name=name;
		this.time=time;
		updateSprite();
	}
	
	protected void updateSprite()
	{
		this.sprite		= DataStore.INSTANCE.getSprite("fx/"+name+".png");
	}
	
	public void setTime(double amount)
	{
		time=amount;
	}
	
	public boolean tick()
	{
		time -= ParkViewProtector.framesToSecs(1);
		
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
