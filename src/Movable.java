/**
 * Class to handle everything associated with things that can move including characters and
 * attacks
 *
 * @author	Jamie of the Javateerz
 * @serial
 */

import java.io.Serializable;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Movable implements Serializable
{
	protected double speed;
	
	// placement
	protected double x;
	protected double y;
	
	// direction (0 = north, 1 = east, 2 = south, 3 = west)
	protected int direction		= 2;
	
	protected transient Sprite sprite;
	protected int moveCount;
	
	protected int stunFrames	= 0;
	
	private static final long serialVersionUID = 2L;
	
	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param speed
	 */
	public Movable(double x, double y, double speed)
	{
		this.x		= x;
		this.y		= y;
		
		this.speed	= speed;
		
		this.sprite	= DataStore.INSTANCE.getSprite("placeholder.png");
	}
	
	/**
	 * Returns the speed of the object
	 * 
	 * @return Speed
	 */
	public double getSpeed()
	{
		return speed;
	}
	
	/**
	 * Changes the speed of the object
	 * 
	 * @param change New speed
	 */
	public void changeSpeed(double change)
	{
		speed = change;
	}
	
	/**
	 * Moves the object a distance (which is multiplied by speed)
	 * 
	 * @param distX X component
	 * @param distY Y component
	 */
	public void move(int distX, int distY)
	{
		// are we stunned?
		if(stunFrames > 0)
		{
			stunFrames--;
			return;
		}
		
		// determine and change direction if necessary
		if(distY > 0)	direction	= Direction.SOUTH;
		if(distX < 0)	direction	= Direction.WEST;
		if(distY < 0)	direction	= Direction.NORTH;
		if(distX > 0)	direction	= Direction.EAST;
		
		System.out.println("Direction: " + direction);
		
		x		   += distX * speed;
		y		   += distY * speed;
		
		moveCount++;
	}
	
	/**
	 * Moves the object a distance (which is multiplied by speed) in the current direction
	 * 
	 * @param dist distance
	 */
	public void move(int distance)
	{
		// are we stunned?
		if(stunFrames > 0)
		{
			stunFrames--;
			return;
		}
		
		double dist		= distance * speed;

		// determine and change direction if necessary
		switch(direction)
		{
			case Direction.NORTH:
				y		-= dist;
				break;
			
			case Direction.EAST:
				x		+= dist;
				break;
				
			case Direction.SOUTH:
				y		+= dist;
				break;
			
			case Direction.WEST:
				x		-= dist;
				break;
		}
			
		moveCount++;
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
	 * Changes the direction that the object is facing
	 * 
	 * @param int direction
	 */
	public void setDirection(int dir)
	{
		direction	= dir;
	}
	
	/**
	 * Returns the direction that the object is facing
	 * 
	 * @return
	 */
	public int getDirection()
	{
		return direction;
	}
	
	/**
	 * Returns the number of moves made by the object
	 * 
	 * @return
	 */
	public int getMoveCount()
	{
		return moveCount;
	}
	
	/**
	 * Resets the move counter of the object
	 */
	public void resetMoveCount()
	{
		moveCount	= 0;
	}
	
	/**
	 * Stuns an object for the number of frames specified
	 */
	public void stun(int frames)
	{
		stunFrames				= frames;
	}
	
	/**
	 * Is the object stunned?
	 * 
	 * @return
	 */
	public boolean getStunned()
	{
		return (stunFrames > 0);
	}
	
	/**
	 * Updates the sprite; this is called by validateState() to ensure
	 * that the sprite is updated after a game is loaded
	 */
	protected abstract void updateSprite();
	
	/**
	 * Ensures that the object is in a usable state after loading a
	 * game
	 */
	protected void validateState()
	{
		updateSprite();
	}
	
	/**
	 * Called by main game loop, draws the object's sprite on the screen
	 */
	public void draw(Graphics g)
	{
		sprite.draw(g, (int) x, (int) y);
	}
	
	/**
	 * Computes the bounding box for the object
	 * 
	 * @return Bounding box
	 */
	public Rectangle getBounds()
	{
		Rectangle rect			= new Rectangle((int) x, (int) y, sprite.getWidth(),
				sprite.getHeight());
		
		return rect;
	}
	
	/**
	 * Computes a new bounding box for the object moved the specified distance
	 * 
	 * @param distX X component
	 * @param distY Y component
	 * @return New bounding box
	 */
	public Rectangle getNewBounds(int distX, int distY)
	{
		int newX				= (int) x;
		int newY				= (int) y;
		
		newX				   += (int) (distX * speed);
		newY				   += (int) (distY * speed);
		
		Rectangle bounds		= new Rectangle(newX, newY, sprite.getWidth(),
				sprite.getHeight());
		
		return bounds;
	}
	
	/**
	 * Computes a new bounding box for the object moved the specified distance
	 * 
	 * @param distX X component
	 * @param distY Y component
	 * @return New bounding box
	 */
	public Rectangle getNewBounds(int distance)
	{
		int newX				= (int) x;
		int newY				= (int) y;
		
		int dist				= (int) (distance * speed);
		
		// determine and change direction if necessary
		switch(direction)
		{
			case Direction.NORTH:
				newY	-= dist;
				break;
			
			case Direction.EAST:
				newX	+= dist;
				break;
				
			case Direction.SOUTH:
				newY	+= dist;
				break;
			
			case Direction.WEST:
				newX	-= dist;
				break;
		}
		
		Rectangle bounds		= new Rectangle(newX, newY, sprite.getWidth(),
				sprite.getHeight());
		
		return bounds;
	}
}

class Direction
{
	public static final int NORTH	= 0;
	public static final int EAST	= 1;
	public static final int SOUTH	= 2;
	public static final int WEST	= 3;
}