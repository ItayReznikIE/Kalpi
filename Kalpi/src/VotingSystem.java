import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

public class VotingSystem implements Runnable {
	private Queue votersQueueToVote;
	private boolean endDay=false;
	private VotesCounter votesCounter;
	private Vector<SecurityGuard> SecurityGuards ;
	private boolean finishWorking = false;
	static VotesList votesList;


	public VotingSystem(Queue votersQueueToVote,VotesList votesList) // Voting System constructor
	{
		this.votersQueueToVote = votersQueueToVote;
		this.votesList=votesList;
	}
	public void run() //runs the voting system main
	{

		Voter v = null;
		while(!endDay || !votersQueueToVote.isEmpty() || !SecurityGuardsFinishWorking())
		{
			try 
			{
				v = (Voter) votersQueueToVote.remove(0);
				if(v!=null)
				{
					double random = Math.random();
					Thread.sleep(2000);

					if(random>0.2)
					{
						VoteTicket t = new VoteTicket(v.getId(),v.getAge(),v.getMayorCandidateSelection(),v.getListSelection());
						votesList.add(t);

					}
					else
					{
						System.out.println("CreationProcessWasNotExecutedPropetlyException");
						throw new CreationProcessWasNotExecutedPropetlyException();
					}
				}
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			} catch (CreationProcessWasNotExecutedPropetlyException e) {
				e.printStackTrace();
			}
		}
		this.votesCounter.notifyThatFinishWorking();

	}



	public void updateDayEnd()
	{
		this.endDay=true;
	}

	public void updateVotesCounter(VotesCounter votesCounter)
	{
		this.votesCounter=votesCounter;
	}
	public void updateSecurityGuards(Vector<SecurityGuard> SecurityGuards)
	{
		this.SecurityGuards=SecurityGuards;
	}
	
	public boolean SecurityGuardsFinishWorking()
	{
		for (int i = 0 ; i<SecurityGuards.size();i++)
		{
			if(!SecurityGuards.elementAt(i).finishWorking())
			{
				return false;
			}
		}
		return true;
	}






}



