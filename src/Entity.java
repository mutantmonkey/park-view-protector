/**
 * Park View Protector
 *
 * @author	Javateerz
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Entity
{
	public int hp;
	
	public ArrayList<Item> inventory;
	
	public Entity(int health)
	{
		hp		= health;
	}
}