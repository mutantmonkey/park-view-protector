/**
 * Main game
 * 
 * @author	James Schwinabart
 */

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.*;
import java.util.ArrayList;

public class Game implements Serializable
{
	public static final int SPEED_THROTTLE		= 10;
	
	// number of pixels to move by
	public static final int MOVE_SPEED			= 1;
	
	// delay (in number of frames) before another attack can be used
	public static final int ATTACK_DELAY		= 100;
	private int attackDelay						= 0;
	
	public static final int MIN_STUDENTS		= 20;
	public static final int MAX_STUDENTS		= 30;
	
	public static final int MAX_STUDENT_SPEED	= 1;
	public static final double GENDER_CHANCE	= 0.5;
	
	public static final double INFECT_CHANCE	= 0.4;
	public static final double CUPPLE_CHANCE	= 0.6;
	public static final double ATTACK_CHANCE	= 0.1;
	
	public static final int MIN_NUM_MOVES		= 10;
	public static final int MAX_NUM_MOVES		= 400;
	
	public static final int DECOUPLE_SPACING	= 50;
	
	public static final int STATS_BAR_HEIGHT	= 20;
	public static final int STAT_PAD_BOTTOM		= 6;
	
	private static final long serialVersionUID	= 1L;
	
	private ParkViewProtector driver;
	private Graphics g;
	private BufferStrategy strategy;
	
	// objects on the screen
	private Staff player;
	private ArrayList<Student> students			= new ArrayList<Student>();
	private ArrayList<Cupple> couples			= new ArrayList<Cupple>();
	private ArrayList<Attack> attacks			= new ArrayList<Attack>();

	/**
	 * Constructor
	 * 
	 * @param w Width of the game canvas
	 * @param h Height of the game canvas
	 * @param g Graphics canvas
	 * @param strategy Buffer strategy
	 */
	public Game(ParkViewProtector p, Graphics g, BufferStrategy strategy)
	{
		this.driver							= p;
		this.g								= g;
		this.strategy						= strategy;
		
		// initialize everything
		initPlayer();
		initStudents();
	}
	
	/**
	 * Create and initialize player (the staff member we're playing as)
	 */
	public void initPlayer()
	{
		// FIXME: magic numbers are bad
		player						= new Stark(0, STATS_BAR_HEIGHT, 100, 100, 10, 10, 1);
		//player						= new Stark(0, STATS_BAR_HEIGHT, 0, 0, 10, 10, 1);
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
		
		int x, y;
		double speed;
		char gender;
		
		for(int i = 0; i < numStudents; i++)
		{
			x						= (int) (Math.random() * ParkViewProtector.WIDTH) + 1;
			y						= (int) (Math.random() * ParkViewProtector.HEIGHT) + 1;
			speed					= Math.random() * MAX_STUDENT_SPEED + 1;
			gender					= (Math.random() <= GENDER_CHANCE) ? 'm' : 'f';
			
			student					= new Student(x, y, 5, 5, speed, 0, gender);
			
			students.add(student);
		}
	}
	
