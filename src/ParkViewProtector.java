/**
 * Park View Protector
 * 
 * This class is a subclass of Canvas so we can use accelerated graphics
 *
 * @author	Jamie of the Javateerz
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.*;

public class ParkViewProtector extends Canvas
{
	public static final int WIDTH			= 700;
	public static final int HEIGHT			= 500;
	
	public static final int SPEED_THROTTLE	= 10;
	public static final int MAX_STUDENTS	= 5;
	
	protected JFrame window;
	protected JPanel contentPanel;
	
	private boolean running					= true;
	
	// how far the player needs to move in each direction the next time the game loop is run
	public static int moveX					= 0;
	public static int moveY					= 0;
	
	// graphics
	private Graphics g;
	private BufferStrategy strategy;
	
	// characters
	private Staff player;
	private ArrayList<Student> students		= new ArrayList<Student>();
	
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
		
		// set up window
		window.setResizable(false);
		//window.pack();
		
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
		
		// don't automatically repaint
		setIgnoreRepaint(true);
		
		// initialize everything
		initPlayer();
		initStudents();
		
		// add key handler class
		addKeyListener(new KeyHandler());
		
		// request focus so we will get events without a click
		requestFocus();
	}
	
	/**
	 * Create and initialize player (the staff member we're playing as)
	 */
	public void initPlayer()
	{
		player						= new Stark(10, 10, 10, 10, 10.0, 10,10, 10);
	}
	
	/**
	 * Create and initialize students
	 */
	public void initStudents()
	{
		int numStudents				= (int) (Math.random() * MAX_STUDENTS) + 1;
		Student student				= null;
		
		int x, y;
		double speed;
		
		for(int i = 0; i < numStudents; i++)
		{
			x						= (int) (Math.random() * WIDTH) + 1;
			y						= (int) (Math.random() * HEIGHT) + 1;
			speed					= Math.random() * 5 + 1;
			
			student					= new Student(x, y, 5, 5, speed, 0, 'm');
			
			students.add(student);
		}
	}
	
	/**
	 * The main game loop
	 */
	public void mainLoop()
	{
		// accelerated graphics
		createBufferStrategy(2);
		strategy					= getBufferStrategy();
		
		while(running)
		{
			g						= (Graphics) strategy.getDrawGraphics();
			
			// draw the background
			g.setColor(Color.white);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			// update player
			player.draw(g);
			
			// update students
			for(int i = 0; i < students.size(); i++)
			{
				students.get(i).draw(g);
				
				// move students randomly for testing
				students.get(i).move((int) (Math.random() * 5) - 2, (int) (Math.random() * 5) - 2);
			}
			
			// finish drawing
			g.dispose();
			strategy.show();
			
			// move player
			player.move(moveX, moveY);
			moveX					= 0;
			moveY					= 0;
			
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