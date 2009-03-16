/**
 * Sprite class using AWT
 *
 * @author	Jamie of the Javateerz
 */

package org.javateerz.ParkViewProtector;

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
	 * Gets the width (in pixels) of the sprite
	 * 
	 * @return Width of the sprite
	 */
	public int getWidth()
	{
		return img.getWidth(null);
	}
	
	/**
	 * Gets the height (in pixels) of the sprite
	 * 
	 * @return Height of the sprite
	 */
	public int getHeight()
	{
		return img.getHeight(null);
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