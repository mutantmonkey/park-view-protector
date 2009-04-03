package org.javateerz.ParkViewProtector;

import org.newdawn.slick.Music;

public abstract class GameScreen 
{
	protected transient ParkViewProtector driver;
	protected transient Music bgMusic;
	
	public abstract void show();
	
	public Music getMusic()
	{
		return bgMusic;
	}
}
