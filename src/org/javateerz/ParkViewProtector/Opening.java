package org.javateerz.ParkViewProtector;

public class Opening extends GameScreen
{
	private Sprite jtzLogo;
	
	public Opening()
	{
		jtzLogo						= DataStore.INSTANCE.getSprite("javateerslogo.png");
		
		// try to play "Clock Home Start" startup clip
		try
		{
			ParkViewProtector.playSound("clockhomestart.wav");
		}
		catch(Exception e)
		{
			System.out.println("No clock home start!");
		}
	}
	
	public void show()
	{
		jtzLogo.draw(ParkViewProtector.WIDTH / 2 - jtzLogo.getWidth() / 2,
				ParkViewProtector.HEIGHT / 2 - jtzLogo.getHeight() / 2);
		
		throttleSpeed();
	}
}
