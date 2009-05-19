package org.javateerz.ParkViewProtector;

import java.io.Serializable;

import org.javateerz.EasyGL.GLString;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

public class StatusIcon implements Serializable
{
	protected Game game;
	
	protected double speed;
	
	// placement
	protected double x;
	protected double y;
	
	public static final int X_OFFSET	= 1;
	public static final int Y_OFFSET	= -2;
	
	protected int status;
	protected Staff player;
	
	protected int time;
	
	protected Font font = new TrueTypeFont(new java.awt.Font("Monospaced",
			java.awt.Font.PLAIN, 10), false);
	
	protected transient Sprite sprite;
	
	private static final long serialVersionUID = 2L;
	
	/**
	 * Constructor
	 * 
	 * @param game Instance of Game
	 * @param x
	 * @param y
	 * @param status
	 */
	public StatusIcon(Game game, int status)
	{
		this.game	= game;
		
		this.status = status;
		this.player	= game.getPlayer();
		setTime();
		this.sprite	= DataStore.INSTANCE.getSprite("status/"+status+".png");
	}
	
	/**
	 * Move the object to a location
	 * 
	 * @param x
	 * @param y
	 */
	public void moveTo(double x, double y)
	{
		this.x			= x;
		this.y			= y;
	}
	
	/**
	 * @return The x component
	 */
	public double getX()
	{
		return x;
	}

	/**
	 * @return The y component
	 */
	public double getY()
	{
		return y;
	}
	
	public int getStatus()
	{
		return status;
	}
	
	public void setTime()
	{
		if(status==Status.INVULNERABLE && player.getInvulHit())
		{
			time=player.getInvulFrames();
		}
		else if(status==Status.STUN)
		{
			time=player.getStunFrames();
		}
	}
	
	public boolean isOver()
	{
		if(time<=0)
			return true;
		return false;
	}
	
	/**
	 * @return The bounding box of the object
	 */
	public Rectangle getBounds()
	{
		Rectangle rect			= new Rectangle((int) x, (int) y, sprite.getWidth(),
				sprite.getHeight());
		
		return rect;
	}
	
	/**
	 * Called by main game loop, draws the object's sprite on the screen
	 */
	public void draw(int x, int y)
	{
		sprite.draw(x, y);
		
		GLString frames = new GLString(time+"");
		frames.setColor(Color.yellow);
		frames.setFont(font);
		frames.draw(x+X_OFFSET, y+Y_OFFSET);
	}

	/**
	 * Updates the sprite; this is called by validateState() to ensure
	 * that the sprite is updated after a game is loaded
	 */
	protected void updateSprite()
	{
		try
		{
			sprite	= DataStore.INSTANCE.getSprite("status/"+ status +".png");
		}
		catch(Exception e)
		{
			sprite = DataStore.INSTANCE.getSprite("status/testStatus.png");
		}
	}

	/**
	 * Ensures that the object is in a usable state after loading a
	 * game
	 */
	protected void validateState()
	{
		updateSprite();
	}
}
