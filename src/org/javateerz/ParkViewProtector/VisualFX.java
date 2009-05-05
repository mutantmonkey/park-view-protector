package org.javateerz.ParkViewProtector;

import java.io.*;

public class VisualFX extends Movable implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	int time, tick=0;
	
	public VisualFX(double x, double y, double speed, String name, int time)
	{
		super(x, y, speed);
		this.name=name;
		this.time=time;
		updateSprite();
	}
	
	protected void updateSprite()
	{
		this.sprite		= DataStore.INSTANCE.getSprite("attack/blip.png");
	}
	
	public boolean tick()
	{
		tick++;
		if(tick>=time)
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
