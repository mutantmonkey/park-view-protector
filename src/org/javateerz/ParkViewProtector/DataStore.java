/**
 * Singleton class to handle data storage and caching (to prevent loading sprites, music,
 * etc. more than once)
 * 
 * @author	Jamie of the Javateerz
 */

package org.javateerz.ParkViewProtector;

import org.newdawn.slick.SlickException;

// this is an enum, but we could also use our own Singleton class
public enum DataStore
{
	INSTANCE;
	
	/**
	 * Loads/retrieves a sprite
	 * 
	 * @param file The image to use as a sprite
	 * @return The loaded sprite (instance of Sprite)
	 */
	public Sprite getSprite(String file)
	{
		Sprite sprite = null;
		try
		{
			sprite = new Sprite(file);
		}
		catch(SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sprite;
	}
}