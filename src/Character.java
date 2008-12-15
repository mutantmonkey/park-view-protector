/**
 * Class to handle everything associated with Student, Staff, and Cupple
 *
 * @author	Jamie of the Javateerz
 */

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class Character
{
	// stats
	protected int hp;
	protected int maxHp;
	protected int damage;
	
	protected double speed;
	
	// placement
	protected int x;
	protected int y;
	
	// direction (0 = north, 1 = east, 2 = south, 3 = west)
	protected int direction		= 2;
	
	public ArrayList<Item> inventory;
	
	protected Sprite sprite;
	protected int moveCount;
	
	public Character(int x, int y, int hp, int maxHp, double speed, int damage)
	{
		this.x		= x;
		this.y		= y;
		
		this.hp		= hp;
		this.maxHp	= maxHp;
		this.damage	= damage;
		
		this.speed	= speed;
		
		this.sprite	= DataStore.INSTANCE.getSprite("images/placeholder.png");
	}
	
	/**
	 * Returns the amount of HP that this character has
	 * 
	 * @return Amount of HP
	 */
	public int getHp()
	{
		return hp;
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
		
		return hp;
	}
	
	/**
	 * Returns the speed of the character
	 * 
	 * @return Speed
	 */
	public double getSpeed()
	{
		return speed;
	}
	
	/**
	 * Changes the character's speed
	 * 
	 * @param change New speed
	 */
	public void changeSpeed(double change)
	{
		speed = change;
	}
	
	/**
	 * Moves the student a distance (which is multiplied by speed)
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
	 * Moves the student a distance (which is multiplied by speed) in the current direction
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
	 * Random movement
	 * 
	 * @param speed Speed to move at
	 * @param changeMoves Number of moves to change the direction after
	 */
	public void moveRandom(int speed, int changeMoves)
	{
		// change direction if the move count exceeds the number of moves to change after
		if(moveCount <= 0 || moveCount > changeMoves)
		{
			// choose a new direction
			setDirection((int) (Math.random() * 4));
					
			resetMoveCount();
		}
		
		move(speed);
	}
	
	/**
	 * Does an attack
	 */
	public abstract void attack();
	
	/**
	 * Removes an item from the character's inventory
	 * 
	 * @param i Index of item in the ArrayList
	 */
	public void dropItem(int i)
	{
		inventory.remove(i);
	}
	
	/**
	 * Adds an item to the character's inventory
	 * 
	 * @param item Item to add
	 */
	public void pickItem(Item item)
	{
		inventory.add(item);
	}
	
	/**
	 * Changes the direction that the character is facing
	 * 
	 * @param int direction
	 */
	public void setDirection(int dir)
	{
		direction	= dir;
	}
	
	/**
	 * Returns the direction that the character is facing
	 * 
	 * @return
	 */
	public int getDirection()
	{
		return direction;
	}
	
	/**
	 * Returns the number of moves made
	 * 
	 * @return
	 */
	public int getMoveCount()
	{
		return moveCount;
	}
	
	/**
	 * Resets the move counter
	 */
	public void resetMoveCount()
	{
		moveCount	= 0;
	}
	
	/**
	 * Called by main game loop, draws the character's sprite on the screen
	 */
	public void draw(Graphics g)
	{
		sprite.draw(g, x, y);
	}
	
	/**
	 * Gets the bounds :D
	 * @return Bounding box
	 */
	public Rectangle getBounds()
	{
		Rectangle rect			= new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
		
		return rect;
	}
}