package org.javateerz.ParkViewProtector;

public abstract class Boss extends Staff
{
	private static final long serialVersionUID = 1L;
	
	public Boss(Game g, int x, int y, int hp, int maxHp, double speed, int tp, int maxTp)
	{
<<<<<<< local
		super("boss",x,y,hp,maxHp,speed,tp,maxTp);
		updateSprite();
	}
	
	public Boss(String s,int x, int y, int hp, int maxHp, double speed, int tp, int maxTp)
	{
		super(s,x,y,hp,maxHp,speed,tp,maxTp);
=======
		super(g, x,y, hp,maxHp, speed, tp, maxTp);
>>>>>>> other
		updateSprite();
	}
	
	protected void updateSprite()
	{
		sprite = DataStore.INSTANCE.getSprite("big_boss.png");
	}
	
	public abstract Attack getAttack(int i);
}