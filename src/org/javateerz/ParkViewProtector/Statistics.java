package org.javateerz.ParkViewProtector;

import org.javateerz.EasyGL.GLRect;
import org.javateerz.EasyGL.GLString;
import org.newdawn.slick.Font;
import org.newdawn.slick.TrueTypeFont;

public class Statistics
{
	public static final int BAR_HEIGHT		= 10;
	public static final int BAR_SPACING		= 5;
	public static final int BAR_MULTIPLIER	= 2;
	
	public static final int PAD_TOP			= 10;
	public static final int PAD_BOTTOM		= PAD_TOP;
	public static final int PAD_LEFT_BAR	= PAD_TOP * 3 + 3;
	public static final int HEIGHT			= PAD_TOP + BAR_HEIGHT * 2 + BAR_SPACING +
			PAD_BOTTOM;
	
	private Font font;
	
	GraphicBar hpBar;
	GraphicBar tpBar;
	
	Staff player;
	
	Game g;
	
	public Statistics(Game g)
	{
		font						= new TrueTypeFont(new java.awt.Font("Monospaced",
				java.awt.Font.PLAIN, 10), false);
		this.g=g;
		this.player=g.getPlayer();
		hpBar = new GraphicBar(g, "red", player.getMaxHp()*BAR_MULTIPLIER,
				(double) player.getHp() / player.getMaxHp());
		tpBar = new GraphicBar(g, "blue", player.getMaxTp()*BAR_MULTIPLIER,
				(double) player.getTp() / player.getMaxTp());
	}
	
	public void draw(Staff player, int level)
	{
		// background rectangle
		GLRect bgRect				= new GLRect(0, 0, ParkViewProtector.WIDTH,
				HEIGHT);
		bgRect.setColor(ParkViewProtector.STATS_BAR_BG);
		bgRect.draw();
		
		// draw labels
		GLString hpStr				= new GLString("HP:", PAD_TOP, PAD_TOP);
		hpStr.setColor(ParkViewProtector.STATS_BAR_FG);
		hpStr.setFont(font);
		hpStr.draw();

		hpBar.setFilled((double)player.getHp() / player.getMaxHp());
		hpBar.draw(PAD_LEFT_BAR, PAD_TOP);
		
		GLString tpStr				= new GLString("TP:", PAD_TOP, PAD_TOP +
				BAR_HEIGHT + BAR_SPACING);
		tpStr.setColor(ParkViewProtector.STATS_BAR_FG);
		tpStr.setFont(font);
		tpStr.draw();
		
		// draw TP bar
		tpBar.setFilled((double)player.getTp() / player.getMaxTp());
		tpBar.draw(PAD_LEFT_BAR, PAD_TOP + BAR_HEIGHT + BAR_SPACING);
		
		// draw speed
		GLString speedStr			= new GLString("Speed: " + player.getSpeed(), 400,
				PAD_TOP);
		speedStr.setColor(ParkViewProtector.STATS_BAR_FG);
		speedStr.setFont(font);
		speedStr.draw();
		
		// draw level
		GLString levelStr			= new GLString("Level: " + level, 500, PAD_TOP);
		levelStr.setColor(ParkViewProtector.STATS_BAR_FG);
		levelStr.setFont(font);
		levelStr.draw();
		
		// draw Inventory
		player.bin.draw(font, 550, PAD_TOP);
	}
}
