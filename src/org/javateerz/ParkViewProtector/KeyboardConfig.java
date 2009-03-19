/**
 * Key handling
 * 
 * @author	Jamie of the Javateerz
 */

package org.javateerz.ParkViewProtector;

import org.lwjgl.input.Keyboard;

public class KeyboardConfig
{
	// movement
	public static final int UP				= Keyboard.KEY_W;
	public static final int DOWN			= Keyboard.KEY_S;
	public static final int LEFT			= Keyboard.KEY_A;
	public static final int RIGHT			= Keyboard.KEY_D;
	
	// options
	public static final int MENU			= Keyboard.KEY_M;
	public static final int SHOW_CHARGES	= Keyboard.KEY_LSHIFT;
	
	// navigation
	public static final int NAV_UP			= Keyboard.KEY_UP;
	public static final int NAV_DOWN		= Keyboard.KEY_DOWN;
	public static final int NAV_LEFT		= Keyboard.KEY_LEFT;
	public static final int NAV_RIGHT		= Keyboard.KEY_RIGHT;
	public static final int BACK			= Keyboard.KEY_ESCAPE;
	public static final int ENTER			= Keyboard.KEY_RETURN;
	
	// character change
	public static final int CHAR1			= Keyboard.KEY_1;
	public static final int CHAR2			= Keyboard.KEY_2;
	
	// attacks
	public static final int ATTACK1			= Keyboard.KEY_J;
	public static final int ATTACK2			= Keyboard.KEY_K;
	public static final int ATTACK3			= Keyboard.KEY_L;
}
