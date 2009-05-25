package org.javateerz.ParkViewProtector.Menu;

import org.javateerz.EasyGL.GLString;
import org.javateerz.ParkViewProtector.DataStore;
import org.javateerz.ParkViewProtector.Sprite;

public abstract class MenuBlock extends GLString
{
	private boolean selected;
	private Sprite selectedSprite;
	
	public MenuBlock(String str)
	{
		super(str);
	}
	
	public void setSelected(boolean isSelected)
	{
		selected			= isSelected;
	}
	
	/**
	 * Draws the menu item centered on the graphics context
	 * 
	 * @param x X position
	 * @param y Y position
	 */
	public void drawCentered(int x, int y)
	{
		// center the string
		int width			= getBounds().getWidth();
		x				   -= width / 2;
		
		draw(x, y);
	}
	
	/**
	 * Draws the menu item at the specified position
	 * 
	 * @param x x position
	 * @param y y position
	 */
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
