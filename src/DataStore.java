/**
 * A singleton class to handle data storage and caching (to prevent loading sprites, music, etc. more than once)
 * 
 * @author Javateerz
 */

import java.util.HashMap;

// this is an enum, but we could also use our own Singleton class
public enum DataStore
{
	INSTANCE;
	
	private HashMap sprites		= new HashMap();
	
	public Sprite getSprite(String ref)
	{
		
	}
}