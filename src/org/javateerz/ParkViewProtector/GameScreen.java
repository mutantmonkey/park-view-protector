package org.javateerz.ParkViewProtector;

import org.newdawn.slick.Music;

public abstract class GameScreen 
{
	protected transient ParkViewProtector driver;
	protected transient Music bgMusic;
	
	protected transient float musicPosition			= 0f;
	
	public abstract void show();
	
	protected void ensureMusicPlaying()
	{
		if(!bgMusic.playing())
		{
			bgMusic.loop();
			
			if(musicPosition > 0)
			{
				bgMusic.setPosition(musicPosition);
			}
		}
		
		musicPosition								= bgMusic.getPosition();
	}
	
	public Music getMusic()
	{
		return bgMusic;
	}
}
