/**
 * Main game
 * 
 * @author	Jamie of the Javateerz
 */

package org.javateerz.ParkViewProtector;

import java.io.*;
import java.util.ArrayList;

import org.javateerz.ParkViewProtector.Levels.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.geom.Rectangle;

public class Game extends GameScreen implements Serializable
{
	// Speed at which the game slows down
	public static final int SPEED_THROTTLE		= 10;
	
	// Number of pixels to move
	public static final int MOVE_SPEED			= 1;
	
	// delay (in number of frames) before another attack can be used
	// FIXME: should probably go in Staff
	public static final int ATTACK_DELAY		= 20;
	private int attackDelay						= 0;
	public static final int ITEM_USE_DELAY		= 20;
	private int itemDelay						= 0;
	public static final int TP_REGEN			= 5;
	public static int tpRegen					= 0;
	////////////////////////////////////////////////////
	public static final int CHARGE_REGEN		= 10;
	public static int chargeRegen				= 0;
	
	public static final int GAME_OVER_DELAY		= 5000;
	
	public static final double COUPLE_CHARGE_CHANCE	= 0.1;
	public static final int COUPLE_CHARGE_AMOUNT	= 1;
	
	public static final double Couple_CHANCE	= 0.6;
	public static final double ATTACK_CHANCE	= 0.1;
	
	public static final int MIN_NUM_MOVES		= 10;
	public static final int MAX_NUM_MOVES		= 400;
	
	public static final int DECOUPLE_SPACING	= 40;
	public static final int COUPLE_CHANCE_MULTIPLIER = 400;
	
	public static final int PLAYER_X			= 10;
	public static final int PLAYER_Y			= 30;
	public static double hpPercent;
	public static double tpPercent;
	
	private static final long serialVersionUID	= 7L;
	
	private transient Statistics stats;
	private transient Level lev;
	
	// objects on the screen
	private int level							= 1;
	private Staff player;
	private VisualFX background					= new VisualFX(this,"background1",0,0,0);
	private ArrayList<Student> students			= new ArrayList<Student>();
	private ArrayList<Couple> couples			= new ArrayList<Couple>();
	private ArrayList<Attack> attacks			= new ArrayList<Attack>();
	private ArrayList<Item> items				= new ArrayList<Item>();
	private ArrayList<VisualFX> fx				= new ArrayList<VisualFX>();
	private ArrayList<Wall> walls;

	/**
	 * Constructor
	 * 
	 * @param w Width of the game canvas
	 * @param h Height of the game canvas
	 * @param g Graphics canvas
	 * @param strategy Buffer strategy
	 */
	public Game(ParkViewProtector p)
	{
		init(p);
		
		stats									= new Statistics();
		
		// load background music
		setMusic("heavyset.ogg");
		
		// initialize level
		initLevel();
		
		// initialize everything
		initStudents();
		//initPlayer();
		initItems();
	}
	
	public ArrayList<Student> getStudents()
	{
		return students;
	}
	
	public ArrayList<Couple> getCouples()
	{
		return couples;
	}
	
	public ArrayList<Wall> getWalls()
	{
		return walls;
	}
	
	public ArrayList<Attack> getAttacks()
	{
		return attacks;
	}
	
	public Staff getPlayer()
	{
		return player;
	}
	
	
	
	public void init(ParkViewProtector p)
	{
		this.driver								= p;
	}
	
	/**
	 * Create and initialize player (the staff member we're playing as)
	 */
	public void initPlayer()
	{
		player						= new SpecialCharacter(this, PLAYER_X, PLAYER_Y);
	}
	
	// FIXME: decide if this should be part of the constructor
	public void setPlayer(Staff player)
	{
		this.player					= player;
		this.player.moveTo(PLAYER_X, PLAYER_Y);
	}
	
	/**
	 * Initialize level and draw walls
	 */
	public void initLevel()
	{
		switch(level)
		{
			default:
				lev					= new Level1(this);
				break;
		}
		
		walls						= lev.getWalls();
	}
	
	/**
	 * Initialize walls only
	 */
	public void initStudents()
	{
		students					= lev.getStudents();
	}
	
	public void initItems()
	{
		for(int i = 0;i < students.size();i++)
		{
			int random = (int)(Math.random()*4);
			//there is a 1/2 chance of a health item, 1/4 chance of a teacher item, and 1/4 chance of no item
			switch(random)
			{
			case 0:
			case 1:
				students.get(i).pickItem(new Item(this, 'h', 0, 0));
				break;
			case 2:
				students.get(i).pickItem(new Item(this, 't', 0, 0));
			default:
				break;
			}
		}
	}
	
