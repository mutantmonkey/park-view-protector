/**
 * Game menu item
 * 
 * @author	James Schwinabart
 */

import java.awt.*;
import javax.swing.*;

public class MenuItem extends JLabel
{
	private String name;
	
	public MenuItem(String name)
	{
		this.name		= name;
	}
	
	public void draw(Graphics g, int x, int y)
	{
		// center the string
		int width		= g.getFontMetrics().stringWidth(name);
		x			   -= width / 2;
		
		g.drawString(name, x, y);
	}
}
