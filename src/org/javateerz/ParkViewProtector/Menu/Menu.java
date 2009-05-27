/**
 * Game menu
 * 
 * @author	James Schwinabart
 */

package org.javateerz.ParkViewProtector.Menu;

import org.javateerz.EasyGL.*;
import org.javateerz.ParkViewProtector.DataSaver;
import org.javateerz.ParkViewProtector.Game;
import org.javateerz.ParkViewProtector.GameScreen;
import org.javateerz.ParkViewProtector.KeyboardConfig;
import org.javateerz.ParkViewProtector.ParkViewProtector;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.TrueTypeFont;

public class Menu extends GameScreen implements KeyListener
{
	public static final int TOP_SPACING				= 120;
	public static final int LINE_SPACING			= 40;
	public static final Color BG_COLOR				= new Color(0, 0, 0);
	public static final Color TEXT_COLOR			= new Color(255, 255, 255);
	public static final Color SELECTED_TEXT_COLOR	= new Color(255, 0, 255);
	
	public static final int RESET					= 8;
	public static final int QUIT 					= 9;
	
	protected static Font textFont;
	
	private MenuItem[] items						= {
			new MenuItem("Back", 1),
			new MenuItem("Options", 2),
			new MenuItem("Save Game", 4),
			new MenuItem("Load Game", 5),
			new MenuItem("Reset", Menu.RESET),
			new MenuItem("Quit Game", Menu.QUIT),
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
		
		// load background music
		setMusic("menu.ogg");
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
	
	public void step()
	{
		// key events
		addKeyListener(this);
		poll();
	}
	
	public void draw()
	{
		// ensure music is playing
		ensureMusicPlaying();
		
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
				items[i].setSelected(true);
			}
			else {
				items[i].setColor(TEXT_COLOR);
				items[i].setSelected(false);
			}
			
			items[i].drawCentered(ParkViewProtector.WIDTH / 2, TOP_SPACING + (i + 1) * LINE_SPACING);
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
				ParkViewProtector.showMenu		= false;
				bgMusic.stop();
				break;
			
			case 2:
				ParkViewProtector.showOptions	= true;
				break;
				
			case 4:
				ParkViewProtector.selectChar 	= false;
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
				ParkViewProtector.selectChar 	= false;
				bgMusic.stop();
				
				break;
				
			case Menu.RESET:
				driver.restart();
				break;
				
			case Menu.QUIT:
				driver.quitGame();
				break;
		}
	}
}
