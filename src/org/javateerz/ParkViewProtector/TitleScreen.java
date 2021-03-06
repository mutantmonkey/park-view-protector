/**
 * Game title screen
 * 
 * @author	James Schwinabart
 */

package org.javateerz.ParkViewProtector;

import org.javateerz.ParkViewProtector.Menu.Menu;
import org.javateerz.ParkViewProtector.Menu.MenuItem;
import org.newdawn.slick.Color;

public class TitleScreen extends Menu
{
	public static final int RIGHT_SPACING	= 20;
	
	public static final Color SELECTED_TEXT_COLOR	= new Color(255, 255, 255);
	public static final Color TEXT_COLOR			= new Color(255, 150, 255);
	
	private MenuItem[] items			= {
			new MenuItem("New Game", 1),
			new MenuItem("Load Game", 2),
			new MenuItem("Quit Game", 9)
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
				
			case KeyboardConfig.ENTER:
				execute(items[selectedItem].getAction());
				break;
		
			case KeyboardConfig.BACK:
				ParkViewProtector.showMenu	= false;
				break;
		}
		
		clearKeyPressedRecord();
	}
	
	public boolean isAcceptingInput()
	{
		return true;
	}
	
	public void draw()
	{
		// ensure music is playing
		ensureMusicPlaying();
		
		// draw logo
		mainLogo.draw(0, 0);
		
		// draw menu items
		for(int i = 0; i < items.length; i++)
		{
			items[i].setFont(textFont);
				
			// set text color
			if(i == selectedItem)
			{
				items[i].setColor(SELECTED_TEXT_COLOR);
				items[i].setSelected(true);
			}
			else {
				items[i].setColor(TEXT_COLOR);
				items[i].setSelected(false);
			}
			
			items[i].draw(ParkViewProtector.WIDTH - items[i].getBounds().getWidth() - RIGHT_SPACING,
					TOP_SPACING + (i + 1) * LINE_SPACING);
		}
	}
	
	/**
	 * Runs the action for the menu item (almost certainly not the best way to do this)
	 * 
	 * @param actionId
	 */
	protected void execute(int actionId)
	{
		switch(actionId)
		{
			case 1:
				ParkViewProtector.showTitle		= false;
				//bgMusic.stop();
				break;
			
			case 2:
				Game gameData					= DataSaver.load();
				
				if(gameData != null)
				{
					driver.setGame(gameData);

					ParkViewProtector.showTitle	= false;
					ParkViewProtector.selectChar = false;
					bgMusic.stop();
				}
				
				break;
			case 9:
				driver.quitGame();
				break;
		}
	}
}
