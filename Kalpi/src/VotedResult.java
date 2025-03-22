
public class VotedResult {
	protected String name;
	protected int numOfVotes = 1;//if creating new voted result its 1 because its have it first vote
	
	
	public VotedResult() //constructor
	{
		this.name = "no one was voted";
		this.numOfVotes= 0;
	}
	
	public VotedResult(String name) //constructor
	{
		this.name = name;
	}
	
	public String getName()//returns the voted name
	{
		return this.name;
	}
	
	public void addOneVoteToNumOfVotes()//add 1 to count votes
	{
		numOfVotes++;
	}
	
	public int getNumOfVotes()//returns num of votes
	{
		return this.numOfVotes;
	}

}
