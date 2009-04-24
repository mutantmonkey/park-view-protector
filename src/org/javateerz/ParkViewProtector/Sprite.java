/**
 * Sprite class using AWT
 *
 * @author	Jamie of the Javateerz
 */

package org.javateerz.ParkViewProtector;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sprite extends Image
{
	/**
	 * Create a new sprite
	 * 
	 * @param image An image to use as the sprite
	 * @throws SlickException 
	 */
	public Sprite(String img) throws SlickException
	{
		super(img);
	}
}