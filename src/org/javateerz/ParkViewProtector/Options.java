package org.javateerz.ParkViewProtector;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public enum Options
{
	INSTANCE;
	
	private Preferences options;
	
	private Options()
	{
		options						= Preferences.userNodeForPackage(getClass());
	}

	/**
	 * Gets the value of a float option
	 */
	public float getFloat(String key, float def)
	{
		return options.getFloat(key, def);
	}
	
	/**
	 * Update a float option
	 * 
	 * @param key
	 * @param value
	 */
	public void putFloat(String key, float value)
	{
		options.putFloat(key, value);
	}
	
	/**
	 * Ensure the options are stored
	 */
	public void sync()
	{
		try
		{
			options.sync();
		}
		catch (BackingStoreException e)
		{
			e.printStackTrace();
		}
	}
}
