/**
 * Key handling
 * 
 * @author	Jamie of the Javateerz
 */

package org.javateerz.ParkViewProtector;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public static class Keyboard extends org.lwjgl.input.Keyboard
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
}
