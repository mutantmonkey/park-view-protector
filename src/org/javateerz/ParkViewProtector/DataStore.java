/**
 * Singleton class to handle data storage and caching (to prevent loading sprites, music,
 * etc. more than once)
 * 
 * @author	Jamie of the Javateerz
 */

package org.javateerz.ParkViewProtector;

import java.awt.*;
import java.awt.color.*;
import java.awt.image.*;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Hashtable;

import javax.imageio.*;
import javax.sound.sampled.*;

import org.lwjgl.opengl.GL11;

// this is an enum, but we could also use our own Singleton class
public enum DataStore
{
	INSTANCE;
	
	private HashMap<String, Clip> clips		= new HashMap<String, Clip>();
	private HashMap<String, Sprite> sprites	= new HashMap<String, Sprite>();
	private HashMap<String, GLTexture> textures	= new HashMap<String, GLTexture>();
	
	private ColorModel glAlphaColorModel;
	private ColorModel glColorModel;
	
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
		
		// load the file
		/*BufferedImage img		= loadImage(file);
		
		// create an image using accelerated graphics (hardware rendering, prevents flickering)
		GraphicsConfiguration gc	= GraphicsEnvironment.getLocalGraphicsEnvironment().
			getDefaultScreenDevice().getDefaultConfiguration();
		
		Image image				= gc.createCompatibleImage(img.getWidth(), img.getHeight(), Transparency.TRANSLUCENT);
		
		// draw image into accelerated image
		image.getGraphics().drawImage(img, 0, 0, null);
		
		Sprite sprite			= new Sprite(image);
		
		// cache the sprite so it doesn't have to be loaded again
		sprites.put(file, sprite);
		
		return sprite;*/
		
		return new Sprite(getTexture(file));
	}
	
	/**
	 * Load a texture
	 * 
	 * @param file The image to load as a texture
	 * @return The loaded texture
	 */
	public GLTexture getTexture(String file)
	{
		if(textures.get(file) != null)
		{
			return textures.get(file);
		}
		
		GLTexture texture			= getTexture(file, GL11.GL_TEXTURE_2D, GL11.GL_RGBA);
		
		textures.put(file, texture);
		
		return texture;
	}
	
	public GLTexture getTexture(String file, int glTarget, int colorModel)
	{
		int textureId				= createTextureId();
		GLTexture texture			= new GLTexture(glTarget, textureId);
		
		GL11.glBindTexture(glTarget, textureId);
		
		BufferedImage img			= loadImage(file);
		texture.setWidth(img.getWidth());
		texture.setHeight(img.getHeight());
		
		// selected initial pixel format (RGB or RGBA)
		int srcColorModel			= img.getColorModel().hasAlpha() ? GL11.GL_RGBA : GL11.GL_RGB;
		
		// convert the image data into something OpenGL can use
		ByteBuffer textureBuffer	= convertImageData(img, texture);
		
		// scaling methods
		if(glTarget == GL11.GL_TEXTURE_2D)
		{
			GL11.glTexParameteri(glTarget, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(glTarget, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		}
		
		GL11.glTexImage2D(glTarget, 0, colorModel, get2Fold(img.getWidth()), get2Fold(img.getHeight()), 0,
				srcColorModel, GL11.GL_UNSIGNED_BYTE, textureBuffer);
		
		return texture;
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
	
	////////////////////////////////////////////////////////////////////////////////////
	// FIXME: Utility methods borrowed from Coke and Code, should be reimplemented
	// Authors: Kevin Glass, Brian Matzon
	////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Create a new texture ID 
	 *
	 * @return A new texture ID
	 */
	private int createTextureId() 
	{ 
		IntBuffer tmp = createIntBuffer(1); 
		GL11.glGenTextures(tmp); 
		return tmp.get(0);
	}
	
	/**
	 * Get the closest greater power of 2 to the fold number
	 * 
	 * @param fold The target number
	 * @return The power of 2
	 */
	private int get2Fold(int fold) {
		int ret = 2;
		while (ret < fold) {
			ret *= 2;
		}
		return ret;
	} 
	
	/**
	 * Convert the buffered image to a texture
	 *
	 * @param bufferedImage The image to convert to a texture
	 * @param texture The texture to store the data into
	 * @return A buffer containing the data
	 */
	private ByteBuffer convertImageData(BufferedImage bufferedImage,GLTexture texture)
	{ 
		ByteBuffer imageBuffer = null; 
		WritableRaster raster;
		BufferedImage texImage;
		
		int texWidth = 2;
		int texHeight = 2;
		
		// find the closest power of 2 for the width and height

		// of the produced texture

		while (texWidth < bufferedImage.getWidth())
		{
			texWidth *= 2;
		}
		while (texHeight < bufferedImage.getHeight())
		{
			texHeight *= 2;
		}
		
		texture.setTextureHeight(texHeight);
		texture.setTextureWidth(texWidth);
		
		// create a raster that can be used by OpenGL as a source

		// for a texture

		if (bufferedImage.getColorModel().hasAlpha())
		{
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,texWidth,texHeight,4,null);
			texImage = new BufferedImage(glAlphaColorModel,raster,false,new Hashtable());
		}
		else {
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,texWidth,texHeight,3,null);
			texImage = new BufferedImage(glColorModel,raster,false,new Hashtable());
		}
			
		// copy the source image into the produced image

		Graphics g = texImage.getGraphics();
		g.setColor(new Color(0f,0f,0f,0f));
		g.fillRect(0,0,texWidth,texHeight);
		g.drawImage(bufferedImage,0,0,null);
		
		// build a byte buffer from the temporary image 

		// that be used by OpenGL to produce a texture.

		byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData(); 

		imageBuffer = ByteBuffer.allocateDirect(data.length); 
		imageBuffer.order(ByteOrder.nativeOrder()); 
		imageBuffer.put(data, 0, data.length); 
		imageBuffer.flip();
		
		return imageBuffer; 
	}
	
	/**
	 * Creates an integer buffer to hold specified ints
	 * - strictly a utility method
	 *
	 * @param size how many int to contain
	 * @return created IntBuffer
	 */
	protected IntBuffer createIntBuffer(int size)
	{
	  ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
	  temp.order(ByteOrder.nativeOrder());

	  return temp.asIntBuffer();
	}
}