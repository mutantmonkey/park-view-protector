/**
 * Class to handle everything associated with Student, Staff, and Couple
 *
 * @author	Jamie of the Javateerz
 */

package org.javateerz.ParkViewProtector;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

public abstract class Character extends Movable
{
	private static final long serialVersionUID = 4L;
	protected int hp;
	protected int maxHp;
	
	protected int invulFrames	= 0;
	protected int stunFrames	= 0;
	protected int attackFrames	=0;
	
	protected boolean invul		= false;
	protected boolean stunned	= false;
	protected boolean attacking	= false;
	
	//	Keeps track of itemsMemory inquired
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
	 * Moves the character a distance if it is able to; 
	 * Character will not move if stunned or attacking
	 * 
	 * @param distX	X distance
	 * @param distY	Y distance
	 */
	public void move(int distX, int distY)
	{
		if(isStunned())
		{
			return;
		}
		super.move(distX, distY);
	}
	
	/**
	 * Moves the character a distance in the current direction if it is able to; 
	 * Character will not move if stunned or attacking
	 * 
	 * @param distX	X distance
	 * @param distY	Y distance
	 */
	public void move(int dist)
	{
		if(isStunned() || isAttacking())
		{
			return;
		}
		super.move(dist);
	}
	
	/**
	 * @return The HP of the character
	 */
	public int getHp()
	{
		return hp;
	}
	
	/**
	 * Sets HP of the character
	 * 
	 * @param amount
	 */
	public void setHp(int amount)
	{
		hp=amount;
	}
	
	/**
	 * Decrements the amount of frames for all status effects the character has
	 */
	public void recover()
	{
		if(invulFrames>0)
			invulFrames--;
		if(stunFrames>0)
			stunFrames--;
		if(attackFrames>0)
			attackFrames--;
		
		if(invulFrames<=0)
			invul=false;
		if(stunFrames<=0)
			stunned=false;
		if(attackFrames<=0)
			attacking=false;
	}
	
	/**
	 * Sets time for character to be invulnerable
	 * 
	 * @param amount
	 */
	public void setInvulFrames(int amount)
	{
		invulFrames=amount;
		if(amount>0)
			invul=true;
	}
	
	/**
	 * Sets time for character to be stunned
	 * 
	 * @param amount
	 */
	public void setStunFrames(int amount)
	{
		stunFrames=amount;
		if(amount>0)
			stunned=true;
	}
	
	/**
	 * Sets time for character to be attacking
	 * 
	 * @param amount
	 */
	public void setAttackFrames(int amount)
	{
		attackFrames=amount;
		if(amount>0)
			attacking=true;
	}
	
	/**
	 * @return If the character is vulnerable
	 */
	public boolean isVulnerable()
	{
		return !invul;
	}
	
	/**
	 * @return If the character is stunned
	 */
	public boolean isStunned()
	{
		return stunned;
	}
	
	/**
	 * @return If the character is attacking
	 */
	public boolean isAttacking()
	{
		return attacking;
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
	 * @return The maximum HP the character can have
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
	
	public boolean canMove(Rectangle newRect)
	{
		// students
		ArrayList<Student> students = game.getStudents();
		if(students.size() > 0)
		{
			for(Student s : students)
			{
				if(!s.isStunned() && newRect.intersects(s.getBounds()))
				{
					if(!(s instanceof Student) || s!=this)
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
					if(!(c instanceof Couple) || c!=this)
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
	/**
	 * @return If the character can move into the new specified rectangle
	 */
	/*public boolean canMove(Rectangle newRect)
	{
		// students
		ArrayList<Student> students=getCollidedStudents();
		if(students.size()!=0)
			for(Student s : students)
			{
				if(!s.isStunned())
					return false;
			}
		
		// couples
		ArrayList<Couple> couples=getCollidedCouples();
		if(couples.size()!=0)
			for(Couple s : couples)
			{
				if(!s.isStunned())
					return false;
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
	}*/

	
	/**
	 * Causes c1 to push c2 away.
	 * 
	 * @param c1
	 * @param c2
	 */
	/* FIXME: Yeah, I have no idea how to fix this.
	 * 			IT's BROKEN!
	 * 
	 * 		Does not work when an object is colliding with 2 players! D:
	 */
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

	
	/**
	 * Random movement
	 * 
	 * @param Movable Object to move
	 */
	public void moveRandom(Movable obj)
	{
		int speed					= Game.MOVE_SPEED;
		int changeMoves				= (int) (Math.random() * (Game.MAX_NUM_MOVES - Game.MIN_NUM_MOVES) +
				Game.MIN_NUM_MOVES + 1);
		
		// change direction if the move count exceeds the number of moves to change after
		if(obj.getMoveCount() <= 0 || obj.getMoveCount() > changeMoves)
		{
			// choose a new direction
			obj.setDirection((int) (Math.random() * 4));
			obj.resetMoveCount();
		}
		
		// change direction if we hit the top or bottom
		if(obj.getBounds().getY() <= 0 && obj.getDirection() == Direction.NORTH)
		{
			while(obj.getDirection()==Direction.NORTH)
				obj.setDirection((int) (Math.random() * 4));
			obj.resetMoveCount();
		}
		else if(obj.getBounds().getY() >= ParkViewProtector.HEIGHT - obj.getBounds().getHeight()  &&
				obj.getDirection() == Direction.SOUTH)
		{
			while(obj.getDirection()==Direction.SOUTH)
				obj.setDirection((int) (Math.random() * 4));
			obj.resetMoveCount();
		}
		else if(obj.getBounds().getX() <= 0 && obj.getDirection() == Direction.WEST)
		{
			while(obj.getDirection()==Direction.WEST)
				obj.setDirection((int) (Math.random() * 4));
			obj.resetMoveCount();
		}
		else if(obj.getBounds().getX() >= ParkViewProtector.WIDTH - obj.getBounds().getWidth() &&
				obj.getDirection() == Direction.EAST)
		{
			while(obj.getDirection()==Direction.EAST)
				obj.setDirection((int) (Math.random() * 4));
			obj.resetMoveCount();
		}
		
		// check for collisions
		if(obj.getNewBounds(speed).intersects(game.getPlayer().getBounds())
				|| !((Character) obj).canMove(obj.getNewBounds(speed)))
		{
			// collision, must choose new direction
			
			if(obj instanceof Character && obj.getNewBounds(speed).intersects(game.getPlayer().getBounds()))
			{
				((Character) obj).push(game.getPlayer());
				obj.incrementMoveCount();
			}
			else
				obj.resetMoveCount();
		}
		
		else
		{
			obj.move(speed);
		}
	}
	
	/**
	 * @return The bounding box of the character
	 */
	public Rectangle getBounds()
	{
		Rectangle rect = new Rectangle((int) x, (int) y+(sprite.getHeight()-sprite.getWidth())/2,
				sprite.getWidth(), sprite.getWidth());
		
		return rect;
	}
	
	/**
	 * @param distX x distance
	 * @param distY y distance
	 * @return The new bounding box of the character if moved the specified distance
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
	 * @param distance
	 * @return The new bounding box of the character if moved the specified distance
	 * in the current direction
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

	protected void validateState()
	{
		super.validateState();
		
		if(hp > maxHp)
		{
			throw new IllegalArgumentException("HP cannot exceed max HP");
		}
	}
}