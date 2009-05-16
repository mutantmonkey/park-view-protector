/**
 * Float items for the option menu
 * 
 * @author	James Schwinabart
 */

package org.javateerz.ParkViewProtector;

import org.javateerz.ParkViewProtector.Menu.OptionItem;
import org.javateerz.ParkViewProtector.Menu.OptionsMenu;

public class FloatOption extends OptionItem
{
	public static final int BAR_WIDTH		= 200;
	public static final float ACCURACY		= 0.03f;
	
	private float value;
	private Bar optionBar;
	
	/**
	 * Creates a new option item for a float
	 * 
	 * @param label
	 * @param value
	 */
	public FloatOption(String label, String key, float value)
	{
		super(label, key);
		
		this.value				= value;
		this.optionBar			= new Bar(ParkViewProtector.COLOR_BG_1, BAR_WIDTH,
				(double) value);
	}
	
	/**
	 * Action for when the left key is pressed
	 */
	public void leftPressed()
	{
		if(value >= ACCURACY)
		{
			value			   -= ACCURACY;
		}
	}
	
	/**
	 * Action for when the right key is pressed
	 */
	public void rightPressed()
	{
		if(value <= 1.0 - ACCURACY)
		{
			value			   += ACCURACY;
		}
	}
	
	/**
	 * Updates the option
	 */
	public void update(ParkViewProtector p)
	{
		// update option bar to show new value
		optionBar.setFilled(value);
		
		// update preference
		p.setFloat(key, value);
	}
	
	/**
	 * Returns the current value of the option
	 * 
	 * @return
	 */
	public float getValue()
	{
		return value;
	}
	
	/**
	 * Draws the item on the graphics context
	 *  
	 * @param x X position
	 * @param y Y position
	 */
	public void draw(int x, int y)
	{
		super.draw(x, y);
		
		x						= ParkViewProtector.WIDTH - OptionsMenu.RIGHT_SPACING
									- BAR_WIDTH;
		y						= (int) (y + getBounds().getHeight() / 2);
		
		optionBar.draw(x, y);
	}
}
