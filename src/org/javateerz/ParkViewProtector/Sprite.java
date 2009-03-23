/**
 * Sprite class using AWT
 *
 * @author	Jamie of the Javateerz
 */

package org.javateerz.ParkViewProtector;

import java.awt.image.BufferedImage;

import org.javateerz.EasyGL.GLSprite;

public class Sprite extends GLSprite
{
	/**
	 * Create a new sprite
	 * 
	 * @param image An image to use as the sprite
	 */
	public Sprite(BufferedImage img)
	{
		super(img);
	}
}