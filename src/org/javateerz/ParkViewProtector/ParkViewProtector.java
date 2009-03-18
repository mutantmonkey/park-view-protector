/**
 * Park View Protector
 * 
 * This class is a subclass of Canvas so we can use accelerated graphics
 *
 * @author	Jamie of the Javateerz
 */

package org.javateerz.ParkViewProtector;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

import javax.sound.sampled.Clip;
import javax.swing.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import org.newdawn.easyogg.OggClip;

public class ParkViewProtector
{
	public static final int WIDTH			= 800;
	public static final int HEIGHT			= 600;
	
	// colors
	public static final Color COLOR_BG_1	= new Color(255, 0, 255);
	public static final Color COLOR_TEXT_1	= Color.white;
	public static final Color COLOR_BG_2	= Color.white;
	public static final Color COLOR_TEXT_2	= Color.black;
	
	public static final Color STATS_BAR_BG	= new Color(0f, 0f, 0f, 0.5f);
	public static final Color STATS_BAR_FG	= Color.white;
	public static final Color STATS_BAR_HP	= new Color(255, 0, 255);
	public static final Color STATS_BAR_TP	= new Color(0, 255, 0);
	
	private static final long serialVersionUID = 1L;
	
	protected JFrame window;
	protected JPanel contentPanel;
	
	private boolean running					= true;
	public static boolean showTitle			= true;
	public static boolean showMenu			= false;
	public static boolean showOptions		= false;
	
	// logos
	private Sprite jtzLogo;
	
	// graphics
	private Graphics g;
	private BufferStrategy strategy;
	
	private OggClip bgMusic;
	
	private TitleScreen title;
	private Game game;
	private Menu menu;
	private OptionsMenu optMenu;
	
	public ParkViewProtector()
	{
		try
		{
			// TODO: add fullscreen support
			
			Display.setTitle("Park View Protector");
			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
		// the ugly cursor must die
		Mouse.setGrabbed(true);
		
		// enable 2D textures
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		// we are drawing in 2D (sadly), so disable the depth test (there is no depth!)
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		
		GL11.glOrtho(0, WIDTH, HEIGHT, 0, -1, 1);
		
		// load logos
		jtzLogo						= DataStore.INSTANCE.getSprite("javateerslogo.png");
		
		// set the Swing look and feel to the system one so the file selector looks native
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			System.out.println("Error setting system look and feel");
		}
	}
	
	/**
	 * Show opening graphics
	 */
	public void showOpening()
	{
		// try to play "Clock Home Start" startup clip
		try
		{
			playSound("clockhomestart.wav");
		}
		catch(Exception e)
		{
			System.out.println("No clock home start!");
		}
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		// Javateerz logo
		jtzLogo.draw(WIDTH / 2 - jtzLogo.getWidth() / 2, HEIGHT / 2 - jtzLogo.getHeight() / 2);
		
		try{Thread.sleep(3000);}catch(Exception e){}
	}
	
	/**
	 * Initialize game
	 */
	public void init()
	{
		// try to play background music
		try
		{
			bgMusic					= new OggClip("kicked.ogg");
			bgMusic.setGain(Options.INSTANCE.getFloat("music_volume", 0.8f));
			bgMusic.loop();
		}
		catch(Exception e)
		{
			System.out.println("Error playing background music");
		}
		
		// add key handler class
		//addKeyListener(new Keyboard());
		
		// request focus so we will get events without a click
		//requestFocus();
		
		// accelerated graphics
		//createBufferStrategy(2);
		//strategy					= getBufferStrategy();
		
		title						= new TitleScreen(this);
		game						= new Game(this);
		menu						= new Menu(this);
		optMenu						= new OptionsMenu(this);
	}
	
	/**
	 * The main game loop
	 */
	public void mainLoop()
	{
		while(running)
		{
			// close requested?
			/*if(Display.isCloseRequested())
			{
				running				= false;
			}*/
			
			// clear screen
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			if(showTitle)
			{
				title.show();
			}
			else if(showOptions)
			{
				optMenu.show();
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
	
	/**
	 * Sets the instance of Game that will be run by ParkViewProtector
	 * 
	 * @param g An instance of Game
	 */
	public void setGame(Game g)
	{
		game							= g;
		game.init(this);
	}
	
	/**
	 * Update a float option
	 * 
	 * @param key
	 * @param value
	 */
	public void setFloat(String key, float value)
	{
		Options.INSTANCE.putFloat(key, value);
		
		if(key == "music_volume")
		{
			bgMusic.setGain(value);
		}
	}
	
	/**
	 * Play a sound
	 * 
	 * @param file File name
	 */
	public static void playSound(String file)
	{
		Clip soundClip		= DataStore.INSTANCE.getAudioClip(file);
		
		// TODO: add volume control
		//soundClip.setGain(Options.INSTANCE.getFloat("sfx_volume", 1.0f));
		
		soundClip.start();
	}
	
	/**
	 * Display an error message
	 * 
	 * @param msg Message to display
	 * @param fatal Should the program be terminated?
	 */
	public void error(String msg, boolean fatal)
	{
		System.out.println("Error: " + msg);
		
		JOptionPane.showMessageDialog(window, msg, "Error", JOptionPane.ERROR_MESSAGE);
		
		if(fatal)
		{
			System.exit(0);
		}
	}
	
	/**
	 * Display an error message and terminates the program
	 * 
	 * @param msg Message to display
	 */
	public static void error(String msg)
	{
		System.out.println("Fatal error: " + msg);
		
		JOptionPane.showMessageDialog(null, msg, "Park View Protector Error", JOptionPane.ERROR_MESSAGE);
		
		System.exit(0);
	}
	
	public void quit()
	{
		running							= false;
		
		bgMusic.stop();
		
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