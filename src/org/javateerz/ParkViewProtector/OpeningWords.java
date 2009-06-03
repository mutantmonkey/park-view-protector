package org.javateerz.ParkViewProtector;

import org.lwjgl.input.Keyboard;

public class OpeningWords extends GameScreen
{
	public boolean running;
	
	private Sprite words;
	private double wordsY;
	private double speed;
	
	private Sprite bg;
	
	public OpeningWords()
	{
		super();
		running = true;
		
		words = DataStore.INSTANCE.getSprite("Opening_text.png");
		wordsY = ParkViewProtector.HEIGHT;
		speed = .20;
		
		bg = DataStore.INSTANCE.getSprite("bg/stark_head.png");
	}
	
	public Sprite getBg()
	{
		return bg;
	}
	
	public void step()
	{
		if(Keyboard.isKeyDown(KeyboardConfig.ENTER))
		{
			speed = 1;
		}
		else
		{
			speed = .20;
		}
		wordsY-=speed;
		if(wordsY <= -1*words.getHeight() || Keyboard.isKeyDown(Keyboard.KEY_BACK) || Keyboard.isKeyDown(Keyboard.KEY_DELETE))
		{
			running = false;
		}
	}
	
	public void draw()
	{
		bg.draw(0,0);
		words.draw(0,(int)wordsY);
	}
}
