/**
 * This class stores information about a couple ("cupple")
 *
 * @author	Jamie of the Javateerz
 * @serial
 */

package org.javateerz.ParkViewProtector;

import java.io.*;

import org.newdawn.slick.geom.Rectangle;

public class Cupple extends Character
{
	private static final long serialVersionUID = 3L;
	
	/**
	 * Number to multiply to the combined speed of both students by
	 */
	public static final double SPEED_MULTIPLIER	= 0.2;
	
	/**
	 * The chance that a couple will become a wall couple
	 */
	public static final double WALL_CHANCE		= 0.6;
	
	/**
	 * Maximum distance from a wall in pixels that couples will
	 * "stick" to
	 */
	public static final int WALL_DISTANCE		= 10;
	
	private Student male;
	private Student female;
	
	private boolean likesWall		= false;
	private int wallId				= 0;
	
	public Cupple(Student a,Student b)
	{
		super(a.x,a.y,a.hp+b.hp,a.maxHp + b.maxHp, (a.speed+b.speed) * SPEED_MULTIPLIER);
		
		if(a.getGender() == 'm')
		{
			male = a;
			female = b;
		}
		else
		{
			male = b;
			female = a;
		}
		
		// determine whether or not this should be a wall couple
		likesWall					= (Math.random() < WALL_CHANCE) ? true : false;
		
		// temp graphic
		updateSprite();
	}
	
	protected void updateSprite()
	{
		System.out.println(female.getType() + "_" + male.getType());
		
		sprite	= DataStore.INSTANCE.getSprite("couples/" + female.getType() + "_"
				+ male.getType() + ".png");
	}
	
	public Student getMale()
	{
		return male;
	}
	
	public Student getFemale()
	{
		return female;
	}
	
	public void step(Game game)
	{
		if(likesWall)
		{
			// find nearest wall
			if(wallId == 0)
			{
				wallId		= game.findNearestWall(this);
			}
			
			// move toward the wall
			if(game.canMove(this.getBounds()))
			{
				Wall wall	= game.getWall(wallId);
				moveToward(wall);
			}
			else {
				
			}
		}
		else {
			game.moveRandom(this);
		}
		
		// decrement the hit delay
		decrementHitDelay(1);
		
		game.handleAttacks(this);
	}
	
	public void moveToward(Wall wall)
	{
		int distX				= 0,
			distY				= 0;
		
		// increase the bounding box for testing
		Rectangle testBounds	= this.getBounds();
		testBounds.grow(WALL_DISTANCE, WALL_DISTANCE);
		
		// make sure we actually need to move
		if(testBounds.intersects(wall.getBounds()))
		{
			return;
		}
		
		// compute X distance
		if(wall.getBounds().getX() < x)
		{
			distX				   -= Game.MOVE_SPEED;
		}
		else {
			// wall lies to the right
			distX				   += Game.MOVE_SPEED;
		}
		
		// compute Y distance
		if(wall.getBounds().getY() <= y)
		{
			// wall lies above
			distY				   -= Game.MOVE_SPEED;
		}
		else {
			// wall lies below
			distY				   += Game.MOVE_SPEED;
		}
		
		move(distX, distY);
	}

	public void attack()
	{
		// TODO: insert code here
	}
	
	private void readObject(ObjectInputStream os) throws ClassNotFoundException, IOException
	{
		os.defaultReadObject();
		
		validateState();
	}
	
	private void writeObject(ObjectOutputStream os) throws IOException
	{
		os.defaultWriteObject();
	}
}