import java.util.Vector;

public class VotesCounter implements Runnable {
	protected VotesList votesList;
	protected double endTime =  0;
	Vector<SecurityGuard> securityGuards;
	private Queue votersQueueToSecurityCheck; 
	private Queue votersQueueToVote;
	VotingSystem votingSystem1 ;
	VotingSystem votingSystem2 ;
	private static int countFinishVotingSystems = 0;


	public VotesCounter(VotesList votesList, double endTime,Vector<SecurityGuard> securityGuards,VotingSystem v1,VotingSystem v2, Queue votersQueueToSecurityCheck, Queue votersQueueToVote)//constructor
	{
		this.votesList=votesList;
		this.endTime = endTime;
		this.securityGuards=securityGuards;
		this.votingSystem1 =v1;
		this.votingSystem2 =v2;
		this.votersQueueToSecurityCheck = votersQueueToSecurityCheck;
		this.votersQueueToVote=votersQueueToVote;

	}

	public void run() // run on votes counter thread
	{
		try {
			Thread.sleep((long) (endTime*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		declareEndDay();
		countVotes();
	}


	protected void declareEndDay() // update the other threads that day is over
	{
		for(int i = 0 ; i<securityGuards.size(); i++)	
		{
			securityGuards.elementAt(i).updateDayEnd();
		}
		votersQueueToSecurityCheck.updateDayEnd();
		votersQueueToVote.updateDayEnd();
		votingSystem1.updateDayEnd();
		votingSystem2.updateDayEnd();
		




	}

	public synchronized void countVotes()//counts the votes
	{

		while(countFinishVotingSystems<2)
		{
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Voting is over, let's start counting");
		Vector<VotedResult> mayorVotedResult = new Vector();
		Vector<VotedResult> listVotedResult = new Vector();
		for(int i = 0; i<votesList.size(); i++)
		{
			if(mayorVotedResult.size() == 0)
			{
				VotedResult result = new VotedResult(votesList.elementAt(i).getMayorCandidateSelection());
				mayorVotedResult.add(result);			}
			else
			{
				for(int j=0; j<mayorVotedResult.size(); j++)
				{
					insertToMayorVector(mayorVotedResult,i,j);
				}
			}
			if(listVotedResult.size() == 0)
			{
				VotedResult result = new VotedResult(votesList.elementAt(i).getListSelection());
				listVotedResult.add(result);
			}
			else
			{
				for(int j=0; j<listVotedResult.size(); j++)
				{
					insertToListVector(listVotedResult,i,j);
				}
			}
		}
		printWinners(mayorVotedResult,listVotedResult);
	}

	public void printWinners(Vector<VotedResult> mayorVotedResult,Vector<VotedResult> listVotedResult)//prints the winner
	{
		VotedResult mayorWinner = getMax(mayorVotedResult);
		System.out.println("The next mayor is: " + mayorWinner.getName());
		VotedResult listWinner = getMax(listVotedResult);
		System.out.println("The list with most votes is: " + listWinner.getName());
	}


	public void insertToMayorVector(Vector<VotedResult> mayorVotedResult,int i,int j)//insert vote to mayor candidates votes list
	{
		if(mayorVotedResult.elementAt(j).getName().equals(votesList.elementAt(i).getMayorCandidateSelection()))
		{
			mayorVotedResult.elementAt(j).addOneVoteToNumOfVotes();
		}
		else if(j == mayorVotedResult.size()-1)
		{
			VotedResult result = new VotedResult(votesList.elementAt(i).getMayorCandidateSelection());
			mayorVotedResult.add(result);
		}

	}

	public void insertToListVector(Vector<VotedResult> listVotedResult,int i,int j)//insert vote to list votes list
	{

		if(listVotedResult.elementAt(j).getName().equals(votesList.elementAt(i).getListSelection()))
		{			
			listVotedResult.elementAt(j).addOneVoteToNumOfVotes();
		}
		else if(j == listVotedResult.size()-1)
		{
			VotedResult result = new VotedResult(votesList.elementAt(i).getListSelection());
			listVotedResult.add(result);
		}
	}

	public VotedResult getMax(Vector<VotedResult> result)//returns the max element at the vector
	{
		if(!result.isEmpty())
		{
			VotedResult max = result.elementAt(0);
			for(int i =1; i<result.size(); i++)
			{
				if(result.elementAt(i).getNumOfVotes()>max.getNumOfVotes())
				{
					max = result.elementAt(i);
				}
			}
			return max;
		}


		else return new VotedResult();
	}

	public synchronized void notifyThatFinishWorking()
	{
		countFinishVotingSystems++;
		this.notifyAll();
	}

}