	public void show()
	{
		Student currStudent; 
		Couple currCouple;
		Attack currAttack;
		
		// ensure music is playing
		ensureMusicPlaying();
		
		// key handling
		if(Keyboard.isKeyDown(KeyboardConfig.MENU))
		{
			ParkViewProtector.showMenu			= true;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
		{
			driver.quitGame();
		}
		////////////////////////////////////////////////////////////////////////////////////
		// Draw Background
		////////////////////////////////////////////////////////////////////////////////////
		
		background.draw();

		////////////////////////////////////////////////////////////////////////////////////
		// Draw walls
		////////////////////////////////////////////////////////////////////////////////////
		
		for(Wall w : walls)
		{
			w.draw();
		}
		
		////////////////////////////////////////////////////////////////////////////////////
		// Draw students
		////////////////////////////////////////////////////////////////////////////////////
		
		for(int i = 0; i < students.size(); i++)
		{
			currStudent			= students.get(i);
			currStudent.draw();
			currStudent.step(this);
			if(currStudent.getCharge() <= 0 && currStudent.bin.items.size() > 0)
			{
				while(currStudent.bin.items.size() > 0)
				{
					items.add(currStudent.bin.items.get(0));
					currStudent.bin.dropItem(currStudent.bin.items.get(0));
				}
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////////
		// Draw couples
		////////////////////////////////////////////////////////////////////////////////////
		
		for(int i = 0; i < couples.size(); i++)
		{
			currCouple			= couples.get(i);
			currCouple.draw();
			
			currCouple.step(this);
			
			// did the couple hit the player? if so, decrease HP
			if(currCouple.getBounds().intersects(player.getBounds()) &&
					Math.random() <= ATTACK_CHANCE && !currCouple.isStunned())
			{
				if(player.getHp() <= 0)
				{
					gameOver();
				}
				else {
					player.adjustHp(1);
				}
			}
			
			// update students
			/*for(int j = 0; j < students.size(); j++)
			{
				// if we hit a student that isn't infected
				if(currCouple.getBounds().intersects(students.get(j).getBounds())
						&& !students.get(j).isInfected()
						&& Math.random() <= INFECT_CHANCE)
				{
					students.get(j).infect();
					System.out.println("student #" + j + " infected by couple #" + i);
					break;
				}
			}*/
		}
		
		////////////////////////////////////////////////////////////////////////////////////
		// Draw player
		////////////////////////////////////////////////////////////////////////////////////
		
		player.draw();
		player.step(this);
		
		////////////////////////////////////////////////////////////////////////////////////
		// Draw attacks
		////////////////////////////////////////////////////////////////////////////////////
		
		for(int i = 0; i < attacks.size(); i++)
		{
			currAttack			= attacks.get(i);
			currAttack.draw();
			
			currAttack.move(MOVE_SPEED);
			
			// is the attack off the screen?
			if(currAttack.getBounds().getX() < -currAttack.getBounds().getWidth() ||
					currAttack.getBounds().getX() > ParkViewProtector.WIDTH ||
					currAttack.getBounds().getY() < -currAttack.getBounds().getHeight() ||
					currAttack.getBounds().getY() > ParkViewProtector.HEIGHT)
			{
				System.out.println("Attack #" + i +" went off screen, removing");
				
				attacks.remove(i);
				i--;
			}
			
			if(i >= 0 && currAttack.over())
			{
				attacks.remove(i);
				i--;
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////////
		// Draw items
		///////////////////////////////////////////////////////////////////////////////////
		// FIXME: these should probably be drawn before everything else
		
		for(int i = 0;i < items.size();i++)
		{
				items.get(i).draw();
				if(items.get(i).getBounds().intersects(player.getBounds()))
				{
					player.pickItem(items.get(i));
					items.remove(items.get(i));
				}
		}
		
		////////////////////////////////////////////////////////////////////////////////////
		// Draw FXs
		////////////////////////////////////////////////////////////////////////////////////
		
		for(int i=0; i<fx.size(); i++)
		{
			fx.get(i).draw();
			if(fx.get(i).tick())
			{
				fx.remove(fx.get(i));
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////////
		// Draw statistics
		////////////////////////////////////////////////////////////////////////////////////
		// these are painted last to ensure that they are always on top
		
		stats.draw(player, level);
		
		////////////////////////////////////////////////////////////////////////////////////
		// Move the player
		////////////////////////////////////////////////////////////////////////////////////
		
		int distX = 0, distY = 0;
		
		if(Keyboard.isKeyDown(KeyboardConfig.UP) &&
			!Keyboard.isKeyDown(KeyboardConfig.DOWN) &&
			player.getBounds().getY() > 0)
		{
			distY						= -MOVE_SPEED;
		}
		
		if(Keyboard.isKeyDown(KeyboardConfig.DOWN) &&
				!Keyboard.isKeyDown(KeyboardConfig.UP) &&
				player.getBounds().getY() < ParkViewProtector.HEIGHT - player.getBounds().getHeight())
		{
			distY						= MOVE_SPEED;
		}
		
		if(Keyboard.isKeyDown(KeyboardConfig.LEFT) &&
				!Keyboard.isKeyDown(KeyboardConfig.RIGHT) &&
				player.getBounds().getX() > 0)
		{
			distX						= -MOVE_SPEED;
		}
		
		if(Keyboard.isKeyDown(KeyboardConfig.RIGHT) &&
				!Keyboard.isKeyDown(KeyboardConfig.LEFT) &&
				player.getBounds().getX() < ParkViewProtector.WIDTH - player.getBounds().getWidth())
		{
			distX						= MOVE_SPEED;
		}
		
		// if we are moving, check to see if location is clear, then move
		if(distX != 0 || distY != 0)
		{
			if(!player.canMove(player.getNewBounds(distX,distY)))
			{
				for(int i=0; i<students.size(); i++)
				{
					Student s = students.get(i);
					if(player.getNewBounds(MOVE_SPEED).intersects(s.getBounds()))
					{
						player.push(students.get(i));
					}
				}
			}
			else if(player.canMove(player.getNewBounds(distX, distY)))
			{
				player.move(distX, distY);
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////////
		// Create attacks
		////////////////////////////////////////////////////////////////////////////////////
		
		playerAttack();
		
		// keep the game from running too fast
		try
		{
			Thread.sleep(SPEED_THROTTLE);
		}
		catch(Exception e) {}
		
		////////////////////////////////////////////////////////////////////////////////////
		// Use items
		// "Yeah, yeah, Jamie, I know; this is terrible placement"
		// 			-Donny
		////////////////////////////////////////////////////////////////////////////////////
		
		if(Keyboard.isKeyDown(KeyboardConfig.USE_HP_ITEM))
		{
			if(itemDelay == ITEM_USE_DELAY)
			{
				System.out.println("Game class says you healed hp! - itemDelay is " + itemDelay);
				player.useItem('h');
				itemDelay = 0;
			}
		}
		if(Keyboard.isKeyDown(KeyboardConfig.USE_TP_ITEM))
		{
			if(itemDelay == ITEM_USE_DELAY)
			{
				System.out.println("Game class says you healed tp! - itemDelay is " + itemDelay);
				player.useItem('t');
				itemDelay = 0;
			}
		}
		if(itemDelay < ITEM_USE_DELAY)
		{
			itemDelay++;
		}
	}
	
	/**
	 * Random movement
	 * 
	 * @param Movable Object to move
	 */
	/*public void moveRandom(Movable obj)
	{
		int speed					= MOVE_SPEED;
		int changeMoves				= (int) (Math.random() * (MAX_NUM_MOVES - MIN_NUM_MOVES) +
				MIN_NUM_MOVES + 1);
		
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
			obj.setDirection(Direction.SOUTH);
			obj.resetMoveCount();
		}
		else if(obj.getBounds().getY() >= ParkViewProtector.HEIGHT - obj.getBounds().getHeight()  &&
				obj.getDirection() == Direction.SOUTH)
		{
			obj.setDirection(Direction.NORTH);
			obj.resetMoveCount();
		}
		else if(obj.getBounds().getX() <= 0 && obj.getDirection() == Direction.WEST)
		{
			obj.setDirection(Direction.EAST);
			obj.resetMoveCount();
		}
		else if(obj.getBounds().getX() >= ParkViewProtector.WIDTH - obj.getBounds().getWidth() &&
				obj.getDirection() == Direction.EAST)
		{
			obj.setDirection(Direction.WEST);
			obj.resetMoveCount();
		}
		
		// check for collisions
		if(obj.getNewBounds(speed).intersects(player.getBounds())
				|| !((Character) obj).canMove(obj.getNewBounds(speed)))
		{
			// collision, must choose new direction
			
			if(obj instanceof Character && obj.getNewBounds(speed).intersects(player.getBounds()))
			{
				((Character) obj).push(player);
				obj.incrementMoveCount();
			}
			else
				obj.resetMoveCount();
		}
		
		else
		{
			obj.move(speed);
		}
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
	/*public void push(Character c1, Character c2)
	{
		//System.out.println("PUSHING!");
		double	temp1=c1.getSpeed(),
				temp2=c2.getSpeed();
		
		c1.setSpeed(c1.getSpeed());
		c2.setSpeed(c1.getSpeed()+.1);
		
		switch(c1.getDirection())
		{
			case Direction.NORTH:
				if(canMove(c2.getNewBounds(0,-1), c2))
				{
					c1.move(0,-1);
					c2.move(0,-1);
				}
				break;
			case Direction.SOUTH:
				if(canMove(c2.getNewBounds(0,1), c2))
				{
					c1.move(0,1);
					c2.move(0,1);
				}
				break;
			case Direction.EAST:
				if(canMove(c2.getNewBounds(1,0), c2))
				{
					c1.move(1,0);
					c2.move(1,0);
				}
				break;
			case Direction.WEST:
				if(canMove(c2.getNewBounds(-1,0), c2))
				{
					c1.move(-1,0);
					c2.move(-1,0);
				}
				break;
		}
		c1.setSpeed(temp1);
		c2.setSpeed(temp2);
	}*/
	
	/**
	 * Loop through students, couples, and walls to see if the specified rectangle is
	 * available
	 * 
	 * @return
	 */
	/*public boolean canMove(Rectangle newRect, Character curr)
	{
		// students
		if(students.size() > 0)
		{
			for(Student s : students)
			{
				if(!s.isStunned() && newRect.intersects(s.getBounds()))
				{
					if(!(s instanceof Student) || s!=curr)
						return false;
				}
			}
		}
		
		// couples
		if(couples.size() > 0)
		{
			for(Couple c : couples)
			{
				if(!c.isStunned() && newRect.intersects(c.getBounds()))
				{
					if(!(c instanceof Couple) || c!=curr)
						return false;
				}
			}
		}
			
		// walls
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
	}/*
	
	/**
	 * Handles players attacks
	 */
	public void playerAttack()
	{
		if((Keyboard.isKeyDown(KeyboardConfig.ATTACK1) || Keyboard.isKeyDown(KeyboardConfig.ATTACK2)
				|| Keyboard.isKeyDown(KeyboardConfig.ATTACK3)) && attackDelay == 0)
		{
			Attack playerAttack;
			int attackKey=0;
			if(Keyboard.isKeyDown(KeyboardConfig.ATTACK1))
			{
				attackKey=0;
			}
			else if(Keyboard.isKeyDown(KeyboardConfig.ATTACK2))
			{
				attackKey=1;
			}
			else if(Keyboard.isKeyDown(KeyboardConfig.ATTACK3))
			{
				attackKey=2;
			}
		
			playerAttack			= player.getAttack(attackKey);
			if(player.getTp()>=playerAttack.getTp())
			{
				player.setAttackFrames(playerAttack.getStillTime());
				player.adjustTp(-playerAttack.getTp());
				playerAttack.switchXY();
				attacks.add(playerAttack);
				
				try
				{
					ParkViewProtector.playSound(playerAttack.getName()+".wav");
				}
				catch(Exception e)
				{
					System.out.println("The attack has no sound.");
				}
				
				// set delay
				attackDelay			= playerAttack.getReuse();
			}
			
		}
		
		// decrease delay if there is one
		if(attackDelay > 0)
			attackDelay--;
	}
	
	public void hitFX(int x, int y)
	{
		fx.add(new VisualFX(this, "blip", 10, x, y));
	}
	
	/**
	 * Returns the specified wall
	 */
	public Wall getWall(int wallId)
	{
		return walls.get(wallId);
	}
	
	/**
	 * "Game over" message
	 */
	public void gameOver()
	{
		Sprite gameOver					= DataStore.INSTANCE.getSprite("game_over.png");
		
		gameOver.draw(0, 0);
		
		// show rendered content
		GL11.glFlush();
		Display.update();
		
		try
		{
			Thread.sleep(GAME_OVER_DELAY);
		}
		catch(Exception e)
		{
		}
		
		driver.quitGame();
	}
	
	/**
	 * Shows the charges of the students
	 */
	public void showCharges()
	{
		//g.setColor(ParkViewProtector.COLOR_BG_1);
		
		for(int i = 0;i < students.size();i++)
		{
			if(students.get(i).getCharge() > 0)
			{
				students.get(i).showCharge();
			}
		}
	}
	
	/**
	 * Read a save file
	 * 
	 * @param os Object input stream
	 */
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream os)
	{
		try
		{
			level		= os.readInt();
			player		= (Staff) os.readObject();
			students	= (ArrayList<Student>) os.readObject();
			couples		= (ArrayList<Couple>) os.readObject();
			attacks		= (ArrayList<Attack>) os.readObject();
			
			initLevel();
		}
		catch(ClassNotFoundException e)
		{
			driver.error("Your save file appears to be corrupt. A ClassNotFoundException occurred.", true);
		}
		catch(IOException e)
		{
			driver.error("Unable to read the save file", true);
		}
	}
	
	/**
	 * Write a save file
	 * 
	 * @param os Object output stream
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream os) throws IOException
	{
		os.writeInt(level);
		os.writeObject(player);
		os.writeObject(students);
		os.writeObject(couples);
		os.writeObject(attacks);
	}
}
