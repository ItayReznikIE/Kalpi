
public class Voter implements Runnable {
	private String name;
	private String lastName;
	private String id;
	private int age;
	private String mayorCandidateSelection;
	private String listSelection;
	private int time;
	private double endTime;
	private Queue votersQueueToSecurityCheck; 

	public Voter(String name, String lastName, String id, int age, String mayorCandidateSelection, String listSelection, int time,double endTime,Queue votersQueueToSecurityCheck)//constructor
	{
		this.name = name;
		this.lastName = lastName;
		this.id = id;
		this.age = age;
		this.mayorCandidateSelection = mayorCandidateSelection;
		this.listSelection = listSelection;
		this.time = time;
		this.endTime =endTime;
		this.votersQueueToSecurityCheck = votersQueueToSecurityCheck;
	}

	public void run()//runs the voter main
	{
		try
		{
			Thread.sleep(this.time*1000);

		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		if(this.time<this.endTime)
		{
		this.votersQueueToSecurityCheck.add(this);
		}
		else {

		}
		

	}

	public String getName()//return name
	{
		return this.name;
	}

	public String getMayorCandidateSelection()//return the mayor candidate selection
	{
		return this.mayorCandidateSelection;
	}

	public String getListSelection()//return the list selection
	{
		return this.listSelection;
	}

	public String getLastName()//returns last name
	{
		return this.lastName;
	}

	public String getId()//returns id 
	{
		return this.id;
	}

	public int getAge()//returns age
	{
		return this.age;
	}

	public int getTime()//returns time
	{
		return this.time;
	}

}
