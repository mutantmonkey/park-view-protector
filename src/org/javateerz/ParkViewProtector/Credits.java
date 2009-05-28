package org.javateerz.ParkViewProtector;

import org.javateerz.EasyGL.GLRect;
import org.javateerz.EasyGL.GLString;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.TrueTypeFont;

public class Credits extends GameScreen implements KeyListener
{
	public static final int SCREEN_TIME		= 5;
	public static final int SECTION_Y		= 200;
	public static final int NAME_Y			= 300;
	
	public static final Color BG_COLOR		= new Color(0, 0, 0);
	public static final Color SECTION_COLOR	= new Color(255, 0, 255);
	public static final Color NAME_COLOR	= new Color(255, 255, 255);
	
	private final static String[] sections	= {"Developers", "Project Manager",
		"Graphics", "Music", "Beta Testing", "Special Thanks", "Park View Protector"};
	private final static String[][] names	= {
			{"Jason Khamphila", "James Schwinabart", "Donnelly Phillips"},
			{"James Schwinabart"},
			{"Jason Khamphila", "Donnelly Phillips", "James Schwinabart", "Amanda Wikle"},
			{"Paul Rosenthal"},
			{"Label Sak"},
			{"Mr. Brian Withnell", "Mr. David Stark", "Michael Schwinabart"},
			{"Thanks for playing!"},
	};
	
	private int activeSection				= 0;
	private int activeName					= -1;
	private float startTime					= 0;
	
	private Font sectionFont				= new TrueTypeFont(new java.awt.Font("System",
			java.awt.Font.BOLD, 24), true);
	private Font nameFont					= new TrueTypeFont(new java.awt.Font("System",
			java.awt.Font.PLAIN, 24), true);
	
	private GLRect background				= new GLRect(0, 0, ParkViewProtector.WIDTH,
			ParkViewProtector.HEIGHT);
	private GLString sectionText;
	private GLString nameText;
	
	public Credits(ParkViewProtector p)
	{
		setMusic("bloated.ogg");
	}
	
	public void keyPressed(int key, char c)
	{
		switch(key)
		{
			case Keyboard.KEY_ESCAPE:
				driver.quitGame();
				break;
		}
	}
	
	public boolean isAcceptingInput()
	{
		return true;
	}
	
	public void step()
	{
		addKeyListener(this);
		poll();
		
		background.setColor(BG_COLOR);
		
		if(startTime <= 0 || ParkViewProtector.timer.getTime() > startTime + SCREEN_TIME)
		{
			if(activeName < names[activeSection].length - 1)
			{
				activeName++;
			}
			else if(activeSection < sections.length - 1)
			{
				activeSection++;
				activeName				= 0;
			}
			
			startTime					= ParkViewProtector.timer.getTime();
			
			sectionText					= new GLString(sections[activeSection]);
			sectionText.setFont(sectionFont);
			sectionText.setColor(SECTION_COLOR);
			
			nameText					= new GLString(names[activeSection][activeName]);
			nameText.setFont(nameFont);
			nameText.setColor(NAME_COLOR);
		}
	}
	
	public void draw()
	{
		// ensure music is playing
		ensureMusicPlaying();
		
		background.draw();
		
		sectionText.drawCentered(ParkViewProtector.WIDTH / 2, SECTION_Y);
		nameText.drawCentered(ParkViewProtector.WIDTH / 2, NAME_Y);
	}
}
