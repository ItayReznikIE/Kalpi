import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

public class SecurityGuard implements Runnable {
	private Vector<String> votersIdList;
	private Queue votersQueueToVote;
	private Queue votersQueueToSecurityCheck; 
	private boolean endDay=false;
	private boolean finishWorking;

	public SecurityGuard(Vector<String> votersIdList,Queue votersQueueToVote,Queue votersQueueToSecurityCheck)
	{
		this.votersIdList = votersIdList;
		this.votersQueueToVote = votersQueueToVote;
		this.votersQueueToSecurityCheck = votersQueueToSecurityCheck;
	}

	public void run() // run the security guard thread
	{
		while(!endDay  || !votersQueueToSecurityCheck.isEmpty())
		{
			Voter v;
			long sleepTime = (long) (generateRandomNumber(2,5)*1000);
			try {

				v = (Voter) this.votersQueueToSecurityCheck.remove(0);
				Thread.sleep((long) sleepTime);
				if(v!=null && this.checkIfVoterInTheList(v) && this.checkIfVoterOlderThan17(v))
				{
					sendToNextLine(v);
				}

			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		updateFinishWorking();

	}

	public static double generateRandomNumber(double min, double max) {
		double random = Math.random();
		return min + (max - min) * random;
	}


	public synchronized void sendToNextLine(Voter v) // send voter to next line (to vote)
	{
		votersQueueToVote.add(v);
	}

	private boolean checkIfVoterOlderThan17(Voter v) 
	{
		if(v.getAge()>17)
		{
			return true;
		}
		return false;
	}

	protected boolean checkIfVoterInTheList(Voter voter) // check if voter in the list
	{
		for(int i = 0; i<votersIdList.size(); i++)
		{
			if(votersIdList.elementAt(i).equals(voter.getId()))
			{
				return true;
			}
		}
		return false;
	}

	public void updateDayEnd()
	{
		this.endDay=true;
	}

	public void updateFinishWorking()
	{
		this.finishWorking= true;
	}
	
	public boolean finishWorking()
	{
		return this.finishWorking;
	}







}
