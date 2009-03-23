/**
 * Game title screen
 * 
 * @author	James Schwinabart
 */

package org.javateerz.ParkViewProtector;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

public class TitleScreen extends Menu
{
	public static final int RIGHT_SPACING	= 20;
	
	public static final Color SELECTED_TEXT_COLOR	= new Color(255, 255, 255);
	public static final Color TEXT_COLOR			= new Color(255, 150, 255);
	
	private MenuItem[] items			= {
			new MenuItem("New Game", 1),
			new MenuItem("Load Game", 2),
		};
	
	private Sprite mainLogo;
	
	/**
	 * Constructor
	 * 
	 * @param p Driver class
	 */
	public TitleScreen(ParkViewProtector p)
	{
		super(p);
		
		mainLogo					= DataStore.INSTANCE.getSprite("logo.png");
	}
	
	public void show()
	{
		// handle key presses
		if(Keyboard.isKeyDown(KeyboardConfig.NAV_UP) && selectedItem > 0)
		{
			selectedItem--;
		}
		else if(Keyboard.isKeyDown(KeyboardConfig.NAV_DOWN) && selectedItem < items.length - 1)
		{
			selectedItem++;
		}
		else if(Keyboard.isKeyDown(KeyboardConfig.ENTER))
		{
			execute(items[selectedItem].getAction());
		}
		else if(Keyboard.isKeyDown(KeyboardConfig.BACK))
		{
			ParkViewProtector.showMenu		= false;
		}
		
		// draw logo
		mainLogo.draw(0, 0);
		
		// FIXME: remove this notice about the new keys
		/*g.setColor(SELECTED_TEXT_COLOR);
		g.drawString("NOTICE: Keys have changed. Use w, a, s, and d to move; j, k, and l to attack", 50, 50);*/
		
		// draw menu items
		for(int i = 0; i < items.length; i++)
		{
			items[i].setFont(TEXT_FONT);
		
			// set text color
			if(i == selectedItem)
			{
				items[i].setColor(SELECTED_TEXT_COLOR);
			}
			else {
				items[i].setColor(TEXT_COLOR);
			}
			
			items[i].draw(ParkViewProtector.WIDTH - items[i].getBounds().getWidth() - RIGHT_SPACING,
					TOP_SPACING + (i + 1) * LINE_SPACING);
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
				ParkViewProtector.showTitle		= false;
				break;
			
			case 2:
				Game gameData					= DataSaver.load();
				
				if(gameData != null)
				{
					driver.setGame(gameData);

					ParkViewProtector.showTitle	= false;
				}
				
				break;
		}
	}
}
