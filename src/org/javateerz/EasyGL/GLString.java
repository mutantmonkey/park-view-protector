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

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Font;
import org.newdawn.slick.opengl.TextureImpl;

/**
 * An OpenGL text string
 * 
 * @author	James Schwinabart
 */
public class GLString extends GLObject
{
	private Font font;
	private String text;
	
	/**
	 * Creates a new OpenGL string
	 * 
	 * @param str Text to display
	 */
	public GLString(String str)
	{
		text					= str;
	}
	
	/**
	 * Creates a new OpenGL string at the given position
	 * 
	 * @param str Text to display
	 * @param x
	 * @param y
	 */
	public GLString(String str, int x, int y)
	{
		super(x, y);
		
		text					= str;
	}
	
	/**
	 * Sets the font of the object
	 * 
	 * @param font
	 */
	public void setFont(Font font)
	{
		this.font				= font;
	}
	
	/**
	 * Gets the bounding box of the font
	 */
	public Rectangle getBounds()
	{
		if(font == null) super.getBounds();
		
		Rectangle ret			= new Rectangle(x, y, font.getWidth(text),
				font.getLineHeight());
		
		return ret;
	}
	
	/**
	 * Draws the OpenGL string on the display
	 */
	public void draw()
	{
		if(font == null) return;
		
		TextureImpl.bindNone();
		
		font.drawString(x, y, text, color);
	}
	
	/**
	 * Draws the menu item centered on the graphics context
	 * 
	 * @param x X position
	 * @param y Y position
	 */
	public void drawCentered(int x, int y)
	{
		// center the string
		int width			= getBounds().getWidth();
		x				   -= width / 2;
		
		draw(x, y);
	}
}
