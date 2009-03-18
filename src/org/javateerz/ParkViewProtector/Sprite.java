/**
 * Sprite class using AWT
 *
 * @author	Jamie of the Javateerz
 */

package org.javateerz.ParkViewProtector;

import org.lwjgl.opengl.GL11;

public class Sprite
{
	private GLTexture texture;
	
	private int width;
	private int height;
	
	/**
	 * Create a new sprite
	 * 
	 * @param image An image to use as the sprite
	 */
	public Sprite(GLTexture texture)
	{
		this.texture	= texture;
		
		width			= texture.getImageWidth();
		height			= texture.getImageHeight();
	}
	
	/**
	 * Gets the width (in pixels) of the sprite
	 * 
	 * @return Width of the sprite
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Gets the height (in pixels) of the sprite
	 * 
	 * @return Height of the sprite
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Draws the image on a Graphics context
	 * 
	 * @param g Graphics context
	 * @param x X Position
	 * @param y Y Position
	 */
	public void draw(int x, int y)
	{
		GL11.glPushMatrix();
		
		texture.bind();
		
		// move to selected location
		GL11.glTranslatef(x, y, 0);
		
		// set color for drawing
		GL11.glColor3f(1, 1, 1);
		
		// draw a quadrilateral
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(0, 0);
			GL11.glTexCoord2f(0, texture.getHeight());
			GL11.glVertex2f(0, height);
			GL11.glTexCoord2f(texture.getWidth(), texture.getHeight());
			GL11.glVertex2f(width, height);
			GL11.glTexCoord2f(texture.getWidth(), 0);
			GL11.glVertex2f(width, 0);
		}
		GL11.glEnd();
		
		GL11.glPopMatrix();
	}
}