package org.javateerz.ParkViewProtector;

import org.javateerz.EasyGL.GLString;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Font;
import org.newdawn.slick.TrueTypeFont;

public class CharSelect extends GameScreen
{
	public static final int CHAR_X		= 50;
	public static final int CHAR_Y		= 50;
	
	public static final int NAME_X		= CHAR_X + 100;
	public static final int NAME_Y		= CHAR_Y;
	
	private Font nameFont;
	
	private Staff[] choices;
	private int selectedIndex			= 0;
	
	public CharSelect(ParkViewProtector p)
	{
		driver							= p;
		
		nameFont						= new TrueTypeFont(new java.awt.Font(
				"System", java.awt.Font.PLAIN, 32), true);
		
		// FIXME: add default constructors (we just need to display the sprite!)
		choices							= new Staff[2];
		choices[0]						= new Stark(CHAR_X, CHAR_Y, 0, 0);
		choices[1]						= new SpecialCharacter(CHAR_X, CHAR_Y, 0, 0);
	}
	
	public void show()
	{
		// handle key presses
		if(Keyboard.isKeyDown(KeyboardConfig.NAV_LEFT) && selectedIndex > 0)
		{
			selectedIndex--;
		}
		else if(Keyboard.isKeyDown(KeyboardConfig.NAV_RIGHT) && selectedIndex < choices.length - 1)
		{
			selectedIndex++;
		}
		else if(Keyboard.isKeyDown(KeyboardConfig.ENTER))
		{
			finish();
		}
		
		Staff selectedChar				= choices[selectedIndex];
		
		selectedChar.draw();
		
		GLString name					= new GLString(selectedChar.getName(), NAME_X,
				NAME_Y);
		name.setFont(nameFont);
		name.setColor(ParkViewProtector.COLOR_TEXT_2);
		name.draw();
	}
	
	public void finish()
	{
		// FIXME: there should be a better way of doing this
		ParkViewProtector.selectChar	= false;
		driver.getGame().setPlayer(choices[selectedIndex]);
	}
}
