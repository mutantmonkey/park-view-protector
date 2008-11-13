/**
 * Singleton class to handle data storage and caching (to prevent loading sprites, music,
 * etc. more than once)
 * 
 * @author	Jamie of the Javateerz
 */

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.*;

// this is an enum, but we could also use our own Singleton class
public enum DataStore
{
	INSTANCE;
	
	private HashMap<String, Sprite> sprites	= new HashMap<String, Sprite>();
	
	public Sprite getSprite(String file)
	{
		// is the sprite already cached?
		if(sprites.get(file) != null)
		{
			return sprites.get(file);
		}
		
		// load the file
		//URL url				= this.getClass().getClassLoader().getResource(file);
		File url			= new File(file);
		
		BufferedImage img	= null;
		
		// try to load the image
		try
		{
			img				= ImageIO.read(url);
		}
		catch(IOException e)
		{
			// bah
		}
		
		Sprite sprite		= new Sprite(img);
		
		// cache the sprite so it doesn't have to be loaded again
		sprites.put(file, sprite);
		
		return sprite;
	}
}