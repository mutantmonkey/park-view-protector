/**
 * Handles keyboard input
 * 
 * @author	Javateerz
 */

import java.awt.event.*;

public class KeyHandler extends KeyAdapter
{
	/**
	 * Called when a key is pressed (not necessarily released)
	 */
	public void keyPressed(KeyEvent e)
	{
		System.out.println("Key pressed: code " + e.getKeyCode());
		
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_KP_LEFT:
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_KP_RIGHT:
				break;
		}
	}
}