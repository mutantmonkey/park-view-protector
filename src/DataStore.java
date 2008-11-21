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
import javax.sound.sampled.*;

// this is an enum, but we could also use our own Singleton class
public enum DataStore
{
	INSTANCE;
	
	private HashMap<String, Sprite> sprites	= new HashMap<String, Sprite>();
	
	public Clip getAudio(String file)
	{
		// load the file
		//URL url				= this.getClass().getClassLoader().getResource(file);
		File url			= new File(file);
		
		AudioInputStream stream = null;
		try
		{
			stream = AudioSystem.getAudioInputStream(url);
		}
		catch (UnsupportedAudioFileException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		AudioFormat format	= stream.getFormat();
		
		Clip clip = null;
		
		try
		{
			// Create the clip
			DataLine.Info info = new DataLine.Info(
				Clip.class, stream.getFormat(), ((int)stream.getFrameLength()*format.getFrameSize()));
			clip = (Clip) AudioSystem.getLine(info);
			
			// This method does not return until the audio file is completely loaded
			clip.open(stream);
			
			// Start playing
			clip.start();
		}
		catch (LineUnavailableException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return clip;
	}
	
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