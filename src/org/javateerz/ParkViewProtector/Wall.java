/**
 * Wall class
 * 
 * @author	Jamie of the Javateerz
 */

package org.javateerz.ParkViewProtector;

import org.javateerz.EasyGL.GLRect;
import org.newdawn.slick.geom.Rectangle;

public class Wall extends Movable
{
	public static final int NORMAL=0;
	public static final int NARROW_H=1;
	public static final int NARROW_V=2;
	public static final int SMALL=3;
	
	private static final long serialVersionUID = 1L;
	
	private int width;
	private int height;
	private int type;
	
	private Sprite spriteX;
	
	/**
	 * Create a new wall
	 * 
	 * @param g Instance of Game
	 * @param x X-position
	 * @param y Y-position
	 * @param w Width
	 * @param h Height
	 */
	public Wall(Game g, int t, int x, int y , int w, int h)
	{
		super(g, x, y, 0);
		
		width				= w;
		height				= h;
		type				= t;
		
		updateSprite();
	}
	
	protected void updateSprite()
	{
		try
		{
			sprite				= DataStore.INSTANCE.getSprite("wall_" + type + ".png");
			spriteX				= DataStore.INSTANCE.getSprite("wall_" + type + "x.png");
		}
		catch(Exception e)
		{
			sprite				= DataStore.INSTANCE.getSprite("wall_0.png");
		}
	}
	
	/**
	 * Called by main game loop, draws the object's sprite on the screen
	 */
	public void draw()
	{
		int spriteHeight	= sprite.getHeight() - 1;
		int spriteWidth		= sprite.getWidth() - 1;
		
		for(int i = 0; i < width; i ++)
		{
			for(int j = 0; j < height; j ++)
			{
				if(j+1>=height)
					spriteX.draw((int) x + i * spriteWidth, (int) y + j * spriteHeight);
				else
					sprite.draw((int) x + i * spriteWidth, (int) y + j * spriteHeight);
			}
		}
		
		/*GLRect raw				= new GLRect((int) x, (int) y, spriteWidth * width,
				spriteHeight * height);
		raw.draw();*/
	}
	
	/**
	 * Computes the bounding box for the object
	 * 
	 * @return Bounding box
	 */
	public Rectangle getBounds()
	{
		Rectangle ret		= new Rectangle((int) x, (int) y,
				width*sprite.getWidth(), height*sprite.getHeight());
		
		return ret;
	}
}
