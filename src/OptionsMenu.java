/**
 * Options menu
 * 
 * @author	James Schwinabart
 */

import java.awt.*;
import java.awt.image.BufferStrategy;

public class OptionsMenu extends Menu
{
	public static final int LEFT_SPACING	= 60;
	public static final int RIGHT_SPACING	= 60;
	
	private OptionItem[] items				= {
			new MenuItem("Back"					, 1),
			
			new FloatOption("Music Volume"		, 0.6F),
			new FloatOption("Effects Volume"	, 0.9F),
		};
	
	/**
	 * Constructor
	 * 
	 * @param p Driver class
	 */
	public OptionsMenu(ParkViewProtector p)
	{
		super(p);
	}
	
	public void show()
	{
		// handle key presses
		if(ParkViewProtector.upPressed && selectedItem > 0)
		{
			selectedItem--;
		}
		else if(ParkViewProtector.downPressed && selectedItem < items.length - 1)
		{
			selectedItem++;
		}
		else if(ParkViewProtector.escPressed)
		{
			ParkViewProtector.showMenu		= false;
			ParkViewProtector.escPressed	= false;
		}
		
		// menu items are different
		if(items[selectedItem] instanceof MenuItem)
		{
			if(ParkViewProtector.enterPressed)
			{
				execute(((MenuItem) items[selectedItem]).getAction());
				
				ParkViewProtector.enterPressed	= false;
			}
		}
		else {
			if(ParkViewProtector.leftPressed)
			{
				items[selectedItem].leftPressed();
			}
			else if(ParkViewProtector.rightPressed)
			{
				items[selectedItem].rightPressed();
			}
		}
		
		g									= (Graphics) strategy.getDrawGraphics();
		
		// draw the background
		g.setColor(BG_COLOR);
		g.fillRect(0, 0, ParkViewProtector.WIDTH, ParkViewProtector.HEIGHT);
		
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
