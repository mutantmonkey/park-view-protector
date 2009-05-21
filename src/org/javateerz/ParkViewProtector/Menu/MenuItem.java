/**
 * Game menu item
 * 
 * @author	James Schwinabart
 */

package org.javateerz.ParkViewProtector.Menu;

import org.javateerz.ParkViewProtector.DataStore;
import org.javateerz.ParkViewProtector.Sprite;

public class MenuItem extends MenuBlock
{
	private int action;
	
	private boolean selected;
	private Sprite selectedSprite;
	
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
	
	public void setSelected(boolean isSelected)
	{
		selected			= isSelected;
	}
	
	public void draw(int x, int y)
	{
		if(selected)
		{
			selectedSprite	= DataStore.INSTANCE.getSprite("arrow.png");
			selectedSprite.draw(x - selectedSprite.getWidth(),
					y - selectedSprite.getHeight() / 2 + getBounds().getHeight() / 2);
		}
		
		super.draw(x, y);
	}
}
