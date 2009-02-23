import java.io.*;
import java.util.ArrayList;

public class DataSaver
{
	private static final String FILE = "pvp.jpg"; //it stands for "java programmed game"!!;
	
	public static void save(Game gamma)
	{
		SavedData datas			= new SavedData(gamma);
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try
		{
			fos = new FileOutputStream(FILE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(datas);
			oos.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static SavedData load()
	{
		FileInputStream fos = null;
		ObjectInputStream ois = null;
		SavedData ret = null;
		
		try
		{
			fos = new FileInputStream(FILE);
			ois = new ObjectInputStream(fos);
			try
			{
				ret = (SavedData) ois.readObject();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			ois.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return ret;
	}
}

class SavedData implements Serializable
{
	private Staff player;
	private ArrayList<Student> students;
	private ArrayList<Cupple> couples;
	private ArrayList<Attack> attacks;
	
	private static final long serialVersionUID	= 1L;
	
	public SavedData()
	{
	}
	
	public SavedData(Game g)
	{
		player			= g.getPlayer();
		students		= g.getStudents();
		couples			= g.getCouples();
		attacks			= g.getAttacks();
	}
	
	public Staff getPlayer()
	{
		return player;
	}
	
	public ArrayList<Student> getStudents()
	{
		return students;
	}
	
	public ArrayList<Cupple> getCouples()
	{
		return couples;
	}
	
	public ArrayList<Attack> getAttacks()
	{
		return attacks;
	}
}