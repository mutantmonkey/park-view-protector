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
		// no sprite is actually shown at the moment (FIXME: then this shouldn't be here)
		sprite				= DataStore.INSTANCE.getSprite("images/wall_h.png");
	}
	
	/**
	 * Called by main game loop, draws the object's sprite on the screen
	 */
	public void draw(Graphics g)
	{
		g.fillRect(x, y, width, height);
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
