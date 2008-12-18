/**
 * Class to handle everything associated with things that can move including characters and
 * attacks
 *
 * @author	Jamie of the Javateerz
 */

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Movable
{
	protected double speed;
	
	// placement
	protected int x;
	protected int y;
	
	// direction (0 = north, 1 = east, 2 = south, 3 = west)
	protected int direction		= 2;
	
	protected Sprite sprite;
	protected int moveCount;
	
	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param speed
	 */
	public Movable(int x, int y, double speed)
	{
		this.x		= x;
		this.y		= y;
		
		this.speed	= speed;
		
		this.sprite	= DataStore.INSTANCE.getSprite("images/placeholder.png");
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
		// determine and change direction if necessary
		switch(direction)
		{
			// NORTH
			default:
				if(distY > 0)	direction	= 3;
				break;
			
			// EAST
			case 1:
				if(distX < 0)	direction	= 3;
				break;
				
			// SOUTH
			case 2:
				if(distY < 0)	direction	= 0;
				break;
			
			// WEST
			case 3:
				if(distX < 0)	direction	= 1;
				break;
		}
		
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
		// determine and change direction if necessary
		switch(direction)
		{
			// NORTH
			default:
				y		-= distance * speed;
				break;
			
			// EAST
			case 1:
				x		+= distance * speed;
				break;
				
			// SOUTH
			case 2:
				y		+= distance * speed;
				break;
			
			// WEST
			case 3:
				x		-= distance * speed;
				break;
		}
		
		moveCount++;
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
	 * Called by main game loop, draws the object's sprite on the screen
	 */
	public void draw(Graphics g)
	{
		sprite.draw(g, x, y);
	}
	
	/**
	 * Gets the bounds of the object
	 * 
	 * @return Bounding box
	 */
	public Rectangle getBounds()
	{
		Rectangle rect			= new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
		
		return rect;
	}
}