import java.io.*;
import java.io.FileOutputStream.*;
import java.util.ArrayList;

public class DataSaver
{
	private static final String FILE = "pvp.jpg"; //it stands for "java programmed game"!!;
	private static final String ERROR = "Oops, I a-sploded!";
	
	public static void save(Game gamma)
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try
		{
			fos = new FileOutputStream(FILE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(gamma);
			oos.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static Game load()
	{
		FileInputStream fos = null;
		ObjectInputStream ois = null;
		Game ret = null;
		try
		{
			fos = new FileInputStream(FILE);
			ois = new ObjectInputStream(fos);
			try
			{
				ret = (Game)(ois.readObject());
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