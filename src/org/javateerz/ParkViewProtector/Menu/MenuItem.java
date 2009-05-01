/**
 * Game menu item
 * 
 * @author	James Schwinabart
 */

package org.javateerz.ParkViewProtector.Menu;

public class MenuItem extends MenuBlock
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
	 * Gets the action ID
	 * 
	 * @return Action ID :D
	 */
	public int getAction()
	{
		return action;
	}
}
