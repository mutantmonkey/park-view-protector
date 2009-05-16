/**
 * A colored bar with a background
 * Can be used for statistics, options, etc.
 * 
 * @author	James Schwinabart
 */

package org.javateerz.ParkViewProtector;

import org.javateerz.EasyGL.GLRect;
import org.newdawn.slick.Color;

public class Bar
{
	public static final int BAR_HEIGHT			= 10;
	public static final double DARKEN_FACTOR	= 0.5;
	
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
	public void draw(int x, int y)
	{
		// background
		GLRect bg				= new GLRect(x, y, maxWidth, BAR_HEIGHT);
		bg.setColor(color.darker(0.5f));
		bg.draw();
		
		// main bar
		GLRect fg				= new GLRect(x, y, width, BAR_HEIGHT);
		fg.setColor(color);
		fg.draw();
	}
	
	public Color darken(Color color)
	{
		int red					= (int) (color.getRed() * DARKEN_FACTOR);
		int green				= (int) (color.getGreen() * DARKEN_FACTOR);
		int blue				= (int) (color.getBlue() * DARKEN_FACTOR);
		
		return new Color(red, green, blue);
	}
}
