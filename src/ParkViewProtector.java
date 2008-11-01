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
	
	protected JFrame window;
	protected JPanel contentPanel;
	
	public ParkViewProtector()
	{
		// create container JFrame (window)
		window						= new JFrame("Park View Protector");
		window.setSize(WIDTH, HEIGHT);
		
		// set up content panel
		contentPanel				= (JPanel) window.getContentPane();
		contentPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		// this makes the panel behave like what we were used to in ACM
		contentPanel.setLayout(null);
		
		setBounds(0, 0, WIDTH, HEIGHT);
		contentPanel.add(this);
		
		// make the window visible
		window.setVisible(true);
	}
	
	public static void main(String args[])
	{
		new ParkViewProtector();
	}
}