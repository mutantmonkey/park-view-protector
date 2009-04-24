/**
 * Main game
 * 
 * @author	Jamie of the Javateerz
 */

package org.javateerz.ParkViewProtector;

import java.io.*;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.geom.Rectangle;

public class Game extends GameScreen implements Serializable
{
	public static final int SPEED_THROTTLE		= 10;
	
	// number of pixels to move by
	public static final int MOVE_SPEED			= 1;
	
	// delay (in number of frames) before another attack can be used
	public static final int ATTACK_DELAY		= 20;
	private int attackDelay						= 0;
	public static final int ITEM_USE_DELAY		= 20;
	private int itemDelay						= 0;
	public static final int TP_REGEN			= 5;
	public static int tpRegen					= 0;
	public static final int CHARGE_REGEN		= 10;
	public static int chargeRegen				= 0;
	
	public static final int GAME_OVER_DELAY		= 5000;
	
	public static final int MIN_STUDENTS		= 20;
	public static final int MAX_STUDENTS		= 30;
	
	public static final int MAX_STUDENT_SPEED	= 1;
	public static final double GENDER_CHANCE	= 0.5;
	
	public static final double COUPLE_CHARGE_CHANCE	= 0.1;
	public static final int COUPLE_CHARGE_AMOUNT	= 1;
	
	public static final double CUPPLE_CHANCE	= 0.6;
	public static final double ATTACK_CHANCE	= 0.1;
	
	public static final int MIN_NUM_MOVES		= 10;
	public static final int MAX_NUM_MOVES		= 400;
	
	public static final int DECOUPLE_SPACING	= 40;
	public static final int COUPLE_CHANCE_MULTIPLIER = 400;
	
	public static final int PLAYER_X			= 10;
	public static final int PLAYER_Y			= 30;
	public static final int PLAYER_HP			= 50;
	public static final int PLAYER_TP			= 300;
	public static double hpPercent;
	public static double tpPercent;
	
	private static final long serialVersionUID	= 6L;
	
	private transient Statistics stats;
	
	// objects on the screen
	private int level							= 1;
	private Staff player;
	private ArrayList<Student> students			= new ArrayList<Student>();
	private ArrayList<Cupple> couples			= new ArrayList<Cupple>();
	private ArrayList<Attack> attacks			= new ArrayList<Attack>();
	private ArrayList<Item> items				= new ArrayList<Item>();
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
		
