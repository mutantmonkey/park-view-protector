package org.javateerz.ParkViewProtector;

public class Location
{
	private int x;
	private int y;
	
	/**
	 * Create a new location
	 * 
	 * @param x x-component of location
	 * @param y y-component of location
	 */
	public Location(int x, int y)
	{
		this.x			= x;
		this.y			= y;
	}
	
	/**
	 * @return x-component of location
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * @return y-component of location
	 */
	public int getY()
	{
		return y;
	}
}
