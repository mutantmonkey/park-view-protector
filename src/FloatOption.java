/**
 * Float items for the option menu
 * 
 * @author	James Schwinabart
 */

import java.awt.Graphics;

public class FloatOption extends OptionItem
{
	public static final int BAR_WIDTH		= 200;
	public static final float ACCURACY		= 0.05F;
	
	private float value;
	private Bar optionBar;
	
	/**
	 * Creates a new option item for a float
	 * 
	 * @param label
	 * @param value
	 */
	public FloatOption(String label, float value)
	{
		super(label);
		
		this.value				= value;
		this.optionBar			= new Bar(ParkViewProtector.COLOR_BG_1, BAR_WIDTH, (double) value);
	}
	
	/**
	 * Action for when the left key is pressed
	 */
	public void leftPressed()
	{
		if(value > 0)
		{
			value			   -= ACCURACY;
		}
		
		// update option bar to show new value
		optionBar.setFilled(value);
	}
	
	/**
	 * Action for when the right key is pressed
	 */
	public void rightPressed()
	{
		if(value < 1.0)
		{
			value			   += ACCURACY;
		}
		
		// update option bar to show new value
		optionBar.setFilled(value);
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
		super.draw(g, x, y);
		
		x						= ParkViewProtector.WIDTH - OptionsMenu.RIGHT_SPACING - BAR_WIDTH;
		y						= (int) (y - getBounds(g).getHeight() / 2);
		
		optionBar.draw(g, x, y);
	}
}
