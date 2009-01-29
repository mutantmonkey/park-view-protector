/**
 * Game menu item
 * 
 * @author	James Schwinabart
 */

import java.awt.*;
import javax.swing.*;

public class MenuItem extends JLabel
{
	private String label;
	private int action;
	
	/**
	 * Creates a new menu item with the specified label
	 * 
	 * @param name
	 */
	public MenuItem(String label)
	{
		this.label			= label;
		this.action			= 0;
	}
	
	/**
	 * Creates a new menu item with the specified label
	 * 
	 * @param name
	 */
	public MenuItem(String label, int action)
	{
		this.label			= label;
		this.action			= action;
	}
	
	/**
	 * Gets the bounds of the menu item
	 * 
	 * @param g Graphics context
	 * @return Bounding box
	 */
	public Rectangle getBounds(Graphics g)
	{
		int width			= g.getFontMetrics().stringWidth(label);
		int height			= g.getFontMetrics().getHeight();
		
		return new Rectangle(width, height);
	}
	
	/**
	 * Gets the action ID
	 * 
	 * @return Action ID :D
	 */
	public int getAction()
	{
		return action;
	}
	
	/**
	 * Draws the menu item centered on the graphics context
	 *  
	 * @param g Graphics context
	 * @param x X position
	 * @param y Y position
	 */
	public void drawCentered(Graphics g, int x, int y)
	{
		// center the string
		int width			= g.getFontMetrics().stringWidth(label);
		x				   -= width / 2;
		
		draw(g, x, y);
	}
	
	/**
	 * Draws the menu item on the graphics context
	 *  
	 * @param g Graphics context
	 * @param x X position
	 * @param y Y position
	 */
	public void draw(Graphics g, int x, int y)
	{
		g.drawString(label, x, y);
	}
}
