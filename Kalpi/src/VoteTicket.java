import java.util.Comparator;

public class VoteTicket implements Comparable{
	private int cardIndex;
	private String voterId;
	private int voterAge;
	private String mayorCandidateSelection;
	private String listSelection;
	private static int numOfVoters;

	protected  VoteTicket(String voterId,int voterAge,String mayorCandidateSelection,String listSelection)//constructor
	{
		numOfVoters++;
		this.cardIndex= numOfVoters;
		this.voterId=voterId;
		this.voterAge=voterAge;
		this.mayorCandidateSelection=mayorCandidateSelection;
		this.listSelection=listSelection;
	}

	public int compareTo(Object o) //comapre by nature compare - card index
	{
		if(this.cardIndex > ((VoteTicket)o).getCardIndex())
		{
			return 1;
		}
		else if(this.cardIndex < ((VoteTicket)o).getCardIndex())
		{
			return -1;
		}
		return 0;
	}

	public int CompareByVoterAge(VoteTicket other)//compare by voter age
	{
		Comparator c = new VoteTicketsByVoterAgeComparator();
		int result = c.compare(this, other);
		if(result > 0)
		{
			return 1;
		}
		else if(result<0)
		{
			return -1;
		}
		return 0;	
	}

	public int getCardIndex()//returns card index
	{
		return this.cardIndex;
	}

	public int getVoterAge()//returns voter age 
	{
		return this.voterAge;
	}
	
	public String getMayorCandidateSelection()//returns mayor candidate selection
	{
		return this.mayorCandidateSelection;
	}
	
	public String getListSelection()//returns list selection
	{
		return this.listSelection;
	}

	public String toString()
	{
		return "the card index is: " +this.cardIndex + "the voted Id is :"+ this.voterId+ "this mayor candi choice is : " +this.mayorCandidateSelection + "the list coice is "+ this.getListSelection();
	}
}
