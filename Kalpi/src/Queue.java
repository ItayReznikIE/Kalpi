import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

public class Queue<T> {
	protected Vector<T> QueueList;
	private boolean endDay=false;


	public Queue()
	{
		this.QueueList = new Vector<T>();
	}

	public synchronized T remove(int index) throws InterruptedException // remove method
	{
		while(QueueList.isEmpty() && !endDay)
		{
			this.wait();			
		}
		if(!QueueList.isEmpty())
		{
			return QueueList.remove(index);
		}
		return null;
	}

	public synchronized void add(T v) // add method
	{
		QueueList.add(v);
		this.notifyAll();
	}

	public boolean isEmpty()
	{
		if(QueueList.isEmpty())
		{
			return true;
		}
		return false;
	}
	
	public synchronized void updateDayEnd()
	{
		endDay=true;
		this.notifyAll();
	}







}
