/**
 * Game menu
 * 
 * @author	James Schwinabart
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class Menu
{
	public static final int TOP_SPACING		= 120;
	public static final int LINE_SPACING	= 40;
	public static final Font textFont		= new Font("Dialog", Font.PLAIN, 32);
	public static final Color textColor		= Color.white;
	public static final Color selTextColor	= new Color(255, 0, 255);
	
	// graphics
	private Graphics g;
	private BufferStrategy strategy;
	
	private MenuItem[] items			= {
			new MenuItem("Back"),
			new MenuItem("Options"),
			new MenuItem("Quit Game"),
		};
	private int selectedItem			= 0;
	
	/**
	 * Constructor
	 * 
	 * @param w Width of the game canvas
	 * @param h Height of the game canvas
	 * @param g Graphics canvas
	 * @param strategy Buffer strategy
	 */
	public Menu(Graphics g, BufferStrategy strategy)
	{
		this.g							= g;
		this.strategy					= strategy;
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
		else if(ParkViewProtector.enterPressed)
		{
			// FIXME: replace with proper handling
			if(selectedItem == 0)
			{
				ParkViewProtector.showMenu	= false;
			}
			
			ParkViewProtector.enterPressed	= false;
		}
		else if(ParkViewProtector.escPressed)
		{
			ParkViewProtector.showMenu		= false;
			ParkViewProtector.escPressed	= false;
		}
		
		g									= (Graphics) strategy.getDrawGraphics();
		
		// draw the background
		g.setColor(Color.black);
		g.fillRect(0, 0, ParkViewProtector.WIDTH, ParkViewProtector.HEIGHT);
		
		// set font and color
		g.setFont(textFont);
		
		// draw menu items
		for(int i = 0; i < items.length; i++)
		{
			// set text color
			if(i == selectedItem)
			{
				g.setColor(selTextColor);
			}
			else {
				g.setColor(textColor);
			}
			
			items[i].draw(g, ParkViewProtector.WIDTH / 2, TOP_SPACING + (i + 1) * LINE_SPACING);
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
}
