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

import org.javateerz.ParkViewProtector.Bosses.Boss;
import org.javateerz.ParkViewProtector.Staff.Staff;
import org.javateerz.ParkViewProtector.Students.Student;
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
		setDirection(distX, distY);
		
		move(distX, distY, 0);
	}
	
	/**
	 * Moves the object a distance (which is multiplied by speed)
	 * 
	 * @param distX	x distance
	 * @param distY	x distance
	 */
	public void move(int distX, int distY, int any)
	{
		float delta			= ParkViewProtector.getRenderDeltas();
		
		x				   += distX * speed * delta;
		y				   += distY * speed * delta;
		
		incrementMoveCount();
	}
	
	/**
	 * Moves the object a distance (which is multiplied by speed) in the current direction
	 * 
	 * @param dist distance
	 */
	public void move(int dist)
	{
		float delta			= ParkViewProtector.getRenderDeltas();
		
		double distance		= dist * speed * delta;

		// determine and change direction if necessary
		switch(direction)
		{
			case Direction.NORTH:
				y		   -= distance;
				break;
			
			case Direction.EAST:
				x		   += distance;
				break;
				
			case Direction.SOUTH:
				y		   += distance;
				break;
			
			case Direction.WEST:
				x		   -= distance;
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
	
	public void moveToward(Movable obj, int i)
	{
		int distX				= 0,
			distY				= 0;
		
		// increase the bounding box for testing
		Rectangle testBounds	= this.getBounds();
		testBounds.grow(i, i);
		
		// make sure we actually need to move
		if(testBounds.intersects(obj.getBounds()))
		{
			return;
		}
		
		// compute X distance
		if(this.getBounds().getCenterX() > obj.getBounds().getCenterX() &&
				canMove(getNewBounds(-Game.MOVE_SPEED, 0)))
		{
			distX				   -= Game.MOVE_SPEED;
		}
		else if(this.getBounds().getCenterX() < obj.getBounds().getCenterX() &&
				canMove(getNewBounds(Game.MOVE_SPEED, 0)))
		{
			distX				   += Game.MOVE_SPEED;
		}
		
		if(Math.abs(this.getBounds().getCenterX() - obj.getBounds().getCenterX()) < speed)
		{
			distX					= 0;
		}
		
		// compute Y distance
		if(this.getBounds().getCenterY() > obj.getBounds().getCenterY() &&
				canMove(getNewBounds(0, -Game.MOVE_SPEED)))
		{
			distY				   -= Game.MOVE_SPEED;
		}
		else if(this.getBounds().getCenterY() < obj.getBounds().getCenterY() &&
				canMove(getNewBounds(0, Game.MOVE_SPEED)))
		{
			// obj lies below
			distY				   += Game.MOVE_SPEED;
		}
		
		if(Math.abs(this.getBounds().getCenterY() - obj.getBounds().getCenterY()) < speed)
		{
			distY					= 0;
		}
		
		move(distX, distY);
	}

	public int getDirectionToward(Movable obj)
	{
		int direct=Direction.NORTH;
		int distX=(int) (obj.getBounds().getCenterX()-getBounds().getCenterX()),
			distY=(int) (obj.getBounds().getCenterY()-getBounds().getCenterY());
		
		if(Math.abs(distX) > Math.abs(distY))
		{
			if(distX > 0)
			{
				direct=Direction.EAST;
			}
			else if(distX < 0)
			{
				direct=Direction.WEST;
			}
		}
		else
		{

			if(distY > 0)
			{
				direct=Direction.SOUTH;
			}
			else if(distY < 0)
			{
				direct=Direction.NORTH;
			}
		}
		return direct;
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
	 * Changes the direction that the object is facing given an X distance and and a Y
	 * distance
	 * 
	 * @param distX x distance
	 * @param distY y distance
	 */
	public void setDirection(int distX, int distY)
	{
		if(distY > 0)
			direction	= Direction.SOUTH;
		
		if(distX < 0)
			direction	= Direction.WEST;
		
		if(distY < 0)
			direction	= Direction.NORTH;
		
		if(distX > 0)
			direction	= Direction.EAST;
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
		
		newX				   += (int) (distX * speed *
				ParkViewProtector.getRenderDeltas());
		newY				   += (int) (distY * speed *
				ParkViewProtector.getRenderDeltas());
		
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
		
		int dist				= (int) (distance * speed *
				ParkViewProtector.getRenderDeltas());
		
		// determine and change direction if necessary
		switch(direction)
		{
			case Direction.NORTH:
				newY		   -= dist;
				break;
			
			case Direction.EAST:
				newX		   += dist;
				break;
				
			case Direction.SOUTH:
				newY		   += dist;
				break;
			
			case Direction.WEST:
				newX		   -= dist;
				break;
		}
		
		Rectangle bounds		= new Rectangle(newX, newY, sprite.getWidth(),
				sprite.getHeight());
		
		return bounds;
	}
	
	public boolean canMove(Rectangle newRect)
	{
		// can never move off the screen
		if(newRect.getX() < 0 || newRect.getX() >= ParkViewProtector.WIDTH ||
				newRect.getY() < 0 || newRect.getY() >= ParkViewProtector.HEIGHT)
		{
			return false;
		}
		
		// students
		ArrayList<Student> students = game.getStudents();
		if(students.size() > 0)
		{
			for(Student s : students)
			{
				if(!s.isStunned() && newRect.intersects(s.getBounds()))
				{
					if(/*!(s instanceof Student) || */s!=this)
						return false;
				}
			}
		}
		
		// couples
		ArrayList<Couple> couples = game.getCouples();
		if(couples.size() > 0)
		{
			for(Couple c : couples)
			{
				if(!c.isStunned() && newRect.intersects(c.getBounds()))
				{
					if(/*!(c instanceof Couple) || */c!=this)
						return false;
				}
			}
		}
			
		// walls
		ArrayList<Wall> walls=game.getWalls();
		if(walls.size() > 0)
		{
			for(Wall w : walls)
			{
				if(newRect.intersects(w.getBounds()))
				{
					return false;
				}
			}
		}
		
		if(game.isBossLevel() && newRect.intersects(game.getBoss().getBounds()))
		{
			if(!(this instanceof Boss))
				return false;
		}
		
		return true;
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
	
	public boolean inRange(Movable obj, int i)
	{
		Rectangle testBounds	= this.getBounds();
		testBounds.grow(i, i);
		
		if(testBounds.intersects(obj.getBounds()))
		{
			return true;
		}
		return false;
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