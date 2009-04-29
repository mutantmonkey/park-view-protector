/**
 * Options menu
 * 
 * @author	James Schwinabart
 */

package org.javateerz.ParkViewProtector.Menu;

import org.javateerz.ParkViewProtector.DataStore;
import org.javateerz.ParkViewProtector.FloatOption;
import org.javateerz.ParkViewProtector.KeyboardConfig;
import org.javateerz.ParkViewProtector.Options;
import org.javateerz.ParkViewProtector.ParkViewProtector;
import org.javateerz.ParkViewProtector.Sprite;
import org.lwjgl.input.Keyboard;

public class OptionsMenu extends Menu
{
	public static final int LEFT_SPACING	= 60;
	public static final int RIGHT_SPACING	= 60;
	
	private MenuBlock[] items;
	
	private Sprite optionsBg;
	
	/**
	 * Constructor
	 * 
	 * @param p Driver class
	 */
	public OptionsMenu(ParkViewProtector p)
	{
		super(p);
		
		items		= new MenuBlock[3];
		int i		= 0;
		
		items[i]	= new MenuItem("Back", 1);
		
		i++;
		items[i]	= new FloatOption("Music Volume"		, "music_volume",
				Options.INSTANCE.getFloat("music_volume", 0.8f));
		
		i++;
		items[i]	= new FloatOption("Effects Volume"	, "sfx_volume",
				Options.INSTANCE.getFloat("music_volume", 1.0f));
		
		optionsBg							= DataStore.INSTANCE.getSprite("options.png");
	}
	
	public void keyPressed(int key, char c)
	{
		switch(key)
		{
			case KeyboardConfig.NAV_UP:
				if(selectedItem > 0)
					selectedItem--;
				break;
		
			case KeyboardConfig.NAV_DOWN:
				if(selectedItem < items.length - 1)
					selectedItem++;
				break;
				
			case KeyboardConfig.BACK:
				ParkViewProtector.showOptions	= false;
				break;
		}
		
		clearKeyPressedRecord();
	}
	
	public void show()
	{
		// key events
		addKeyListener(this);
		poll();
		
		// menu items are different
		if(items[selectedItem] instanceof MenuItem)
		{
			if(Keyboard.isKeyDown(KeyboardConfig.ENTER))
			{
				execute(((MenuItem) items[selectedItem]).getAction());
			}
		}
		else if(items[selectedItem] instanceof OptionItem)
		{
			if(Keyboard.isKeyDown(KeyboardConfig.NAV_LEFT))
			{
				((OptionItem) items[selectedItem]).leftPressed();
				((OptionItem) items[selectedItem]).update(driver);
			}
			else if(Keyboard.isKeyDown(KeyboardConfig.NAV_RIGHT))
			{
				((OptionItem) items[selectedItem]).rightPressed();
				((OptionItem) items[selectedItem]).update(driver);
			}
		}
		
		// draw the background
		optionsBg.draw(0, 0);
		
		// draw menu items
		for(int i = 0; i < items.length; i++)
		{
			// set font
			items[i].setFont(textFont);
			
			// set text color
			if(i == selectedItem)
			{
				items[i].setColor(SELECTED_TEXT_COLOR);
			}
			else {
				items[i].setColor(TEXT_COLOR);
			}
			
			items[i].draw(LEFT_SPACING, TOP_SPACING + (i + 1) * LINE_SPACING);
		}
		
		// keep the game from running too fast
		try
		{
			Thread.sleep(100);
		}
		catch(Exception e) {}
	}
	
	/**
	 * Runs the action for the menu item (almost certainly not the best way to do this)
	 * 
	 * @param actionId
	 */
	private void execute(int actionId)
	{
		switch(actionId)
		{
			case 1:
				ParkViewProtector.showOptions	= false;
				break;
		}
	}
}
