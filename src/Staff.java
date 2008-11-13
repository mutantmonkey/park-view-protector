/**
 * Park View Protector
 * 
 * @author Jason of Javateerz
 *
 * The player class.
 *
 * Update log
 * -Updated parameters. (TP=Teacher Points)
 * -Added doSkill. Unsure what this is gonna' do yet.
 * -Added useItem.
 */

public abstract class Staff extends Character
{
	private int tp;
	private int maxTp;
	
	// Creates a Staff member.
	public Staff(int x, int y, int hp, int maxHp, double speed, int tp, int maxTp)
	{
		super(x, y, hp, maxHp, speed);
		this.tp=tp;
		this.maxTp=maxTp;
	}
	
	public void doSkill()
	{
		//TO-DO: No idea what to so far!
	}
	
	public void useItem(int item)
	{
		/*if(inventory.get(item) instanceof KeyItem)
		{
			inventory.get(item).run();
		}*/
	}
}
