package org.javateerz.EasyGL;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

public class GLRect
{
	private int x;
	private int y;
	private int width;
	private int height;
	
	private Color color		= new Color(Color.CYAN);
	
	public GLRect(int x, int y, int width, int height)
	{
		this.x				= x;
		this.y				= y;
		this.width			= width;
		this.height			= height;
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
		GL11.glColor4b(color.getRedByte(), color.getGreenByte(), color.getBlueByte(), color.getAlphaByte());
		
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
