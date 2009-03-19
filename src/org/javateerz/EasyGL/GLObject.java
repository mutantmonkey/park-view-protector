package org.javateerz.EasyGL;

public abstract class GLObject
{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	public GLObject(int x, int y, int width, int height)
	{
		this.x				= x;
		this.y				= y;
		this.width			= width;
		this.height			= height;
	}
	
	public abstract void draw();
}
