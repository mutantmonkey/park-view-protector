/**
 * Class to handle everything associated with Student, Staff, and Cupple
 *
 * @author	Jamie of the Javateerz
 */

package org.javateerz.ParkViewProtector;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

public abstract class Character extends Movable
{
	// stats
	protected int hp;
	protected int maxHp;
	protected int hitDelay;
	
	private static final long serialVersionUID = 4L;
	
	/**
	 * Keeps track of items
	 * 
	 * Memory inquired
	 */
	public ItemBin bin;
	
	/**
	 * Constructor
	 * 
	 * @param g Instance of Game
	 * @param x
	 * @param y
	 * @param hp
	 * @param maxHp
	 * @param speed
	 */
	public Character(Game g, double x, double y, int hp, int maxHp, double speed)
	{
		super(g, x, y, speed);
		
		this.hp		= hp;
		this.maxHp	= maxHp;
		bin = new ItemBin(this);
	}
	
	/**
	 * @return Amount of HP the character has.
	 */
	public int getHp()
	{
		return hp;
	}
	
	/**
	 * Set the amount of HP.
	 * 
	 * @param amount
	 */
	public void setHp(int amount)
	{
		hp=amount;
	}
	
	/**
	 * Lowers time before the character can be hit again by amount.
	 * 
	 * @param amount
	 */
	public void decrementHitDelay(int amount)
	{
		if(hitDelay>0)
		{
			hitDelay-=amount;
		}
	}
	
	/**
	 * Sets time before the character can be hit again.
	 * 
	 * @param amount
	 */
	public void setHitDelay(int amount)
	{
		hitDelay=amount;
	}
	
	/**
	 * @return The time before the character can be hit again.
	 */
	public int getHitDelay()
	{
		return hitDelay;
	}
	
	/**
	 * @return If the character can be hit.
	 */
	public boolean isHittable()
	{
		if(hitDelay<=0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Decreases the HP by the specified amount
	 * 
	 * @param amount Amount of HP to subtract
	 * @return New amount of HP
	 */
	public int adjustHp(int amount)
	{
		hp		   -= amount;
		if(hp > maxHp)
		{
			hp = maxHp;
		}
		
		return hp;
	}
	
	/**
	 * Returns the maximum amount of HP the character can have
	 * 
	 * @return Amount of max HP
	 */
	public int getMaxHp()
	{
		return maxHp;
	}
	
	/**
	 * Drops the entire inventory
	 */
	public void dropInv()
	{
		bin.dropInv();
		//code to add the items to the field
	}
	
	/**
	 * Adds an item to the character's inventory
	 * 
	 * @param item Item to add
	 */
	public void pickItem(Item item)
	{
		bin.add(item);
		//code to remove from field...
	}
	
	/**
	 * Runs an item on the character used on, then removes it from the inventory
	 * 
	 * @param type Char
	 */
	public void useItem(char type)
	{
		bin.useItem(type);
	}
	
	/**
	 * @param students
	 * @return The students currently colliding with the character.
	 */
	public ArrayList<Character> getColliders(ArrayList<Student> students)
	{
		ArrayList<Character> list=new ArrayList<Character>();
		Character curr;
		for(int i=0; i<students.size(); i++)
		{
			curr=students.get(i);
			if(curr.getBounds().intersects(getBounds()))
			{
				list.add(curr);
			}
		}
		return list;
	}
	
	/**
	 * Loop through students, couples, and walls to see if the specified rectangle is
	 * available
	 * 
	 * @return
	 */
	public boolean canMove(Rectangle newRect)
	{
		// students
		ArrayList<Student> students=game.getStudents();
		if(students.size() > 0)
		{
			for(Student s : students)
			{
				if(!s.getStunned() && newRect.intersects(s.getBounds()))
				{
					if(!(s instanceof Student) || s!=this)
						return false;
				}
			}
		}
		
		// couples
		ArrayList<Cupple> couples=game.getCouples();
		if(couples.size() > 0)
		{
			for(Cupple c : couples)
			{
				if(!c.getStunned() && newRect.intersects(c.getBounds()))
				{
					if(!(c instanceof Cupple) || c!=this)
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
		
		return true;
	}
	
	public void push(Character other)
	{
		double	temp1=getSpeed(),
				temp2=other.getSpeed();
		
		setSpeed(getSpeed());
		other.setSpeed(getSpeed());
		
		switch(getDirection())
		{
			case Direction.NORTH:
				if(other.canMove(other.getNewBounds(0,-1)))
				{
					move(0,-1);
					other.move(0,-1);
				}
				break;
			case Direction.SOUTH:
				if(other.canMove(other.getNewBounds(0,1)))
				{
					move(0,1);
					other.move(0,1);
				}
				break;
			case Direction.EAST:
				if(other.canMove(other.getNewBounds(1,0)))
				{
					move(1,0);
					other.move(1,0);
				}
				break;
			case Direction.WEST:
				if(other.canMove(other.getNewBounds(-1,0)))
				{
					move(-1,0);
					other.move(-1,0);
				}
				break;
		}
		setSpeed(temp1);
		other.setSpeed(temp2);

	}
	
	protected void validateState()
	{
		super.validateState();
		
		if(hp > maxHp)
		{
			throw new IllegalArgumentException("HP cannot exceed max HP");
		}
	}
	
	/**
	 * Computes the bounding box for the object
	 * 
	 * @return Bounding box
	 */
	public Rectangle getBounds()
	{
		Rectangle rect = new Rectangle((int) x, (int) y+(sprite.getHeight()-sprite.getWidth())/2,
				sprite.getWidth(), sprite.getWidth());
		
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
		
		Rectangle bounds		= new Rectangle(newX, newY+(sprite.getHeight()-sprite.getWidth())/2, sprite.getWidth(),
				sprite.getWidth());
		
		return bounds;
	}
	
	/**
	 * Computes a new bounding box for the object moved the specified distance
	 * 
	 * @param distance
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
		
		Rectangle bounds		= new Rectangle(newX, newY+(sprite.getHeight()-sprite.getWidth())/2, sprite.getWidth(),
				sprite.getWidth());
		
		return bounds;
	}
}