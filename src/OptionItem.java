/**
 * Items for the option menu
 * 
 * @author	James Schwinabart
 */

import java.awt.*;

public abstract class OptionItem
{
	protected String label;
	
	/**
	 * Creates a new item with the specified label
	 * 
	 * @param label
	 */
	public OptionItem(String label)
	{
		this.label			= label;
	}
	
	/**
	 * Action for when the left key is pressed
	 */
	public abstract void leftPressed();
	
	/**
	 * Action for when the right key is pressed
	 */
	public abstract void rightPressed();
	
	/**
	 * Gets the bounds of the item
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
	 * Draws the item on the graphics context
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
