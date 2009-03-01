/**
 * Game title screen
 * 
 * @author	James Schwinabart
 */

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
	
	Sprite mainLogo;
	
	/**
	 * Constructor
	 * 
	 * @param p Driver class
	 */
	public TitleScreen(ParkViewProtector p)
	{
		super(p);
		
		mainLogo					= DataStore.INSTANCE.getSprite("images/logo.png");
	}
	
	public void show()
	{
		g							= (Graphics) strategy.getDrawGraphics();
		
		// draw logo
		mainLogo.draw(g, 0, 0);
		
		// handle key presses
		if(ParkViewProtector.upPressed && selectedItem > 0)
		{
			selectedItem--;
		}
		else if(ParkViewProtector.downPressed && selectedItem < items.length - 1)
		{
			selectedItem++;
		}
		else if(ParkViewProtector.enterPressed)
		{
			execute(items[selectedItem].getAction());
			
			ParkViewProtector.enterPressed	= false;
		}
		else if(ParkViewProtector.escPressed)
		{
			ParkViewProtector.showMenu		= false;
			ParkViewProtector.escPressed	= false;
		}
		
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
				ParkViewProtector.showTitle	= false;
				break;
			
			case 2:
				driver.setGame(DataSaver.load());
				
				ParkViewProtector.showTitle	= false;
				break;
		}
	}
}
