/**
 * Park View Protector
 * 
 * This class is a subclass of Canvas so we can use accelerated graphics
 *
 * @author	Javateerz
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ParkViewProtector extends Canvas
{
	public static final int WIDTH	= 700;
	public static final int HEIGHT	= 500;
	
	public static final int SPEED_THROTTLE	= 100;
	
	protected JFrame window;
	protected JPanel contentPanel;
	
	private boolean running			= true;
	
	public ParkViewProtector()
	{
		// create container JFrame (window)
		window						= new JFrame("Park View Protector");
		window.setSize(WIDTH, HEIGHT);
		
		// set up content panel
		contentPanel				= (JPanel) window.getContentPane();
		contentPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		// this makes the panel behave like what we were used to with ACM
		contentPanel.setLayout(null);
		
		// set content panel size
		setBounds(0, 0, WIDTH, HEIGHT);
		contentPanel.add(this);
		
		// make the window visible
		window.setVisible(true);
		
		// this makes the program end when the window is closed
		window.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				window.dispose();
				System.exit(0);
			}
		});
	}
	
	/**
	 * The main game loop
	 */
	public void mainLoop()
	{
		while(running)
		{
			// keep the game from running too fast
			try
			{
				Thread.sleep(SPEED_THROTTLE);
			}
			catch(Exception e) {}
		}
	}
	
	public static void main(String args[])
	{
		ParkViewProtector game			= new ParkViewProtector();
		game.mainLoop();
		
		System.out.println("The game has finished running! Yay");
	}
}