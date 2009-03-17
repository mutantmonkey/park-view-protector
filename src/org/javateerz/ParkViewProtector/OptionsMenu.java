/**
 * Options menu
 * 
 * @author	James Schwinabart
 */

package org.javateerz.ParkViewProtector;

import java.awt.*;

public class OptionsMenu extends Menu
{
	public static final int LEFT_SPACING	= 60;
	public static final int RIGHT_SPACING	= 60;
	
	private OptionItem[] items;
	
	private Sprite optionsBg;
	
	/**
	 * Constructor
	 * 
	 * @param p Driver class
	 */
	public OptionsMenu(ParkViewProtector p)
	{
		super(p);
		
		items		= new OptionItem[3];
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
	
	public void show()
	{
		// handle key presses
		if(Keyboard.isPressed(Keyboard.NAV_UP) && selectedItem > 0)
		{
			selectedItem--;
		}
		else if(Keyboard.isPressed(Keyboard.NAV_DOWN) && selectedItem < items.length - 1)
		{
			selectedItem++;
		}
		else if(Keyboard.isPressed(Keyboard.BACK))
		{
			ParkViewProtector.showMenu		= false;
			
			// FIXME: see above
			Keyboard.setReleased(Keyboard.BACK);
		}
		
		// menu items are different
		if(items[selectedItem] instanceof MenuItem)
		{
			if(Keyboard.isPressed(Keyboard.ENTER))
			{
				execute(((MenuItem) items[selectedItem]).getAction());
				
				// FIXME: see above
				Keyboard.setReleased(Keyboard.ENTER);
			}
		}
		else {
			if(Keyboard.isPressed(Keyboard.NAV_LEFT))
			{
				items[selectedItem].leftPressed();
				items[selectedItem].update(driver);
			}
			else if(Keyboard.isPressed(Keyboard.NAV_RIGHT))
			{
				items[selectedItem].rightPressed();
				items[selectedItem].update(driver);
			}
		}
		
		g									= (Graphics) strategy.getDrawGraphics();
		
		// draw the background
		optionsBg.draw(g, 0, 0);
		
		// set font and color
		g.setFont(TEXT_FONT);
		
		// draw menu items
		for(int i = 0; i < items.length; i++)
		{
			// set text color
			if(i == selectedItem)
			{
				g.setColor(SELECTED_TEXT_COLOR);
			}
			else {
				g.setColor(TEXT_COLOR);
			}
			
			items[i].draw(g, LEFT_SPACING, TOP_SPACING + (i + 1) * LINE_SPACING);
		}
		
		// finish drawing
		g.dispose();
		strategy.show();
		
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
