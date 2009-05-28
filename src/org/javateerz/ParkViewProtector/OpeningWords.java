package org.javateerz.ParkViewProtector;

public class OpeningWords extends GameScreen
{
	private Sprite words;
	private int wordsY;
	private int numImages;
	private	Sprite[] images;
	private int[] imageStarts;
	private int[] imageDurations;
	private boolean[] imagesToDraw;
	private int currTime;
	public boolean running;
	
	public OpeningWords()
	{
		super();
	}
	
	public String getBG()
	{
		return "terrazzo_green.png";
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
	}
	
	
	public void step()
	{
		currTime++;
		wordsY--;
		if(wordsY <= -1*words.getHeight())
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
		words.draw(0,wordsY);
	}

}
