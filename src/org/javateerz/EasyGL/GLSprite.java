//******************************************************//
//
// EasyGL
//
//******************************************************//
// Written by:
//	James Schwinabart
//******************************************************//
// EASYGL IS FREE SOFTWARE
// http://james.schwinabart.com/easygl/license/
//******************************************************//

package org.javateerz.EasyGL;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Hashtable;

import org.javateerz.ParkViewProtector.DataStore;
import org.lwjgl.opengl.GL11;

public class GLSprite extends GLObject
{
	public static final int DST_COLOR_MODEL			= GL11.GL_RGBA;
	
	private BufferedImage img;
	private int textureId;
	
	private int textureWidth;
	private int textureHeight;
	
	/**
	 * Creates a new sprite
	 * 
	 * @param img Source image
	 */
	public GLSprite(BufferedImage img)
	{
		super(0, 0);
		
		this.img					= img;
		this.width					= img.getWidth();
		this.height					= img.getHeight();
		
		initTexture();
	}
	
	/**
	 * Creates a new sprite at the given position
	 * 
	 * @param img Source image
	 * @param x
	 * @param y
	 */
	public GLSprite(BufferedImage img, int x, int y)
	{
		super(x, y);
		
		this.img					= img;
		this.width					= img.getWidth();
		this.height					= img.getHeight();
		
		initTexture();
	}
	
	/**
	 * Initializes the texture
	 */
	private void initTexture()
	{
		textureId					= createTextureId();
		
		GL11.glBindTexture(TEXTURE_TARGET, textureId);
		
		// initial pixel format (RGB or RGBA)
		int srcColorModel			= img.getColorModel().hasAlpha() ? GL11.GL_RGBA
			: GL11.GL_RGB;
		
		// convert the image data into something OpenGL can use
		ByteBuffer textureBuffer	= convertImageData();
		
		// set scaling methods
		GL11.glTexParameteri(TEXTURE_TARGET, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(TEXTURE_TARGET, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		
		GL11.glTexImage2D(TEXTURE_TARGET, 0, DST_COLOR_MODEL, get2Fold(width),
				get2Fold(height), 0, srcColorModel, GL11.GL_UNSIGNED_BYTE,
				textureBuffer);
	}
	
	/**
	 * Binds the texture to the OpenGL target
	 */
	public void bindTexture()
	{
		GL11.glEnable(TEXTURE_TARGET);
		GL11.glBindTexture(TEXTURE_TARGET, textureId);
	}
	
	/**
	 * Gets the width of the texture
	 * 
	 * @return
	 */
	private float getTextureWidth()
	{
		return (float) width / textureWidth;
	}
	
	/**
	 * Gets the height of the texture
	 * 
	 * @return
	 */
	private float getTextureHeight()
	{
		return (float) height / textureHeight;
	}
	
	/**
	 * Draws the image on the OpenGL target
	 */
	public void draw()
	{
		GL11.glPushMatrix();
		
		bindTexture();
		
		// move to selected location
		GL11.glTranslatef(x, y, 0);
		
		// set color to black so color is full
		GL11.glColor3d(1, 1, 1);
		
		// draw a quadrilateral
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(0, 0);
		GL11.glTexCoord2f(0, getTextureHeight());
		GL11.glVertex2f(0, height);
		GL11.glTexCoord2f(getTextureWidth(), getTextureHeight());
		GL11.glVertex2f(width, height);
		GL11.glTexCoord2f(getTextureWidth(), 0);
		GL11.glVertex2f(width, 0);
		GL11.glEnd();
		
		GL11.glPopMatrix();
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
	private static int createTextureId() 
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
	private static int get2Fold(int fold) {
		int ret = 2;
		while (ret < fold) {
			ret *= 2;
		}
		return ret;
	} 
	
	/**
	 * Convert the buffered image to a texture
	 *
	 * @return A buffer containing the data
	 */
	private ByteBuffer convertImageData()
	{ 
		ByteBuffer imageBuffer = null; 
		WritableRaster raster;
		BufferedImage texImage;
		
		int texWidth = 2;
		int texHeight = 2;
		
		// find the closest power of 2 for the width and height

		// of the produced texture

		while (texWidth < width)
		{
			texWidth *= 2;
		}
		while (texHeight < height)
		{
			texHeight *= 2;
		}
		
		textureWidth			= texWidth;
		textureHeight			= texHeight;
		
		// create a raster that can be used by OpenGL as a source

		// for a texture

		if (img.getColorModel().hasAlpha())
		{
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,texWidth,texHeight,4,null);
			texImage = new BufferedImage(DataStore.INSTANCE.glAlphaColorModel,raster,false,new Hashtable<Object, Object>());
		}
		else {
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,texWidth,texHeight,3,null);
			texImage = new BufferedImage(DataStore.INSTANCE.glColorModel,raster,false,new Hashtable<Object, Object>());
		}
			
		// copy the source image into the produced image

		Graphics g = texImage.getGraphics();
		g.setColor(new Color(0f,0f,0f,0f));
		g.fillRect(0,0,texWidth,texHeight);
		g.drawImage(img,0,0,null);
		
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
	protected static IntBuffer createIntBuffer(int size)
	{
	  ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
	  temp.order(ByteOrder.nativeOrder());

	  return temp.asIntBuffer();
	}
}
