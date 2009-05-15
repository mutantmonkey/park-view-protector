package org.javateerz.ParkViewProtector;

import java.util.ArrayList;

import org.javateerz.EasyGL.GLString;
import org.newdawn.slick.geom.Rectangle;

public class StatusIcon
{
protected Game game;
	
	protected double speed;
	
	// placement
	protected double x;
	protected double y;
	
	protected int status;
	protected int index;
	protected Staff player;
	protected ArrayList<StatusIcon> icons;
	
	protected transient Sprite sprite;
	
	private static final long serialVersionUID = 2L;
	
	/**
	 * Constructor
	 * 
	 * @param game Instance of Game
	 * @param x
	 * @param y
	 * @param speed
	 */
	public StatusIcon(Game game, double x, double y, int status)
	{
		this.game	= game;
		
		this.x		= x;
		this.y		= y;
		
		this.status = status;
		this.player	= game.getPlayer();
		icons		= game.getIcons();
		index		= game.getIcons().indexOf(this);
		
		this.sprite	= DataStore.INSTANCE.getSprite("placeholder.png");
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
	public void draw()
	{
		sprite.draw((int) x + index * sprite.getWidth(), (int) y);
		int count=0;
		if(status==Status.STUN)
			count=player.getStunFrames();
		else if(status==Status.INVULNERABLE)
			count=player.getInvulFrames();
		
		GLString frames = new GLString(count+"");
		frames.draw((int) x + index * sprite.getWidth(), (int) y);
	}

	/**
	 * Updates the sprite; this is called by validateState() to ensure
	 * that the sprite is updated after a game is loaded
	 */
	protected void updateSprite()
	{
		sprite	= DataStore.INSTANCE.getSprite("placeholder.png");
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
