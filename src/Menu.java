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
	public final int WIDTH;
	public final int HEIGHT;
	
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
	public Menu(int w, int h, Graphics g, BufferStrategy strategy)
	{
		// TODO: find out if this is legal
		WIDTH							= w;
		HEIGHT							= h;
		
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
		g.fillRect(0, 0, WIDTH, HEIGHT);
	
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
