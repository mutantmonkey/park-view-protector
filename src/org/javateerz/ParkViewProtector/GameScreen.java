package org.javateerz.ParkViewProtector;

import org.newdawn.slick.Music;

public abstract class GameScreen 
{
	protected transient ParkViewProtector driver;
	protected transient Music bgMusic;
	
	protected transient float musicPosition			= 0f;
	
	public abstract void show();
	
	protected void setMusic(String file)
	{
		try
		{
			bgMusic									= new Music(file);
			bgMusic.setVolume(Options.INSTANCE.getFloat("music_volume", 0.8f));
		}
		catch(Exception e)
		{
			System.out.println("Error playing background music");
		}
	}
	
	protected void ensureMusicPlaying()
	{
		if(!bgMusic.playing())
		{
			// adjust volume
			bgMusic.setVolume(Options.INSTANCE.getFloat("music_volume", 0.8f));
			
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
