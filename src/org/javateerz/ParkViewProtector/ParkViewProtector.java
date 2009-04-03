/**
 * Park View Protector
 * 
 * This class is a subclass of Canvas so we can use accelerated graphics
 *
 * @author	Jamie of the Javateerz
 */

package org.javateerz.ParkViewProtector;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import org.newdawn.slick.Color;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class ParkViewProtector
{
	public static final int WIDTH			= 800;
	public static final int HEIGHT			= 600;
	
	// colors
	public static final Color COLOR_BG_1	= new Color(255, 0, 255);
	public static final Color COLOR_TEXT_1	= new Color(255, 255, 255);
	public static final Color COLOR_BG_2	= new Color(255, 255, 255);
	public static final Color COLOR_TEXT_2	= new Color(0, 0, 0);
	
	public static final Color STATS_BAR_BG	= new Color(0, 0, 0, 127);
	public static final Color STATS_BAR_FG	= new Color(255, 255, 255);
	public static final Color STATS_BAR_HP	= new Color(255, 0, 255);
	public static final Color STATS_BAR_TP	= new Color(0, 255, 0);
	
	private static final long serialVersionUID = 1L;
	
	private boolean fullscreen				= true;
	
	private boolean running					= true;
	public static boolean showTitle			= true;
	public static boolean showMenu			= false;
	public static boolean showOptions		= false;
	
	// logos
	private Sprite jtzLogo;
	
	private Music bgMusic;
	
	private TitleScreen title;
	private Game game;
	private Menu menu;
	private OptionsMenu optMenu;
	
	public ParkViewProtector()
	{
		try
		{
			setDisplayMode();
			
			if(fullscreen)
			{
				Display.setFullscreen(true);
			}
			
			Display.setTitle("Park View Protector");
			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
		if(fullscreen)
		{
			// hide mouse cursor
			Mouse.setGrabbed(true);
		}
		
		// disable 3D depth test
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		// set clear color to white
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		// enable transparency
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
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
	 * Select a display mode
	 * 
	 * @return True if an acceptable display mode was found
	 */
	public boolean setDisplayMode()
	{
		try
		{
			DisplayMode[] modes				= Display.getAvailableDisplayModes();
		
			for(DisplayMode mode : modes)
			{
				if(mode.getWidth() == WIDTH && mode.getHeight() == HEIGHT)
				{
					Display.setDisplayMode(mode);
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
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
		
		// show rendered content
		GL11.glFlush();
		Display.update();
		
		try{Thread.sleep(3000);}catch(Exception e){}
	}
	
	/**
	 * Initialize game
	 */
	public void init()
	{
		//setMusic("heavyset.ogg");
		
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
			if(Display.isCloseRequested())
			{
				running				= false;
			}
			
			// clear screen
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			
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
				// escape quits
				if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
				{
					running			= false;
				}
				
				game.show();
			}
			
			// show rendered content
			GL11.glFlush();
			Display.update();
		}
		
		quit();
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
			bgMusic.setVolume(value);
		}
	}
	
	/**
	 * Play a sound
	 * 
	 * @param file File name
	 */
	public static void playSound(String file) throws SlickException
	{
		Sound sound							= new Sound(file);
		
		sound.play(1.0f, Options.INSTANCE.getFloat("sfx_volume", 1.0f));
	}
	
	/**
	 * Change the background music
	 * 
	 * @param file File name
	 */
	public void setMusic(String file)
	{
		// try to play background music
		try
		{
			bgMusic							= new Music(file);
			bgMusic.setVolume(Options.INSTANCE.getFloat("music_volume", 0.8f));
			bgMusic.loop();
		}
		catch(Exception e)
		{
			System.out.println("Error playing background music");
		}
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
		
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
		
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
	
	public void quitGame()
	{
		running							= false;
	}

	private void quit()
	{
		running							= false;
		
		bgMusic.stop();
		
		Display.destroy();
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