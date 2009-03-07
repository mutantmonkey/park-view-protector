/**
 * Wall class
 * 
 * @author	Jamie of the Javateerz
 */

import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall extends Movable
{
	private static final long serialVersionUID = 1L;
	
	private int width;
	private int height;
	
	/**
	 * Create a new wall
	 * 
	 * @param x X-position
	 * @param y Y-position
	 * @param w Width
	 * @param h Height
	 */
	public Wall(int x, int y, int w, int h)
	{
		super(x, y, 0);
		
		width				= w;
		height				= h;
		
		updateSprite();
	}
	
	protected void updateSprite()
	{
		char orient;
		
		// determine orientation
		if(width > height)
		{
			orient			= 'h';
		}
		else {
			orient			= 'v';
		}
		
		sprite				= DataStore.INSTANCE.getSprite("wall_" + orient + ".png");
	}
	
	/**
	 * Called by main game loop, draws the object's sprite on the screen
	 */
	public void draw(Graphics g)
	{
		if(width > height)			// horizontal
		{
			int spriteWidth		= sprite.getWidth();
			
			for(int i = 0; i < width; i += spriteWidth)
			{
				sprite.draw(g, x + i, y);
			}
		}
		else {						// vertical
			int spriteHeight	= sprite.getHeight();
			
			for(int i = 0; i < height; i += spriteHeight)
			{
				sprite.draw(g, x, y + i);
			}
		}
	}
	
	/**
	 * Computes the bounding box for the object
	 * 
	 * @return Bounding box
	 */
	public Rectangle getBounds()
	{
		Rectangle ret		= new Rectangle(x, y, width, height);
		
		return ret;
	}
}
