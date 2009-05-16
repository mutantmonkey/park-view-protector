/**
 * Items for the option menu
 * 
 * @author	James Schwinabart
 */

package org.javateerz.ParkViewProtector.Menu;

import org.javateerz.ParkViewProtector.ParkViewProtector;

public abstract class OptionItem extends MenuBlock
{
	protected String key;
	
	/**
	 * Creates a new item with the specified label
	 * 
	 * @param label
	 */
	public OptionItem(String label)
	{
		super(label);
	}
	
	/**
	 * Creates a new item with the specified label and key
	 * 
	 * @param label
	 * @param key
	 */
	public OptionItem(String label, String key)
	{
		super(label);
		
		this.key			= key;
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
	 * Updates the option
	 */
	public abstract void update(ParkViewProtector p);
}
