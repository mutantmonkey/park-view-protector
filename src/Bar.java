/**
 * A colored bar with a background
 * Can be used for statistics, options, etc.
 * 
 * @author	James Schwinabart
 */

import java.awt.*;

public class Bar
{
	private static final int BAR_HEIGHT	= 10;
	
	private Color color;
	
	private int width;
	private int maxWidth;
	
	/**
	 * Creates a new Bar
	 * 
	 * @param width Width in pixels
	 * @param maxWidth Maximum width in pixels
	 */
	public Bar(Color color, int width, int maxWidth)
	{
		this.color				= color;
		this.width				= width;
		this.maxWidth			= maxWidth;
	}
	
	/**
	 * Creates a new Bar
	 * 
	 * @param maxWidth Maximum width in pixels
	 * @param percent Percent of bar filled (0.0-1.0)
	 */
	public Bar(Color color, int maxWidth, double percent)
	{
		this.color				= color;
		this.width				= (int) (percent * maxWidth);
		this.maxWidth			= maxWidth;
	}
	
	/**
	 * Set the bar's color
	 * 
	 * @param color
	 */
	public void setColor(Color color)
	{
		this.color				= color;
	}
	
	/**
	 * Adjust the percentage filled
	 * 
	 * @param width
	 */
	public void setFilled(double percent)
	{
		this.width				= (int) (percent * maxWidth);
	}
	
	/**
	 * Draw the bar at the given position
	 * 
	 * @param g Graphics context
	 * @param x X-position
	 * @param y Y-position
	 */
	public void draw(Graphics g, int x, int y)
	{
		// background
		g.setColor(color.darker().darker());
		g.fillRect(x, y, maxWidth, BAR_HEIGHT);
		
		// main bar
		g.setColor(color);
		g.fillRect(x, y, width, BAR_HEIGHT);
	}
}
