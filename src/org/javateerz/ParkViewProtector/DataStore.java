/**
 * Singleton class to handle data storage and caching (to prevent loading sprites, music,
 * etc. more than once)
 * 
 * @author	Jamie of the Javateerz
 */

package org.javateerz.ParkViewProtector;

import java.awt.color.*;
import java.awt.image.*;

import java.io.*;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.*;
import javax.sound.sampled.*;

import org.newdawn.slick.SlickException;

// this is an enum, but we could also use our own Singleton class
public enum DataStore
{
	INSTANCE;
	
	private HashMap<String, Clip> clips		= new HashMap<String, Clip>();
	private HashMap<String, Sprite> sprites	= new HashMap<String, Sprite>();
	
	public ColorModel glAlphaColorModel;
	public ColorModel glColorModel;
	
	/**
	 * Constructor
	 */
	private DataStore()
	{
		// initialize OpenGL color models
		
		glAlphaColorModel					= new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                new int[] {8,8,8,8},
                true,
                false,
                ComponentColorModel.TRANSLUCENT,
                DataBuffer.TYPE_BYTE);
                
		glColorModel						= new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                new int[] {8,8,8,0},
                false,
                false,
                ComponentColorModel.OPAQUE,
                DataBuffer.TYPE_BYTE);

	}
	
	/**
	 * Loads/retrieves an audio clip
	 * 
	 * @param file The audio clip to load
	 * @return The audio clip
	 */
	public Clip getAudioClip(String file)
	{
		// is the clip already cached?
		if(clips.get(file) != null)
		{
			return clips.get(file);
		}
		
		AudioInputStream stream;
		Clip clip;
		
		// load the file
		URL url					= Thread.currentThread().getContextClassLoader().getResource(file);
		//File url				= new File(file);
		
		try
		{
			stream				= AudioSystem.getAudioInputStream(url);
		}
		catch(UnsupportedAudioFileException e)
		{
			System.out.println("Unsupported audio file");
			return null;
		}
		catch(IOException e)
		{
			System.out.println("Error loading audio clip: " + file);
			return null;
		}
		
		AudioFormat format		= stream.getFormat();
		
		// convert ALAW and ULAW encodings
		if(format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED)
		{
			format				= new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					format.getSampleRate(),
					format.getSampleSizeInBits() * 2,
					format.getChannels(),
					format.getFrameSize() * 2,
					format.getFrameRate(),
					true);
			
			stream				= AudioSystem.getAudioInputStream(format, stream);
		}
		
		try
		{
			// Create the clip
			DataLine.Info info	= new DataLine.Info(
					Clip.class,
					stream.getFormat(),
					(int) (stream.getFrameLength() * format.getFrameSize()));
			
			clip				= (Clip) AudioSystem.getLine(info);
			
			// This method does not return until the audio file is completely loaded
			clip.open(stream);
		}
		catch(LineUnavailableException e)
		{
			//e.printStackTrace();
			System.out.println("Line unavailable");
			return null;
		}
		catch(IOException e)
		{
			//e.printStackTrace();
			System.out.println("Error loading audio clip: " + file);
			return null;
		}
		
		// cache clip so it doesn't have to be loaded again
		//clips.put(file, clip);
		
		return clip;
	}

	/**
	 * Loads/retrieves a sprite
	 * 
	 * @param file The image to use as a sprite
	 * @return The loaded sprite (instance of Sprite)
	 */
	public Sprite getSprite(String file)
	{
		// is the sprite already cached?
		if(sprites.get(file) != null)
		{
			return sprites.get(file);
		}
		
		//BufferedImage img		= loadImage(file);
		Sprite sprite = null;
		try
		{
			sprite = new Sprite(file);
			
			// cache the sprite so it doesn't have to be loaded again
			sprites.put(file, sprite);
		}
		catch(SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sprite;
	}
	
	/**
	 * Loads an image
	 * 
	 * @param file Image file to load
	 * @return
	 */
	public BufferedImage loadImage(String file)
	{
		URL url					= Thread.currentThread().getContextClassLoader().getResource(file);
		
		BufferedImage img		= null;
		
		// try to load the image
		try
		{
			img					= ImageIO.read(url);
		}
		catch(IOException e)
		{
			ParkViewProtector.error("Error loading sprite: " + file);
			return null;
		}
		
		return img;
	}
}