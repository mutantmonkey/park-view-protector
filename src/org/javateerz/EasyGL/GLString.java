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

/**
 * An OpenGL text string
 * 
 * @author	James Schwinabart
 */
public class GLString extends GLObject
{
	private String text;
	
	/**
	 * Creates a new OpenGL string
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
	 * Draws the OpenGL string on the display
	 */
	public void draw()
	{
		
	}
}
