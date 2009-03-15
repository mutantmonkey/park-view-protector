/**
 * Game menu item
 * 
 * @author	James Schwinabart
 */

import java.awt.*;
import javax.swing.*;

public class MenuItem extends OptionItem
{
	private int action;
	
	/**
	 * Creates a new menu item with the specified label
	 * 
	 * @param label
	 */
	public MenuItem(String label)
	{
		super(label);
		
		this.action			= 0;
	}
	
	/**
	 * Creates a new menu item with the specified label
	 * 
	 * @param label
	 */
	public MenuItem(String label, int action)
	{
		super(label);
		
		this.action			= action;
	}
	
	/**
	 * Action for when the left key is pressed
	 */
	public void leftPressed()
	{
		// do nothing
	}
	
	/**
	 * Action for when the right key is pressed
	 */
	public void rightPressed()
	{
		// do nothing
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
}
