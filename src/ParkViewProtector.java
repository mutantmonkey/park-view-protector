/**
 * Park View Protector
 * 
 * This class is a subclass of Canvas so we can use accelerated graphics
 *
 * @author	Jamie of the Javateerz
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.*;

public class ParkViewProtector extends Canvas
{
	public static final int WIDTH			= 700;
	public static final int HEIGHT			= 500;
	
	public static final int SPEED_THROTTLE	= 10;
	public static final int MOVE_SPEED		= 1;
	
	public static final int MIN_STUDENTS	= 20;
	public static final int MAX_STUDENTS	= 30;
	
	public static final double INFECT_CHANCE	= 0.4;
	public static final double CUPPLE_CHANCE	= 0.6;
	
	public static final int MIN_NUM_MOVES	= 10;
	public static final int MAX_NUM_MOVES	= 400;
	
	protected JFrame window;
	protected JPanel contentPanel;
	
	private boolean running					= true;
	
	// how far the player needs to move in each direction the next time the game loop is run
	public static boolean upPressed			= false;
	public static boolean downPressed		= false;
	public static boolean leftPressed		= false;
	public static boolean rightPressed		= false;
	public static boolean attackPressed		=false;
	
	// graphics
	private Graphics g;
	private BufferStrategy strategy;
	
	// characters
	private Staff player;
	private ArrayList<Student> students		= new ArrayList<Student>();
	private ArrayList<Cupple> couples		= new ArrayList<Cupple>();
	private ArrayList<Attack> attacks		= new ArrayList<Attack>();
	
	public ParkViewProtector()
	{
		// create container JFrame (window)
		window						= new JFrame("Park View Protector");
		window.setSize(WIDTH, HEIGHT);
		
		// set up content panel
		contentPanel				= (JPanel) window.getContentPane();
		contentPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		// this makes the panel behave like what we were used to with ACM
		contentPanel.setLayout(null);
		
		// set content panel size
		setBounds(0, 0, WIDTH, HEIGHT);
		contentPanel.add(this);
		
		// set up window
		window.setResizable(false);
		//window.pack();
		
		// make the window visible
		window.setVisible(true);
		
		// this makes the program end when the window is closed
		window.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				window.dispose();
				System.exit(0);
			}
		});
		
		// don't automatically repaint
		setIgnoreRepaint(true);
	}
	
	/**
	 * Show opening graphics
	 */
	public void showOpening()
	{
		DataStore.INSTANCE.getAudio("sounds/clockhomestart.wav");
		
		Graphics g						= getGraphics();
		
		// draw the background
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		Sprite mainLogo					= DataStore.INSTANCE.getSprite("images/javateerslogo.png");
		mainLogo.draw(g, WIDTH / 2 - mainLogo.getWidth() / 2, HEIGHT / 2 - mainLogo.getHeight() / 2);
		
		g.dispose();
		
		try{Thread.sleep(3000);}catch(Exception e){}
	}
	
	/**
	 * Initialize game
	 */
	public void init()
	{
		// initialize everything
		initPlayer();
		initStudents();
		
		// add key handler class
		addKeyListener(new KeyHandler());
		
		// request focus so we will get events without a click
		requestFocus();
		
		// accelerated graphics
		createBufferStrategy(2);
		strategy					= getBufferStrategy();
	}
	
	/**
	 * Create and initialize player (the staff member we're playing as)
	 */
	public void initPlayer()
	{
		player						= new Stark(10, 10, 10, 10, 10, 10, 10);
		
		DataStore.INSTANCE.getAudio("sounds/nof.wav");
	}
	
	/**
	 * Create and initialize students
	 */
	public void initStudents()
	{
		int numStudents				= (int) (Math.random() * (MAX_STUDENTS - MIN_STUDENTS + 1)) + MIN_STUDENTS;
		Student student				= null;
		
		int x, y;
		double speed;
		char gender;
		
		for(int i = 0; i < numStudents; i++)
		{
			x						= (int) (Math.random() * WIDTH) + 1;
			y						= (int) (Math.random() * HEIGHT) + 1;
			speed					= (Math.random())+1;
			gender					= (Math.random() > 0.4) ? 'm' : 'f';
			
			student					= new Student(x, y, 5, 5, speed, 0, gender);
			
			students.add(student);
			
			// FIXME: remove soon, just for testing
			if(Math.random() > 0.5)
			{
				students.get(i).infect();
			}
		}
	}
	
	/**
	 * The main game loop
	 */
	public void mainLoop()
	{
		Student currStudent;
		Cupple currCouple;
		Attack currAttack;
		
		int student1, student2;
		
		students.get(0).changeGraphic();
		while(running)
		{
			g						= (Graphics) strategy.getDrawGraphics();
			
			// draw the background
			g.setColor(Color.white);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			// update player
			player.draw(g);

			for(int i=0; i<attacks.size(); i++)
			{
				currAttack=attacks.get(i);
				currAttack.draw(g);
			}
			
			////////////////////////////////////////////////////////////////////////////////////
			// Update couples
			////////////////////////////////////////////////////////////////////////////////////
			
			for(int i = 0; i < students.size(); i++)
			{
				currStudent			= students.get(i);
				
				currStudent.draw(g);
				
				// random movement
				moveRandom(currStudent, MOVE_SPEED,
						(int) (Math.random() * (MAX_NUM_MOVES - MIN_NUM_MOVES) +
								MIN_NUM_MOVES + 1));

				// collision detection! :D
				if(player.getBounds().intersects(currStudent.getBounds()))
				{
					students.remove(i);
					break;
				}
				
				if(currStudent.isInfected())
				{
					for(int j = 0; j < students.size(); j++)
					{
						// don't do anything if it's us
						if(i == j) continue;

						// if we hit another infected student
						// TODO: decide on and add realistic chances
						if(currStudent.getBounds().intersects(students.get(j).getBounds())
								&& students.get(j).isInfected()
								&& Math.random() <= CUPPLE_CHANCE)
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
			
			////////////////////////////////////////////////////////////////////////////////////
			// Update couples
			////////////////////////////////////////////////////////////////////////////////////
			
			for(int i = 0; i < couples.size(); i++)
			{
				currCouple			= couples.get(i);
				
				currCouple.draw(g);
				
				// random movement
				moveRandom(currCouple, MOVE_SPEED,
						(int) (Math.random() * (MAX_NUM_MOVES - MIN_NUM_MOVES) +
								MIN_NUM_MOVES + 1));
				
				// update students
				for(int j = 0; j < students.size(); j++)
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
				}
			}
			
			// finish drawing
			g.dispose();
			strategy.show();
			
			////////////////////////////////////////////////////////////////////////////////////
			// Move the player
			////////////////////////////////////////////////////////////////////////////////////
			// TODO: use physics for diagonal movement? (sqrt 2 * MOVE_SPEED^2)
			
			if(upPressed && !downPressed)
			{
				player.move(0, -MOVE_SPEED);
				//upPressed				= false;
			}
			
			if(downPressed && !upPressed)
			{
				player.move(0, MOVE_SPEED);
				//downPressed				= false;
			}
			
			if(leftPressed && !rightPressed)
			{
				player.move(-MOVE_SPEED, 0);
				//leftPressed				= false;
			}
			
			if(rightPressed && !leftPressed)
			{
				player.move(MOVE_SPEED, 0);
				//rightPressed			= false;
			}
			
			if(attackPressed)
			{
				Attack testAttack;
				testAttack=new Attack(player.x, player.y, 0, "attack", player.getDirection(), 0, true, 0);
				testAttack.switchXY();
				attacks.add(testAttack);
			}
			
			// keep the game from running too fast
			try
			{
				Thread.sleep(SPEED_THROTTLE);
			}
			catch(Exception e) {}
		}
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
			obj.setDirection((int) (Math.random()*4));
			obj.resetMoveCount();
		}
		
		// change direction if we hit the top or bottom
		if(obj.getBounds().y <= 0 && obj.getDirection() == Direction.NORTH)
		{
			obj.setDirection(Direction.SOUTH);
			obj.resetMoveCount();
		}
		else if(obj.getBounds().y >= HEIGHT - obj.getBounds().height  &&
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
		else if(obj.getBounds().x >= WIDTH - obj.getBounds().width &&
				obj.getDirection() == Direction.EAST)
		{
			obj.setDirection(Direction.WEST);
			obj.resetMoveCount();
		}
		
		obj.move(speed);
	}
	
	public static void main(String args[])
	{
		ParkViewProtector game			= new ParkViewProtector();
		game.showOpening();
		game.init();
		game.mainLoop();
		
		System.out.println("The game has finished running! Yay");
	}
}