	public void show()
	{
		Student currStudent; 
		Cupple currCouple;
		Attack currAttack;
		
		Student male, female;
		int student1, student2;
		int charge;
		
		g						= (Graphics) strategy.getDrawGraphics();
		
		// draw the background
		g.setColor(ParkViewProtector.COLOR_BG_2);
		g.fillRect(0, 0, ParkViewProtector.WIDTH, ParkViewProtector.HEIGHT);
		
		////////////////////////////////////////////////////////////////////////////////////
		// Draw students
		////////////////////////////////////////////////////////////////////////////////////
		
		for(int i = 0; i < students.size(); i++)
		{
			currStudent			= students.get(i);
			currStudent.draw(g);
			
			// random movement
			moveRandom(currStudent, MOVE_SPEED,
					(int) (Math.random() * (MAX_NUM_MOVES - MIN_NUM_MOVES) +
							MIN_NUM_MOVES + 1));
			
			// couple with another student if possible
			if(currStudent.getCharge() > 0)
			{
				for(int j = 0; j < students.size(); j++)
				{
					// don't do anything if it's us
					if(i == j) continue;

					// did we hit another student with a charge?
					if(currStudent.getBounds().intersects(students.get(j).getBounds())
							&& students.get(j).getCharge() > 0)
					{
						charge				= currStudent.getCharge() +
												students.get(j).getCharge();
						
						if(Math.random() * 100 < charge)
						{
							couples.add(new Cupple(currStudent, students.get(j)));
							
							
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
								System.out.println("Something went wrong when deleting someone :O");
							}
							break;
						}
					}
				}
			}
			
			// hit by an attack?
			for(int j = 0; j < attacks.size(); j++)
			{
				currAttack		= attacks.get(j);
				
				if(currAttack.getBounds().intersects(currStudent.getBounds()) &&
						currStudent.getCharge() > 0)
				{
					attacks.remove(j);
					
					// FIXME: should be variable depending on strength
					currStudent.adjustCharge(-1);
					
					break;
				}
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////////
		// Draw couples
		////////////////////////////////////////////////////////////////////////////////////
		
		for(int i = 0; i < couples.size(); i++)
		{
			currCouple			= couples.get(i);
			currCouple.draw(g);
			
			// random movement
			moveRandom(currCouple, MOVE_SPEED,
					(int) (Math.random() * (MAX_NUM_MOVES - MIN_NUM_MOVES) +
							MIN_NUM_MOVES + 1));
			
			// did the couple hit the player? if so, decrease HP
			if(currCouple.getBounds().intersects(player.getBounds()) &&
					Math.random() <= ATTACK_CHANCE)
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
			
			// hit by an attack?
			for(int j = 0; j < attacks.size(); j++)
			{
				currAttack		= attacks.get(j);
				
				if(currAttack.getBounds().intersects(currCouple.getBounds()))
				{
					attacks.remove(j);
					
					male		= currCouple.getMale();
					male.moveTo(currCouple.getBounds().x, currCouple.getBounds().y);
					
					female		= currCouple.getFemale();
					female.moveTo(currCouple.getBounds().x + DECOUPLE_SPACING,
							currCouple.getBounds().y);
					
					// create students before removing couple
					students.add(male);
					students.add(female);
					
					couples.remove(i);
					break;
				}
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////////
		// Draw attacks
		////////////////////////////////////////////////////////////////////////////////////
		
		for(int i = 0; i < attacks.size(); i++)
		{
			currAttack			= attacks.get(i);
			currAttack.draw(g);
			
			currAttack.move(MOVE_SPEED);
			
			// is the attack off the screen?
			if(currAttack.getBounds().x < -currAttack.getBounds().width ||
					currAttack.getBounds().x > ParkViewProtector.WIDTH ||
					currAttack.getBounds().y < -currAttack.getBounds().height ||
					currAttack.getBounds().y > ParkViewProtector.HEIGHT)
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
		// Draw player
		////////////////////////////////////////////////////////////////////////////////////
		
		player.draw(g);
		
		////////////////////////////////////////////////////////////////////////////////////
		// Draw statistics
		////////////////////////////////////////////////////////////////////////////////////
		// these are painted last to ensure that they are always on top

		// background rectangle
		g.setColor(ParkViewProtector.COLOR_BG_1);
		g.fillRect(0, 0, ParkViewProtector.WIDTH, STATS_BAR_HEIGHT);
		
		// draw HP
		g.setColor(ParkViewProtector.COLOR_TEXT_1);
		g.setFont(new Font("Courier New", Font.PLAIN, 12));
		g.drawString("HP:    / " + player.getMaxHp(), 5,
				STATS_BAR_HEIGHT - STAT_PAD_BOTTOM);
		
		g.drawString("" + player.getHp(), 33, STATS_BAR_HEIGHT - STAT_PAD_BOTTOM);
		
		g.drawString("Speed: " + player.getSpeed(), 200, STATS_BAR_HEIGHT - STAT_PAD_BOTTOM);
		
		// finish drawing
		g.dispose();
		strategy.show();
		
		////////////////////////////////////////////////////////////////////////////////////
		// Move the player
		////////////////////////////////////////////////////////////////////////////////////
		// TODO: use physics for diagonal movement? (sqrt 2 * MOVE_SPEED^2)
		
		if(ParkViewProtector.upPressed && !ParkViewProtector.downPressed
				&& player.getBounds().y > STATS_BAR_HEIGHT)
		{
			player.move(0, -MOVE_SPEED);
		}
		
		if(ParkViewProtector.downPressed && !ParkViewProtector.upPressed
				&& player.getBounds().y < ParkViewProtector.HEIGHT - player.getBounds().height)
		{
			player.move(0, MOVE_SPEED);
			//downPressed				= false;
		}
		
		if(ParkViewProtector.leftPressed && !ParkViewProtector.rightPressed
				&& player.getBounds().x > 0)
		{
			player.move(-MOVE_SPEED, 0);
			//leftPressed				= false;
		}
		
		if(ParkViewProtector.rightPressed && !ParkViewProtector.leftPressed
				&& player.getBounds().x < ParkViewProtector.WIDTH - player.getBounds().width)
		{
			player.move(MOVE_SPEED, 0);
			//rightPressed			= false;
		}
		
		if(ParkViewProtector.attackPressed && attackDelay == 0)
		{
			Attack testAttack;
			testAttack			= new Attack(player.x + player.getBounds().width / 2,
												player.y + player.getBounds().height / 2,
												5, "attack", player.getDirection(), 3, 50, 
												true, Type.FRONT);

			testAttack.switchXY();
			attacks.add(testAttack);
			
			// set delay
			attackDelay			= ATTACK_DELAY;
			
		}
		
		// decrease delay if there is one
		if(attackDelay > 0)
			attackDelay--;
		
		// keep the game from running too fast
		try
		{
			Thread.sleep(SPEED_THROTTLE);
		}
		catch(Exception e) {}
	}
	
	/**
	 * Random movement
	 * 
	 * @param Movable Object to move
	 * @param speed Speed to move at
	 * @param changeMoves Number of moves to change the direction after
	 */
	public void moveRandom(Movable obj, int speed, int changeMoves)
	{
		// change direction if the move count exceeds the number of moves to change after
		if(obj.getMoveCount() <= 0 || obj.getMoveCount() > changeMoves)
		{
			// choose a new direction
			obj.setDirection((int) (Math.random() * 4));
			obj.resetMoveCount();
		}
		
		// change direction if we hit the top or bottom
		if(obj.getBounds().y <= STATS_BAR_HEIGHT && obj.getDirection() == Direction.NORTH)
		{
			obj.setDirection(Direction.SOUTH);
			obj.resetMoveCount();
		}
		else if(obj.getBounds().y >= ParkViewProtector.HEIGHT - obj.getBounds().height  &&
				obj.getDirection() == Direction.SOUTH)
		{
			obj.setDirection(Direction.NORTH);
			obj.resetMoveCount();
		}
		else if(obj.getBounds().x <= 0 && obj.getDirection() == Direction.WEST)
		{
			obj.setDirection(Direction.EAST);
			obj.resetMoveCount();
		}
		else if(obj.getBounds().x >= ParkViewProtector.WIDTH - obj.getBounds().width &&
				obj.getDirection() == Direction.EAST)
		{
			obj.setDirection(Direction.WEST);
			obj.resetMoveCount();
		}
		
		obj.move(speed);
	}
	
	public void gameOver()
	{
		String msg				= "GAME OVER";
		
		// draw the background
		g.setColor(ParkViewProtector.COLOR_BG_1);
		g.fillRect(0, 0, ParkViewProtector.WIDTH, ParkViewProtector.HEIGHT);
		
		// get width of string
		int x					= ParkViewProtector.WIDTH / 2;
		x					   -= g.getFontMetrics().stringWidth(msg);
		int y					= ParkViewProtector.HEIGHT / 2;
		y					   -= g.getFontMetrics().getHeight();
		
		System.out.println("Put it at (" + x + ", " + y + ")");
		
		// draw text
		g.setFont(new Font("System", Font.BOLD, 32));
		g.setColor(ParkViewProtector.COLOR_TEXT_1);
		g.drawString(msg, x, y);
		
		// finish drawing
		g.dispose();
		strategy.show();
		
		try
		{
			Thread.sleep(15000);
		}
		catch(Exception e)
		{
		}
		
		driver.quit();
	}
	
	private void readObject(ObjectInputStream os) throws ClassNotFoundException, IOException
	{
		player			= (Staff) os.readObject();
		//students		= (ArrayList<Student>) os.readObject();
		//couples			= (ArrayList<Cupple>) os.readObject();
		//attacks			= (ArrayList<Attack>) os.readObject();
	}
	
	private void writeObject(ObjectOutputStream os) throws IOException
	{
		os.writeObject(player);
		//os.writeObject(students);
		//os.writeObject(couples);
		//os.writeObject(attacks);
	}
}
