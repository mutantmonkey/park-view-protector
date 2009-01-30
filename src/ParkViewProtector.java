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
	public static final int WIDTH			= 800;
	public static final int HEIGHT			= 600;
	
	protected JFrame window;
	protected JPanel contentPanel;
	
	private boolean running					= true;
	public static boolean showTitle			= true;
	public static boolean showMenu			= false;
	
	// which keys are pressed
	public static boolean upPressed			= false;
	public static boolean downPressed		= false;
	public static boolean leftPressed		= false;
	public static boolean rightPressed		= false;
	public static boolean attackPressed		= false;
	public static boolean escPressed		= false;
	public static boolean enterPressed		= false;
	
	// logos
	Sprite jtzLogo;
	
	// graphics
	private Graphics g;
	private BufferStrategy strategy;
	
	private TitleScreen title;
	private Game game;
	private Menu menu;
	
	public ParkViewProtector()
	{
		// load logos
		jtzLogo						= DataStore.INSTANCE.getSprite("images/javateerslogo.png");
		
		// create container JFrame (window)
		window						= new JFrame("Park View Protector");
		window.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		// set up content panel
		contentPanel				= (JPanel) window.getContentPane();
		contentPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		// add canvas
		contentPanel.setLayout(new GridLayout(1, 1));
		contentPanel.add(this);
		
		// set up window
		window.pack();
		window.setResizable(false);
		
		// make the window visible
		window.setVisible(true);
		
		// this makes the program end when the window is closed
		window.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				running				= false;
				
				window.dispose();
				//System.exit(0);
			}
		});
		
		// don't automatically repaint
		setIgnoreRepaint(true);
	}
	
	/**
	 * Show opening graphics
	 */
	public void showOpening()
	{
		DataStore.INSTANCE.getAudio("sounds/clockhomestart.wav");
		
		Graphics g						= getGraphics();
		
		// draw the background
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		// Javateerz logo
		jtzLogo.draw(g, WIDTH / 2 - jtzLogo.getWidth() / 2, HEIGHT / 2 - jtzLogo.getHeight() / 2);
		g.dispose();
		
		try{Thread.sleep(3000);}catch(Exception e){}
	}
	
	/**
	 * Initialize game
	 */
	public void init()
	{
		DataStore.INSTANCE.getAudio("sounds/nof.wav");
		
		// add key handler class
		addKeyListener(new KeyHandler());
		
		// request focus so we will get events without a click
		requestFocus();
		
		// accelerated graphics
		createBufferStrategy(2);
		strategy					= getBufferStrategy();
		
		title						= new TitleScreen(this, g, strategy);
		game						= new Game(this, g, strategy);
		menu						= new Menu(this, g, strategy);
	}
	
	/**
	 * The main game loop
	 */
	public void mainLoop()
	{
		Student currStudent;
		Cupple currCouple;
		Attack currAttack;
		
		int student1, student2;
		
		while(running)
		{
			if(showTitle)
			{
				title.show();
			}
			else if(showMenu)
			{
				menu.show();
			}
			else {
				game.show();
			}
		}
	}
	
	public void quit()
	{
		running							= false;
		window.dispose();
	}
	
	public static void main(String args[])
	{
		ParkViewProtector game			= new ParkViewProtector();
		game.showOpening();
		game.init();
		game.mainLoop();
		
		System.out.println("The game has finished running! Yay");
	}
}