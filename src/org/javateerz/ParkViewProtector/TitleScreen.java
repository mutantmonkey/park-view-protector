/**
 * Game title screen
 * 
 * @author	James Schwinabart
 */

package org.javateerz.ParkViewProtector;

import java.awt.*;

public class TitleScreen extends Menu
{
	public static final int RIGHT_SPACING	= 20;
	
	public static final Color SELECTED_TEXT_COLOR	= Color.white;
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
		if(Keyboard.isPressed(Keyboard.NAV_UP) && selectedItem > 0)
		{
			selectedItem--;
		}
		else if(Keyboard.isPressed(Keyboard.NAV_DOWN) && selectedItem < items.length - 1)
		{
			selectedItem++;
		}
		else if(Keyboard.isPressed(Keyboard.ENTER))
		{
			execute(items[selectedItem].getAction());
			
			// FIXME: should this actually be here?
			Keyboard.setReleased(Keyboard.ENTER);
		}
		else if(Keyboard.isPressed(Keyboard.BACK))
		{
			ParkViewProtector.showMenu		= false;
			
			// FIXME: see above
			Keyboard.setReleased(Keyboard.BACK);
		}
		
		g							= (Graphics) strategy.getDrawGraphics();
		
		// draw logo
		mainLogo.draw(0, 0);
		
		// FIXME: remove this notice about the new keys
		g.setColor(SELECTED_TEXT_COLOR);
		g.drawString("NOTICE: Keys have changed. Use w, a, s, and d to move; j, k, and l to attack", 50, 50);
		
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
			
			items[i].draw(g, ParkViewProtector.WIDTH - items[i].getBounds(g).width - RIGHT_SPACING,
					TOP_SPACING + (i + 1) * LINE_SPACING);
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
