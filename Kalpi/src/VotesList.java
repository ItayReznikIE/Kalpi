import java.util.Vector;

public class VotesList {
	protected Vector<VoteTicket> VotesTicketsList;

	public VotesList() //constructor
	{
		this.VotesTicketsList = new Vector<VoteTicket>();
	}

	protected void add(VoteTicket t) //adds vote ticket to the list
	{
		this.VotesTicketsList.add(t);
	}

	protected VoteTicket remove(int index)//removes vote ticket from the list
	{
		return this.VotesTicketsList.remove(index);
	}

	public int size()//returns the size
	{
		return this.VotesTicketsList.size();
	}

	public VoteTicket elementAt(int index)//returns the element at i 
	{
		return this.VotesTicketsList.elementAt(index);
	}

}
