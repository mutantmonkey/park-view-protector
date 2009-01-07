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
	// graphics
	private Graphics g;
	private BufferStrategy strategy;
	
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
		
		g								= (Graphics) strategy.getDrawGraphics();
		
		// draw the background
		g.setColor(Color.black);
		g.fillRect(0, 0, ParkViewProtector.WIDTH, ParkViewProtector.HEIGHT);
	
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
