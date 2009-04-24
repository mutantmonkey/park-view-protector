package org.javateerz.ParkViewProtector;

import org.javateerz.EasyGL.GLString;

public abstract class MenuBlock extends GLString
{
	public MenuBlock(String str)
	{
		super(str);
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
