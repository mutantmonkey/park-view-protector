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
	
	public VisualFX(Game g, String name, int time, double x, double y)
	{
		super(g, x, y, 0);
		this.name=name;
		this.time=time;
		updateSprite();
	}
	
	protected void updateSprite()
	{
		this.sprite		= DataStore.INSTANCE.getSprite("FX/"+name+".png");
	}
	
	public boolean tick()
	{
		tick++;
		if(time==0 || tick>=time)
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
