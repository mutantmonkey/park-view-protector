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
 * 
 * @serial
 * Yes, I know, this is probably not the best thing to do; classes that are extended usually
 * aren't supposed to be serializable. Sorry.
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Staff extends Character implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int tp;
	private int maxTp;
	public abstract void skill(int ski);
	
	// Creates a Staff member.
	public Staff(int x, int y, int hp, int maxHp, double speed, int dmg, int tp, int maxTp)
	{
		super(x, y, hp, maxHp, speed, dmg);
		//tp=Teacher Points, Amount of points for use of skills
		this.tp=tp;
		this.maxTp=maxTp;
	}
	
	//The character uses an item class uses an item
	public void useItem(int item)
	{
		/*if(inventory.get(item) instanceof KeyItem)
		{
			inventory.get(item).run();
		}*/
	}
	
	//Return the current amount of TP that character has
	public int getTp()
	{
		return tp;
	}
	
	//Return the max TP of the character
	public int getMaxTp()
	{
		return maxTp;
	}
	
	//Changes the current amount of TP
	public void adjustTp(int amount)
	{
		tp+=amount;
	}
	
	//Changes the max TP
	public void adjustMaxTp(int amount)
	{
		maxTp+=amount;
	}
	
	//Sets the current amount of TP
	public void setTp(int amount)
	{
		tp=amount;
	}
	
	//Sets the max TP
	public void setMaxTp(int amount)
	{
		maxTp=amount;
	}
	
	protected void validateState()
	{
		super.validateState();
		
		if(tp > maxTp)
		{
			throw new IllegalArgumentException("TP cannot exceed max TP");
		}
	}
}
