package org.javateerz.ParkViewProtector;

public class Boss extends Character
{
	private static final long serialVersionUID = 1L;
	
	public Boss(double x, double y, int hp, int maxHp, double speed)
	{
		super(x,y,hp,maxHp,speed);
	}
	
	protected void updateSprite()
	{
		sprite = DataStore.INSTANCE.getSprite("big_boss.png");
	}
}