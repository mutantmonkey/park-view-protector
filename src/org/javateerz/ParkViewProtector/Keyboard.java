/**
 * Key handling
 * 
 * @author	Jamie of the Javateerz
 */

package org.javateerz.ParkViewProtector;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter
{
	// movement
	public static final int UP				= KeyEvent.VK_W;
	public static final int DOWN			= KeyEvent.VK_S;
	public static final int LEFT			= KeyEvent.VK_A;
	public static final int RIGHT			= KeyEvent.VK_D;
	
	// options
	public static final int MENU			= KeyEvent.VK_M;
	public static final int SHOW_CHARGES	= KeyEvent.VK_SHIFT;
	
	// navigation
	public static final int NAV_UP			= KeyEvent.VK_UP;
	public static final int NAV_DOWN		= KeyEvent.VK_DOWN;
	public static final int NAV_LEFT		= KeyEvent.VK_LEFT;
	public static final int NAV_RIGHT		= KeyEvent.VK_RIGHT;
	public static final int BACK			= KeyEvent.VK_ESCAPE;
	public static final int ENTER			= KeyEvent.VK_ENTER;
	
	// character change
	public static final int CHAR1			= KeyEvent.VK_1;
	public static final int CHAR2			= KeyEvent.VK_2;
	
	// attacks
	public static final int ATTACK1			= KeyEvent.VK_J;
	public static final int ATTACK2			= KeyEvent.VK_K;
	public static final int ATTACK3			= KeyEvent.VK_L;
	
	public static final int USE_HP_ITEM		= KeyEvent.VK_I;
	public static final int USE_TP_ITEM		= KeyEvent.VK_O;
	
	private static boolean[] keys			= new boolean[1024];
	
	/**
	 * Called when a key is pressed (not necessarily released)
	 */
	public void keyPressed(KeyEvent e)
	{
		// if the event has been consumed, don't handle it
		if(e.isConsumed())
		{
			return;
		}
		
		keys[e.getKeyCode()]				= true;
		
		// special handling
		// FIXME: replace with a better solution
		if(e.getKeyCode() == MENU)
		{
			ParkViewProtector.showMenu	= true;
		}
	}
	
	/**
	 * Called when a key is pressed (not necessarily released)
	 */
	public void keyReleased(KeyEvent e)
	{
		// if the event has been consumed, don't handle it
		if(e.isConsumed())
		{
			return;
		}
		
		setReleased(e.getKeyCode());
	}
	
	/**
	 * Mark a key as released
	 * 
	 * @param keyCode Key code
	 */
	public static void setReleased(int keyCode)
	{
		keys[keyCode]						= false;
	}
	
	/**
	 * Check if the key is pressed
	 * 
	 * @param keyCode Key code
	 * @return
	 */
	public static boolean isPressed(int keyCode)
	{
		return keys[keyCode];
	}
}
