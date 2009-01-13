/**
 * Game menu item
 * 
 * @author	James Schwinabart
 */

import java.awt.Graphics;

public class MenuItem
{
	private String name;
	
	public MenuItem(String name)
	{
		this.name		= name;
	}
	
	public void draw(Graphics g, int y)
	{
		int x = 0;
		
		g.drawString(name, x, y);
	}
}
