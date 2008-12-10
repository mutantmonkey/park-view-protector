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
	
	public static final int MIN_STUDENTS	= 15;
	public static final int MAX_STUDENTS	= 30;
	
	public static final int MOVE_SPEED		= 1;
	
	protected JFrame window;
	protected JPanel contentPanel;
	
	private boolean running					= true;
	
	// how far the player needs to move in each direction the next time the game loop is run
	public static boolean upPressed			= false;
	public static boolean downPressed		= false;
	public static boolean leftPressed		= false;
	public static boolean rightPressed		= false;
	
	// graphics
	private Graphics g;
	private BufferStrategy strategy;
	
	// characters
	private Staff player;
	private ArrayList<Student> students		= new ArrayList<Student>();
	private ArrayList<Cupple> couples		= new ArrayList<Cupple>();
	
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
		
		try{Thread.sleep(5000);}catch(Exception e){}
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
			speed					= Math.random() * 5;
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
		
		while(running)
		{
			g						= (Graphics) strategy.getDrawGraphics();
			
			// draw the background
			g.setColor(Color.white);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			// update player
			player.draw(g);
			
			// update students
			for(int i = 0; i < students.size(); i++)
			{
				currStudent			= students.get(i);
				
				currStudent.draw(g);
				
				// move students randomly for testing
				if(Math.random() > 0.9)
				{
					currStudent.move((int) (Math.random() * 6) - 2, (int) (Math.random() * 6) - 2);
				}

				// collision detection! :D
				if(player.getBounds().intersects(currStudent.getBounds()))
				{
					students.remove(i);
					break;
				}
				
				// FIXME: "this is the worst code"
				if(currStudent.isInfected())
				{
					for(int j = 0; j < students.size(); j++)
					{
						// don't do anything if it's us
						if(i == j) continue;
						
						// if we hit a student that isn't infected
						// TODO: decide on and add realistic chances
						if(currStudent.getBounds().intersects(students.get(j).getBounds())
								&& !students.get(j).isInfected())
						{
							students.get(j).infect();
							System.out.println("student #" + j + " infected");
							break;
						}
						
						// if we hit another infected student
						// TODO: decide on and add realistic chances
						if(currStudent.getBounds().intersects(students.get(j).getBounds()) && 
								students.get(j).isInfected())
						{
							couples.add(new Cupple(currStudent, students.get(j)));
							
							try
							{
								students.remove(i);
								students.remove(j);
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
			
			// update couples
			for(int i = 0; i < couples.size(); i++)
			{
				currCouple			= couples.get(i);
				
				currCouple.draw(g);
				
				// move couples randomly for testing
				if(Math.random() > 0.9)
				{
					currCouple.move((int) (Math.random() * 6) - 2, (int) (Math.random() * 6) - 2);
				}
			}
			
			// finish drawing
			g.dispose();
			strategy.show();
			
			/////////////////////////////////////////////////////////////////
			// Move the player
			/////////////////////////////////////////////////////////////////
			
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
			
			// keep the game from running too fast
			try
			{
				Thread.sleep(SPEED_THROTTLE);
			}
			catch(Exception e) {}
		}
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