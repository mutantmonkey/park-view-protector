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
	public static final int LINE_SPACING	= 50;
	public static final Font textFont		= new Font("Vivaldi", Font.PLAIN, 42);
	public static final Color textColor		= Color.white;
	
	// graphics
	private Graphics g;
	private BufferStrategy strategy;
	
	private MenuItem[] items			= {
			new MenuItem("Ha Ha"),
			new MenuItem("Blah")
		};
	
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
		if(ParkViewProtector.escPressed)
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
		g.setColor(textColor);
		
		// draw menu items
		for(int i = 0; i < items.length; i++)
		{
			items[i].draw(g, (i + 1) * LINE_SPACING);
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
