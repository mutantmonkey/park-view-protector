/**
 * Class to handle everything associated with things that can move including characters and
 * attacks
 *
 * @author	Jamie of the Javateerz
 * @serial
 */

package org.javateerz.ParkViewProtector;

import java.io.Serializable;
import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

public abstract class Movable implements Serializable
{
	protected Game game;
	
	protected double speed;
	
	// placement
	protected double x;
	protected double y;
	
	// direction (0 = north, 1 = east, 2 = south, 3 = west)
	protected int direction		= 2;
	
	protected transient Sprite sprite;
	protected int moveCount;
	
	private static final long serialVersionUID = 2L;
	
	/**
	 * Constructor
	 * 
	 * @param game Instance of Game
	 * @param x
	 * @param y
	 * @param speed
	 */
	public Movable(Game game, double x, double y, double speed)
	{
		this.game	= game;
		
		this.x		= x;
		this.y		= y;
		
		this.speed	= speed;
		
		this.sprite	= DataStore.INSTANCE.getSprite("placeholder.png");
	}
	
	/**
	 * Moves the object a distance (which is multiplied by speed)
	 * 
	 * @param distX	x distance
	 * @param distY	x distance
	 */
	public void move(int distX, int distY)
	{
		// determine and change direction if necessary
		if(distY > 0)	direction	= Direction.SOUTH;
		if(distX < 0)	direction	= Direction.WEST;
		if(distY < 0)	direction	= Direction.NORTH;
		if(distX > 0)	direction	= Direction.EAST;
		
		x		   += distX * speed;
		y		   += distY * speed;
		
		incrementMoveCount();
	}
	
	/**
	 * Moves the object a distance (which is multiplied by speed) in the current direction
	 * 
	 * @param dist distance
	 */
	public void move(int dist)
	{
		double distance		= dist * speed;

		// determine and change direction if necessary
		switch(direction)
		{
			case Direction.NORTH:
				y		-= distance;
				break;
			
			case Direction.EAST:
				x		+= distance;
				break;
				
			case Direction.SOUTH:
				y		+= distance;
				break;
			
			case Direction.WEST:
				x		-= distance;
				break;
		}
			
		incrementMoveCount();
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
	 * @param student
	 * @return If the character intersects specified student
	 */
	public boolean intersects(Student student)
	{
		ArrayList<Student> students=game.getStudents();
		for(Student s : students)
		{
			if(s.getBounds().intersects(getBounds()) && s==this)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return The students that collided with the character
	 */
	public ArrayList<Student> getCollidedStudents()
	{
		ArrayList<Student> students=game.getStudents();
		ArrayList<Student> colliders=new ArrayList<Student>();
		for(Student s : students)
		{
			if(this.intersects(s))
			{
				colliders.add(s);
			}
		}
		return colliders;
	}
	
	/**
	 * @param couple
	 * @return If the character intersects specified couple
	 */
	public boolean intersects(Couple couple)
	{
		ArrayList<Couple> couples=game.getCouples();
		for(Couple c : couples)
		{
			if(c.getBounds().intersects(getBounds()) && c==this)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return The couples that collided with the character
	 */
	public ArrayList<Couple> getCollidedCouples()
	{
		ArrayList<Couple> couples=game.getCouples();
		ArrayList<Couple> colliders=new ArrayList<Couple>();
		for(Couple c : couples)
		{
			if(this.intersects(c))
			{
				colliders.add(c);
			}
		}
		return colliders;
	}
	
	/**
	 * @param player
	 * @return If the character intersects the player
	 */
	public boolean intersects(Staff player)
	{
		Staff curr=game.getPlayer();
		if(curr.getBounds().intersects(getBounds()) && curr==this)
		{
				return true;
		}
		return false;
	}
	
	// TODO: Add a will intersect (getNewBounds() intersects obj, return true;)
	/**public boolean willIntersect(Movable obj)
	{
		if(obj instanceof Student)
			
		return false;
	}*/
	
	/**
	 * @return Returns the speed of the object
	 */
	public double getSpeed()
	{
		return speed;
	}

	/**
	 * Changes the speed of the object
	 * 
	 * @param amount New speed
	 */
	public void setSpeed(double amount)
	{
		speed = amount;
	}

	/**
	 * @return The direction that the object is facing
	 */
	public int getDirection()
	{
		return direction;
	}

	/**
	 * Changes the direction that the object is facing
	 * 
	 * @param direction New direction
	 */
	public void setDirection(int dir)
	{
		direction	= dir;
	}
	
	/**
	 * @return The number of moves made by the object
	 */
	public int getMoveCount()
	{
		return moveCount;
	}
	
	/**
	 * Increases move counter by 1
	 */
	public void incrementMoveCount()
	{
		moveCount++;
	}
	
	/**
	 * Resets the move counter of the object
	 */
	public void resetMoveCount()
	{
		moveCount	= 0;
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
	 * @param distX X component
	 * @param distY Y component
	 * @return The new bounding box of the object if it is moved the specified distance
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
	 * @param distance
	 * @return The new bounding box of the object if it is moved the specified distance
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

	/**
	 * @return The index of the wall that is closest to the object
	 */
	public int findNearestWall()
	{
		Wall nearestWall			= null;
		int smallestDist			= Integer.MAX_VALUE;
		int distX, distY, dist;
		ArrayList<Wall> walls= game.getWalls();
		
		for(Wall wall : walls)
		{
			// compute X distance
			if(wall.getBounds().getX() <= getBounds().getX())
			{
				// wall lies to the left
				distX					= (int) (getBounds().getX() - wall.getBounds().getX());
			}
			else {
				// wall lies to the right
				distX					= (int) (wall.getBounds().getX() - getBounds().getX());
			}
			
			// compute Y distance
			if(wall.getBounds().getY() <= getBounds().getY())
			{
				// wall lies above
				distY					= (int) (getBounds().getY() - wall.getBounds().getY());
			}
			else {
				// wall lies below
				distY					= (int) (wall.getBounds().getY() - getBounds().getY());
			}
			
			// compute distance between wall and object
			dist						= (int) Math.sqrt(distX * distX + distY * distY);
			
			if(dist < smallestDist)
			{
				nearestWall				= wall;
				smallestDist			= dist;
			}
		}
		
		return walls.indexOf(nearestWall);
	}

	/**
	 * Called by main game loop, draws the object's sprite on the screen
	 */
	public void draw()
	{
		sprite.draw((int) x, (int) y);
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
}


/**
 * Direction variables
 */
class Direction
{
	public static final int NORTH	= 0;
	public static final int EAST	= 1;
	public static final int SOUTH	= 2;
	public static final int WEST	= 3;
}