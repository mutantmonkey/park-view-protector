/**
 * Sprite class using AWT
 *
 * @author	Javateerz
 */

import java.awt.*;

public class Sprite
{
	private Image image;
	
	/**
	 * Create a new sprite
	 * 
	 * @param image An image to use as the sprite
	 */
	public Sprite(Image image)
	{
		this.image		= image;
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
		g.drawImage(image, x, y, null);
	}
}