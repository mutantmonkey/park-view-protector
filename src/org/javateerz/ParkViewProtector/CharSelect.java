package org.javateerz.ParkViewProtector;

import org.javateerz.EasyGL.GLString;
import org.javateerz.ParkViewProtector.Staff.*;
import org.newdawn.slick.Font;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.TrueTypeFont;

public class CharSelect extends GameScreen implements KeyListener
{
	public static final int CHAR_X		= 150;
	public static final int CHAR_Y		= 200;
	
	public static final int NAME_PAD_X	= 20;
	
	private Sprite bgImage;
	private Font nameFont;
	
	private Staff[] choices;
	private int selectedIndex			= 0;
	
	public CharSelect(ParkViewProtector p)
	{
		driver							= p;
		
		bgImage							= DataStore.INSTANCE.getSprite("char_select.png");
		
		nameFont						= new TrueTypeFont(new java.awt.Font(
				"System", java.awt.Font.PLAIN, 32), true);
		
		choices							= new Staff[3];
		choices[0]						= new Stark(driver.getGame(), CHAR_X, CHAR_Y);
		choices[1]						= new SpecialCharacter(driver.getGame(), CHAR_X,
				CHAR_Y);
		choices[2]						= new Jamie(driver.getGame(), CHAR_X, CHAR_Y);
	}
	
	public void keyPressed(int key, char c)
	{
		switch(key)
		{
			case KeyboardConfig.NAV_LEFT:
				if(selectedIndex > 0)
				{
					selectedIndex--;
				}
				else {
					//selectedIndex = choices.length - 1;
				}
				
				break;
				
			case KeyboardConfig.NAV_RIGHT:
				if(selectedIndex < choices.length - 1)
				{
					selectedIndex++;
				}
				else {
					//selectedIndex = 0;
				}
				
				break;
				
			case KeyboardConfig.ENTER:
				finish();
				break;
		}
		
		clearKeyPressedRecord();
	}
	
	public boolean isAcceptingInput()
	{
		return true;
	}
	
	public void step()
	{
		addKeyListener(this);
		poll();
		
		throttleSpeed();
	}
	
	public void draw()
	{
		bgImage.draw();
		
		Staff selectedChar				= choices[selectedIndex];
		
		selectedChar.draw();
		
		int x							= (int) (selectedChar.getBounds().getX() +
			selectedChar.getBounds().getWidth() + NAME_PAD_X);
		
		int y							= (int) (selectedChar.getBounds().getY() +
			(selectedChar.getBounds().getHeight() / 4));
		
		GLString name					= new GLString(selectedChar.getName(), x, y);
		name.setFont(nameFont);
		name.setColor(ParkViewProtector.COLOR_TEXT_1);
		name.draw();
	}
	
	public void finish()
	{
		ParkViewProtector.selectChar	= false;
		driver.getGame().setPlayer(choices[selectedIndex]);
		
		removeAllListeners();
	}
}
