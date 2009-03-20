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
		
		width					= text.length() * 5;
		height					= 10;
	}
	
	/**
	 * Draws the OpenGL string on the display
	 */
	public void draw()
	{
		GL11.glPushMatrix();
		
		// disable textures
		GL11.glDisable(TEXTURE_TARGET);
		
		// move to selected location
		GL11.glTranslatef(x, y, 0);
		
		// set color for drawing
		GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255,
				(float) color.getBlue() / 255, (float) color.getAlpha() / 255);
		
		// draw a quadrilateral
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(0, height);
		GL11.glVertex2f(width, height);
		GL11.glVertex2f(width, 0);
		GL11.glEnd();
		
		GL11.glPopMatrix();
	}
}
