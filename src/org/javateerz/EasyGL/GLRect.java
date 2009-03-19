package org.javateerz.EasyGL;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

public class GLRect extends GLObject
{
	private Color color		= new Color(Color.CYAN);
	
	public GLRect(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}
	
	public void setColor(Color c)
	{
		color				= c;
	}
	
	public void draw()
	{
		GL11.glPushMatrix();
		
		// move to selected location
		GL11.glTranslatef(x, y, 0);
		
		// set color for drawing
		GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255,
				(float) color.getBlue() / 255, (float) color.getAlpha() / 255);
		
		// draw a quadrilateral
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2i(0, 0);
		GL11.glVertex2i(0, height);
		GL11.glVertex2i(width, height);
		GL11.glVertex2i(width, 0);
		GL11.glEnd();
		
		GL11.glPopMatrix();
	}
}
