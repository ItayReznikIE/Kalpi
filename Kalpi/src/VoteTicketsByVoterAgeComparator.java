import java.util.Comparator;

public class VoteTicketsByVoterAgeComparator implements Comparator {
	public int compare(Object o1, Object o2)
	{
		return(((VoteTicket)o1).getVoterAge()-((VoteTicket)o2).getVoterAge());
	}

}
