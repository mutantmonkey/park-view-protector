/**
 * Sprite class using AWT
 *
 * @author	Jamie of the Javateerz
 */

import java.awt.*;

public class Sprite
{
	private Image img;
	
	/**
	 * Create a new sprite
	 * 
	 * @param image An image to use as the sprite
	 */
	public Sprite(Image img)
	{
		this.img		= img;
	}
	
	/**
	 * Draws the image on a Graphics context
	 * 
	 * @param g Graphics context
	 * @param x X Position
	 * @param y Y Position
	 */
	public void draw(Graphics g, int x, int y)
	{
		g.drawImage(img, x, y, null);
	}
}