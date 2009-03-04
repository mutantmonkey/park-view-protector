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
import java.io.FileInputStream;
//import java.util.ArrayList;

import javax.sound.sampled.Clip;
import javax.swing.*;

import org.newdawn.easyogg.OggClip;

public class ParkViewProtector extends Canvas
{
	public static final int WIDTH			= 800;
	public static final int HEIGHT			= 600;
	
	// colors
	public static final Color COLOR_BG_1	= new Color(255, 0, 255);
	public static final Color COLOR_TEXT_1	= Color.white;
	public static final Color COLOR_BG_2	= Color.white;
	public static final Color COLOR_TEXT_2	= Color.black;
	
	public static final Color STATS_BAR_BG	= new Color((float) 0, (float) 0, (float) 0, (float) 0.5);
	public static final Color STATS_BAR_FG	= Color.white;
	public static final Color STATS_BAR_HP	= new Color(255, 0, 255);
	public static final Color STATS_BAR_TP	= new Color(0, 255, 0);
	
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
	public static boolean shiftPressed		= false;
	public static boolean zPressed			= false;
	public static boolean xPressed			= false;
	public static boolean cPressed			= false;
	
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
		
		// set the Swing look and feel to the system one so the file selector looks native
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			System.out.println("Error setting system look and feel");
		}
		
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
		Clip openingClip					= DataStore.INSTANCE.getAudioClip("sounds/clockhomestart.wav");
		openingClip.start();
		
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
		try
		{
			OggClip bgMusic			= new OggClip(new FileInputStream("sounds/kicked.ogg"));
			bgMusic.loop();
			bgMusic.play();
		}
		catch(Exception e)
		{
			System.out.println("Error playing audio file");
		}
		
		// add key handler class
		addKeyListener(new KeyHandler());
		
		// request focus so we will get events without a click
		requestFocus();
		
		// accelerated graphics
		createBufferStrategy(2);
		strategy					= getBufferStrategy();
		
		title						= new TitleScreen(this);
		game						= new Game(this);
		menu						= new Menu(this);
	}
	
	/**
	 * The main game loop
	 */
	public void mainLoop()
	{
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
	
	/**
	 * Gets the instance of Game being run by ParkViewProtector
	 * 
	 * @return The instance of Game
	 */
	public Game getGame()
	{
		return game;
	}
	
	public void setGame(Game g)
	{
		game							= g;
		game.init(this);
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