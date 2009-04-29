package org.javateerz.ParkViewProtector;

import org.newdawn.slick.Input;
import org.newdawn.slick.Music;

public abstract class GameScreen extends Input
{
	protected transient ParkViewProtector driver;
	protected transient Music bgMusic;
	
	protected transient float musicPosition			= 0f;
	
	public GameScreen()
	{
		super(ParkViewProtector.HEIGHT);
	}
	
	public abstract void show();
	
	protected void setMusic(String file)
	{
		try
		{
			bgMusic									= new Music(file);
			
			// set volume
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
			
			// make it loop!
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
	
	public void poll()
	{
		poll(ParkViewProtector.WIDTH, ParkViewProtector.HEIGHT);
	}
	
	public void keyPressed(int key, char c)
	{
	}
	
	public void keyReleased(int key, char c)
	{
	}
	
	public void setInput(Input input)
	{
	}
	
	public void inputEnded()
	{
	}
	
	public boolean isAcceptingInput()
	{
		return false;
	}
}
