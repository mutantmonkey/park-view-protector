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

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

/**
 * An OpenGL object
 * 
 * @author	James Schwinabart
 */
public abstract class GLObject
{
	public static final int TEXTURE_TARGET		= GL11.GL_TEXTURE_2D;
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	protected Color color	= new Color(Color.CYAN);
	
	/**
	 * Creates a new OpenGL object
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public GLObject(int x, int y)
	{
		this.x				= x;
		this.y				= y;
	}
	
	/**
	 * Creates a new OpenGL object
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public GLObject(int x, int y, int width, int height)
	{
		this.x				= x;
		this.y				= y;
		this.width			= width;
		this.height			= height;
	}
	
	/**
	 * Changes the color of the OpenGL object
	 * 
	 * @param c
	 */
	public void setColor(Color c)
	{
		color				= c;
	}
	
	/**
	 * Returns the width of the object
	 * 
	 * @return
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Returns the height of the object
	 * 
	 * @return
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Draws the OpenGL object on the display
	 */
	public abstract void draw();
}
