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
		//System.out.println("Key pressed: code " + e.getKeyCode());
		
		switch(e.getKeyCode())
		{
			// UP
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
			case KeyEvent.VK_NUMPAD8:
			case KeyEvent.VK_W:
				ParkViewProtector.upPressed			= true;
				break;
			
			// DOWN
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
			case KeyEvent.VK_NUMPAD2:
			case KeyEvent.VK_S:
				ParkViewProtector.downPressed		= true;
				break;
				
			// LEFT
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_KP_LEFT:
			case KeyEvent.VK_NUMPAD4:
			case KeyEvent.VK_A:
				ParkViewProtector.leftPressed		= true;
				break;
				
			// RIGHT
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_KP_RIGHT:
			case KeyEvent.VK_NUMPAD6:
			case KeyEvent.VK_D:
				ParkViewProtector.rightPressed		= true;
				break;
			
			// UP, TO THE LEFT
			case KeyEvent.VK_NUMPAD7:
				ParkViewProtector.upPressed			= true;
				ParkViewProtector.leftPressed		= true;
				break;
				
			// UP, TO THE RIGHT
			case KeyEvent.VK_NUMPAD9:
				ParkViewProtector.upPressed			= true;
				ParkViewProtector.rightPressed		= true;
				break;
				
			// DOWN, TO THE LEFT
			case KeyEvent.VK_NUMPAD1:
				ParkViewProtector.downPressed		= true;
				ParkViewProtector.leftPressed		= true;
				break;
				
			// DOWN, TO THE RIGHT
			case KeyEvent.VK_NUMPAD3:
				ParkViewProtector.downPressed		= true;
				ParkViewProtector.rightPressed		= true;
				break;
				
			case KeyEvent.VK_SPACE:
				ParkViewProtector.attackPressed=true;
				break;
				
			case KeyEvent.VK_Z:
				ParkViewProtector.zPressed			= true;
				ParkViewProtector.attackPressed		= true;
				break;
				
			case KeyEvent.VK_X:
				ParkViewProtector.xPressed			= true;
				ParkViewProtector.attackPressed		= true;
				break;
				
			case KeyEvent.VK_C:
				ParkViewProtector.cPressed			= true;
				ParkViewProtector.attackPressed		= true;
				break;
				
				
			// "m" for menu?
			case KeyEvent.VK_M:
				ParkViewProtector.showMenu			= true;
				break;
				
			case KeyEvent.VK_ESCAPE:
				ParkViewProtector.escPressed		= true;
				break;
				
			case KeyEvent.VK_ENTER:
				ParkViewProtector.enterPressed		= true;
				break;
				
			case KeyEvent.VK_SHIFT:
				ParkViewProtector.shiftPressed		= true;
				break;
				
			case KeyEvent.VK_1:
				ParkViewProtector.onePressed		= true;
				break;
			case KeyEvent.VK_2:
				ParkViewProtector.twoPressed		= true;
				break;
		}
	}
	
	/**
	 * Called when a key is pressed (not necessarily released)
	 */
	public void keyReleased(KeyEvent e)
	{
		//System.out.println("Key release: code " + e.getKeyCode());
		
		switch(e.getKeyCode())
		{
			// UP
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
			case KeyEvent.VK_NUMPAD8:
			case KeyEvent.VK_W:
				ParkViewProtector.upPressed			= false;
				break;
				
			// DOWN
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
			case KeyEvent.VK_NUMPAD2:
			case KeyEvent.VK_S:
				ParkViewProtector.downPressed		= false;
				break;
				
			// LEFT
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_KP_LEFT:
			case KeyEvent.VK_NUMPAD4:
			case KeyEvent.VK_A:
				ParkViewProtector.leftPressed		= false;
				break;
				
			// RIGHT
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_KP_RIGHT:
			case KeyEvent.VK_NUMPAD6:
			case KeyEvent.VK_D:
				ParkViewProtector.rightPressed		= false;
				break;
				
			// UP, TO THE LEFT
			case KeyEvent.VK_NUMPAD7:
				ParkViewProtector.upPressed			= false;
				ParkViewProtector.leftPressed		= false;
				break;
				
			// UP, TO THE RIGHT
			case KeyEvent.VK_NUMPAD9:
				ParkViewProtector.upPressed			= false;
				ParkViewProtector.rightPressed		= false;
				break;
				
			// DOWN, TO THE LEFT
			case KeyEvent.VK_NUMPAD1:
				ParkViewProtector.downPressed		= false;
				ParkViewProtector.leftPressed		= false;
				break;
				
			// DOWN, TO THE RIGHT
			case KeyEvent.VK_NUMPAD3:
				ParkViewProtector.downPressed		= false;
				ParkViewProtector.rightPressed		= false;
				break;
				
			case KeyEvent.VK_SPACE:
				ParkViewProtector.attackPressed		= false;
				break;
				
			case KeyEvent.VK_Z:
				ParkViewProtector.zPressed			= false;
				ParkViewProtector.attackPressed		= false;
				break;
				
			case KeyEvent.VK_X:
				ParkViewProtector.xPressed			= false;
				ParkViewProtector.attackPressed		= false;
				break;
				
			case KeyEvent.VK_C:
				ParkViewProtector.cPressed			= false;
				ParkViewProtector.attackPressed		= false;
				break;
				
			case KeyEvent.VK_ESCAPE:
				ParkViewProtector.escPressed		= false;
				break;
				
			case KeyEvent.VK_ENTER:
				ParkViewProtector.enterPressed		= false;
				break;
				
			case KeyEvent.VK_SHIFT:
				ParkViewProtector.shiftPressed		= false;
				break;
				
			case KeyEvent.VK_1:
				ParkViewProtector.onePressed		= false;
				break;
			case KeyEvent.VK_2:
				ParkViewProtector.twoPressed		= false;
				break;
		}
	}
}