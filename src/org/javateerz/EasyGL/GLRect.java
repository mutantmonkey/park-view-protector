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
 * An OpenGL rectangle
 * 
 * @author	James Schwinabart
 */
public class GLRect extends GLObject
{
	/**
	 * Create a new OpenGL rectangle
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public GLRect(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}
	
	/**
	 * Draws the OpenGL rectangle on the display
	 */
	public void draw()
	{
		GL11.glPushMatrix();
		
		// disable textures
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
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
