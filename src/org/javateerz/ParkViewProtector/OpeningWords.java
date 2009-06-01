package org.javateerz.ParkViewProtector;

import org.lwjgl.input.Keyboard;

public class OpeningWords extends GameScreen
{
	public boolean running;
	private int currTime;
	
	private Sprite words;
	private int wordsY;
	
	private int numImages;
	private	Sprite[] images;
	private int[] imageStarts;
	private int[] imageDurations;
	private boolean[] imagesToDraw;
	
	private Sprite bg;
	
	public OpeningWords()
	{
		super();
	}
	
	public Sprite getBg()
	{
		return bg;
	}
	
	public void init()
	{
		running = true;
		words = DataStore.INSTANCE.getSprite("Opening_text.png");
		wordsY = ParkViewProtector.HEIGHT;
		numImages = 0;
		images = new Sprite[numImages];
		imageStarts = new int[numImages];
		imageDurations = new int[numImages];
		imagesToDraw = new boolean[numImages];
		for(int i = 0;i < imagesToDraw.length;i++)
		{
			imagesToDraw[i] = false;
		}
		currTime = 0;
		bg = DataStore.INSTANCE.getSprite("bg/stark_head.png");
	}
	
	public void step()
	{
		currTime++;
		if(currTime % 5 == 0 || Keyboard.isKeyDown(KeyboardConfig.ENTER))
		{
			wordsY-=2;
		}
		if(wordsY <= -1*words.getHeight() || Keyboard.isKeyDown(Keyboard.KEY_DELETE))
		{
			running = false;
		}
		for(int i = 0;i < imageStarts.length;i++)
		{
			if(imageStarts[i] == currTime)
			{
				imagesToDraw[i] = true;
			}
		}
	}
	
	public void draw()
	{
		bg.draw(0,0);
		words.draw(0,wordsY);
	}

}