		// initialize everything
		initPlayer();
		initWalls();
		initStudents();
		initItems();
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
		player						= new SpecialCharacter(PLAYER_X, PLAYER_Y, PLAYER_HP, PLAYER_TP);
	}
	
	/**
	 * Create some walls
	 */
	public void initWalls()
	{
		walls						= new ArrayList<Wall>();
		
		switch(level)
		{
			default:
				walls.add(new Wall(100, 0, ParkViewProtector.WIDTH - 60, 4));
				walls.add(new Wall(100, 3, 4, 300));
				
				break;
		}
	}
	
	/**
	 * Create and initialize students
	 */
	public void initStudents()
	{
		// create a random number of students using MIN_STUDENTS and MAX_STUDENTS; multiply
		// it by 2 and divide to ensure that an even number is created to ensure proper
		// coupling
		int numStudents				= (int) (Math.random() * (MAX_STUDENTS - MIN_STUDENTS + 1)) + MIN_STUDENTS;
		numStudents					= Math.round(numStudents * 2 / 2);
		
		Student student				= null;
		
		int x, y, type;
		double speed;
		char gender;
		
		for(int i = 0; i < numStudents; i++)
		{
			x						= (int) (Math.random() * ParkViewProtector.WIDTH);
			y						= (int) (Math.random() * ParkViewProtector.HEIGHT);
			speed					= Math.random() * MAX_STUDENT_SPEED + 1;
			gender					= (Math.random() <= GENDER_CHANCE) ? 'm' : 'f';
			type					= (int)(Math.random()*3);
			
			student					= new Student(x, y, 5, 5, speed, gender, type);
			
			// make sure that the student is not spawned on top of a wall)
			while(!canMove(student.getBounds()))
			{
				x					= (int) (Math.random() * ParkViewProtector.WIDTH) + 1;
				y					= (int) (Math.random() * ParkViewProtector.HEIGHT) + 1;
				
				student.moveTo(x, y);
			}
			
			students.add(student);
		}
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
				students.get(i).pickItem(new Item('h',0,0));
				break;
			case 2:
				students.get(i).pickItem(new Item('t',0,0));
			default:
				break;
			}
		}
	}
	
	public void show()
	{
		Student currStudent; 
		Cupple currCouple;
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
					Math.random() <= ATTACK_CHANCE && !currCouple.getStunned())
			{
				if(player.getHp() <= 0)
				{
					gameOver();
				}
				else {
					player.adjustHp(1);
				}
			}
			
			if(Keyboard.isKeyDown(KeyboardConfig.SHOW_CHARGES))
			{
				showCharges();
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
		// Draw walls
		////////////////////////////////////////////////////////////////////////////////////
		
		for(Wall w : walls)
		{
			w.draw();
		}
		
		////////////////////////////////////////////////////////////////////////////////////
		// Draw player
		////////////////////////////////////////////////////////////////////////////////////
		
		player.draw();
		player.step(this);
		
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
		// Draw statistics
		////////////////////////////////////////////////////////////////////////////////////
		// these are painted last to ensure that they are always on top
		
		stats.draw(player, level);
		
		////////////////////////////////////////////////////////////////////////////////////
		// Move the player
		////////////////////////////////////////////////////////////////////////////////////
		
		int distX = 0, distY = 0;
		
		if(Keyboard.isKeyDown(KeyboardConfig.UP) && player.getBounds().getY() > 0)
		{
			distY						= -MOVE_SPEED;
		}
		
		if(Keyboard.isKeyDown(KeyboardConfig.DOWN) && player.getBounds().getY() < ParkViewProtector.HEIGHT
				- player.getBounds().getHeight())
		{
			distY						= MOVE_SPEED;
		}
		
		if(Keyboard.isKeyDown(KeyboardConfig.LEFT) && player.getBounds().getX() > 0)
		{
			distX						= -MOVE_SPEED;
		}
		
		if(Keyboard.isKeyDown(KeyboardConfig.RIGHT) && player.getBounds().getX() < ParkViewProtector.WIDTH
				- player.getBounds().getWidth())
		{
			distX						= MOVE_SPEED;
		}
		
		// if we are moving, check to see if location is clear, then move
		if(distX != 0 || distY != 0)
		{
			if(canMove(player.getNewBounds(distX, distY)))
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
	public void moveRandom(Movable obj)
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
				|| !canMove(obj.getNewBounds(speed)))
		{
			// collision, must choose new direction
			obj.resetMoveCount();
		}
		else {
			obj.move(speed);
		}
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
		/*for(Student s : students)
		{
			if(!s.getStunned() && newRect.intersects(s.getBounds()))
			{
				return false;
			}
		}*/
		
		// couples
		/*for(Cupple c : couples)
		{
			if(newRect.intersects(c.getBounds()))
			{
				return false;
			}
		}*/
		
		// walls
		for(Wall w : walls)
		{
			if(newRect.intersects(w.getBounds()))
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Switches the player's character to another
	 */
	public void switchChar()
	{
		if(Keyboard.isKeyDown(KeyboardConfig.CHAR1) || Keyboard.isKeyDown(KeyboardConfig.CHAR2))
		{
			hpPercent=(double)player.getHp()/(double)player.getMaxHp();
			tpPercent=(double)player.getTp()/(double)player.getMaxTp();
			if(Keyboard.isKeyDown(KeyboardConfig.CHAR1) && !(player instanceof Stark))
			{
				player=new Stark((int) player.getBounds().getX(), (int) player.getBounds().getY(), (int) ((double)Stats.STARK_HP*hpPercent), (int)((double)Stats.STARK_TP*tpPercent));
			}
			
			if(Keyboard.isKeyDown(KeyboardConfig.CHAR2) && !(player instanceof SpecialCharacter))
			{
				player=new SpecialCharacter((int) player.getBounds().getX(), (int) player.getBounds().getY(), (int) ((double)Stats.SPECIAL_HP*hpPercent), (int) ((double)Stats.SPECIAL_TP*tpPercent));
			}
		}
	}
	
	
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
				player.stun(playerAttack.getStillTime());
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
	
	/**
	 * Regenerate the player's TP
	 */
	public void tpRegen()
	{
		tpRegen+=1;
		if(tpRegen>=TP_REGEN)
		{
			if(player.getTp()<player.getMaxTp())
				player.adjustTp(2);
			tpRegen=0;
		}
	}
	
	
	/**
	 * Recharge students
	 */
	public void recharge(Student student)
	{
		chargeRegen+=1;
		if(chargeRegen>=CHARGE_REGEN)
		{
			chargeRegen=0;
			if(student.getCharge()<100)
				student.adjustCharge(1);
		}
	}
	
	/**
	 * Attempt to couple with another student
	 * 
	 * @param currStudent
	 * @return Whether or not the coupling succeeded
	 */
	public boolean attemptCoupling(Student currStudent)
	{
		int charge, student1, student2;
		Student testStudent;
		
		int i						= students.indexOf(currStudent);
		
		// prevent coupling if we intersect a wall
		if(!canMove(currStudent.getBounds()))
		{
			return false;
		}
		
		for(int j = 0; j < students.size(); j++)
		{
			testStudent				= students.get(j);
			
			// don't do anything if it's us
			if(i == j)
			{
				continue;
			}
			
			// no same-sex couples (for now at least)
			if(currStudent.getGender() == testStudent.getGender())
			{
				continue;
			}

			// did we hit another student with a charge?
			if(currStudent.getBounds().intersects(students.get(j).getBounds())
					&& testStudent.getCharge() > 0)
			{
				charge				= currStudent.getCharge() +
										testStudent.getCharge();
				
				if(Math.random() * COUPLE_CHANCE_MULTIPLIER < charge)
				{
					couples.add(new Cupple(currStudent, testStudent));
					
					student1			= i;
					student2			= j;
					
					if(student2 > student1)
					{
						student2--;
					}
					
					try
					{
						students.remove(student1);
						students.remove(student2);
					}
					catch(Exception e)
					{
						driver.error("Something went wrong when deleting someone :O", false);
					}
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Handle attacks
	 * 
	 * @return Whether or not the attack hit the player
	 */
	public boolean handleAttack()
	{
		Attack currAttack;
		
		for(int j = 0; j < attacks.size(); j++)
		{
			currAttack		= attacks.get(j);
			
			if(currAttack.getBounds().intersects(player.getBounds()) && player.isHittable() && !currAttack.isStudent())
			{
				player.adjustHp(currAttack.getDamage());
				if(player.getHp()>player.getMaxHp())
					player.setHp(player.getMaxHp());
				player.setHitDelay(currAttack.getHitDelay());
				
				if(!currAttack.isAoE())
				{
					attacks.remove(j);
				}
				
				if(currAttack.getStatus()==Status.STUN)
				{
					player.stun(currAttack.getStatusDuration());
				}
				
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Handle any attacks on a student
	 * 
	 * @param obj
	 * @return Whether or not the attack hit the student
	 */
	public boolean handleAttacks(Student currStudent)
	{
		Attack currAttack;
		
		// hit by an attack?
		for(int j = 0; j < attacks.size(); j++)
		{
			currAttack		= attacks.get(j);
			
			//The student takes damage in this if loop
			if(currAttack.getBounds().intersects(currStudent.getBounds()) &&
					/*currStudent.getCharge() > 0 && */currStudent.isHittable() &&
					currAttack.isStudent())
			{
				if(currAttack.getStatus()==Status.STUN)
				{
					currStudent.stun(currAttack.getStatusDuration());
				}
				
				if(!currAttack.isAoE())
				{
					attacks.remove(j);
				}
				
				// FIXME: should be variable depending on strength
				if(currStudent.getCharge()>-10)
					currStudent.adjustCharge(-currAttack.getDamage()/3);
				//System.out.println("Student took "+ currAttack.getDamage()/3 + ", now has "+ currStudent.getCharge());
				currStudent.setHitDelay(currAttack.getHitDelay());
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Handle any attacks on a couple
	 * 
	 * @param currCouple
	 * @return Whether or not the attack hit the couple
	 */
	public boolean handleAttacks(Cupple currCouple)
	{
		Attack currAttack;
		Student male, female;
		
		int i				= couples.indexOf(currCouple);
		
		// hit by an attack?
		for(int j = 0; j < attacks.size(); j++)
		{
			currAttack		= attacks.get(j);
			
			if(currAttack.getBounds().intersects(currCouple.getBounds()) &&
					currCouple.isHittable() && currAttack.isStudent())
			{
				currCouple.adjustHp(currAttack.getDamage());
				currCouple.setHitDelay(currAttack.getHitDelay());
				
				if(!currAttack.isAoE())
				{
					attacks.remove(j);
				}
				
				if(currAttack.getStatus()==Status.STUN)
				{
					currCouple.stun(currAttack.getStatusDuration());
				}
				
				// FIXME: If multiple attacks hit something or what?
				if(currCouple.getHp() <=0)
				{
					male		= currCouple.getMale();
					male.moveTo(currCouple.getBounds().getX(),
							currCouple.getBounds().getY());
					male.setHitDelay(currAttack.getHitDelay());
					male.setCharge(-10);
					
					female		= currCouple.getFemale();
					female.moveTo(currCouple.getBounds().getX() + DECOUPLE_SPACING,
							currCouple.getBounds().getY());
					female.setHitDelay(currAttack.getHitDelay());
					female.setCharge(-10);
					
					// create students before removing couple
					students.add(male);
					students.add(female);
					
					couples.remove(i);
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Finds the index of the wall that is closest to the object
	 * 
	 * @param Movable Object to use
	 */
	public int findNearestWall(Movable obj)
	{
		Wall nearestWall			= null;
		int smallestDist			= Integer.MAX_VALUE;
		int distX, distY, dist;
		
		for(Wall wall : walls)
		{
			// compute X distance
			if(wall.getBounds().getX() <= obj.getBounds().getX())
			{
				// wall lies to the left
				distX					= (int) (obj.getBounds().getX() - wall.getBounds().getX());
			}
			else {
				// wall lies to the right
				distX					= (int) (wall.getBounds().getX() - obj.getBounds().getX());
			}
			
			// compute Y distance
			if(wall.getBounds().getY() <= obj.getBounds().getY())
			{
				// wall lies above
				distY					= (int) (obj.getBounds().getY() - wall.getBounds().getY());
			}
			else {
				// wall lies below
				distY					= (int) (wall.getBounds().getY() - obj.getBounds().getY());
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
			couples		= (ArrayList<Cupple>) os.readObject();
			attacks		= (ArrayList<Attack>) os.readObject();
			
			initWalls();
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
