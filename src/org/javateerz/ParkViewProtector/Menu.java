/**
 * Game menu
 * 
 * @author	James Schwinabart
 */

package org.javateerz.ParkViewProtector;

import org.javateerz.EasyGL.*;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.TrueTypeFont;

public class Menu
{
	public static final int TOP_SPACING				= 120;
	public static final int LINE_SPACING			= 40;
	public static final Color BG_COLOR				= new Color(0, 0, 0);
	public static final Color TEXT_COLOR			= new Color(255, 255, 255);
	public static final Color SELECTED_TEXT_COLOR	= new Color(255, 0, 255);
	
	protected ParkViewProtector driver;
	protected static Font textFont;
	
	private MenuItem[] items						= {
			new MenuItem("Back", 1),
			new MenuItem("Options", 2),
			new MenuItem("Save Game", 4),
			new MenuItem("Load Game", 5),
			new MenuItem("Quit Game", 9),
		};
	protected int selectedItem						= 0;
	
	/**
	 * Constructor
	 * 
	 * @param p Driver class
	 */
	public Menu(ParkViewProtector p)
	{
		this.driver								= p;
		
		textFont								= new TrueTypeFont(new java.awt.Font(
				"System", java.awt.Font.PLAIN, 32), true);
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
		
		// draw the background
		GLRect bg							= new GLRect(0, 0, ParkViewProtector.WIDTH,
				ParkViewProtector.HEIGHT);
		bg.setColor(BG_COLOR);
		bg.draw();
		
		// draw menu items
		for(int i = 0; i < items.length; i++)
		{
			items[i].setFont(textFont);
			
			// set text color
			if(i == selectedItem)
			{
				items[i].setColor(SELECTED_TEXT_COLOR);
			}
			else {
				items[i].setColor(TEXT_COLOR);
			}
			
			items[i].drawCentered(ParkViewProtector.WIDTH / 2, TOP_SPACING + (i + 1) * LINE_SPACING);
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
				ParkViewProtector.showMenu		= false;
				break;
			
			case 2:
				ParkViewProtector.showOptions	= true;
				break;
				
			case 4:
				DataSaver.save(driver.getGame());
				break;
			
			case 5:
				Game gameData					= DataSaver.load();
				
				if(gameData != null)
				{
					driver.setGame(gameData);

					ParkViewProtector.showTitle	= false;
				}
				
				ParkViewProtector.showMenu		= false;
				
				break;
				
			case 9:
				driver.quitGame();
				break;
		}
	}
}
