package org.javateerz.EasyGL;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class EasyGL
{
	public static final int WIDTH			= 800;
	public static final int HEIGHT			= 600;
	
	private boolean running				= true;
	
	public void init()
	{
		try
		{
			setDisplayMode();
			
			Display.setTitle("EasyGL");
			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
		// the ugly cursor must die
		//Mouse.setGrabbed(true);
		
		// enable 2D textures and disable 3D depth test
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		// set clear color to white
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		// enable transparency
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		
		GL11.glOrtho(0, WIDTH, HEIGHT, 0, -1, 1);
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
	
	public void loop()
	{
		while(running)
		{
			// close requested?
			if(Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			{
				running				= false;
			}
			
			// clear screen
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			
			GLRect testRect2		= new GLRect(70, 70, 300, 300);
			testRect2.setColor(Color.cyan);
			testRect2.draw();
			
			GLRect testRect			= new GLRect(30, 30, 100, 100);
			testRect.setColor(new Color(255, 0, 255, 175));
			testRect.draw();
			
			GLString musti			= new GLString("MUSTAFA", 20, 20);
			musti.setColor(new Color(120, 255, 0));
			musti.setFont(new TrueTypeFont(new java.awt.Font("System",
					java.awt.Font.PLAIN, 42), false));
			
			musti.draw();
			
			GL11.glFlush();
			Display.update();
			
			// slow down
			try
			{
				Thread.sleep(100);
			}
			catch(Exception e)
			{
				
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		EasyGL test				= new EasyGL();
		test.init();
		test.loop();
	}

}
