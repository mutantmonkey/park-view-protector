/**
 * This class handles keyboard input and sends it to the main ParkViewProtector
 * class for later processing
 * 
 * Why? This class runs in a separate thread; things would go screwy if we tried
 * to do processing here.
 * 
 * @author	Jamie of the Javateerz
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
				ParkViewProtector.moveY--;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
				ParkViewProtector.moveY++;
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_KP_LEFT:
				ParkViewProtector.moveX--;
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_KP_RIGHT:
				ParkViewProtector.moveX++;
				break;
		}
	}
}